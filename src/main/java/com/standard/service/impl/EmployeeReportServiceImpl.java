package com.standard.service.impl;

import com.standard.entity.Employee;
import com.standard.entity.dtos.employeeReportDtos.EmployeeMonthlyReportResponse;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.EmployeeReportMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.EmployeeAdvanceRepository;
import com.standard.repository.EmployeeAllowanceRepository;
import com.standard.repository.EmployeeAttendanceRepository;
import com.standard.repository.EmployeeRepository;
import com.standard.service.EmployeeReportService;
import com.standard.util.enums.AttendanceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class EmployeeReportServiceImpl implements EmployeeReportService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeAttendanceRepository attendanceRepository;
    private final EmployeeAdvanceRepository advanceRepository;
    private final EmployeeAllowanceRepository allowanceRepository;
    private final EmployeeReportMapper reportMapper;
    private final QueryResultHandler queryResultHandler;


    @Override
    public ApiResponse<?> getMonthlyReport(Long employeeId, int year, int month) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        int totalDays = ym.lengthOfMonth();

        long presentDays = attendanceRepository
                .countByEmployeeIdAndStatusAndAttendanceDateBetween(
                        employeeId, AttendanceStatus.PRESENT, start, end);

        long absentDays = totalDays - presentDays;

        double perDaySalary = employee.getSalary() / totalDays;
        double attendanceSalary = perDaySalary * presentDays;

        Double totalAdvance =
                advanceRepository.findTotalAdvance(employeeId, start, end);
        totalAdvance = totalAdvance == null ? 0.0 : totalAdvance;

        Double totalAllowance =
                allowanceRepository.findTotalAllowance(employeeId, start, end);
        totalAllowance = totalAllowance == null ? 0.0 : totalAllowance;

        double netPayable = attendanceSalary + totalAllowance - totalAdvance;

        return ApiResponseUtil.fetched(
                reportMapper.toMonthlyReportResponse(
                        employee,
                        ym,
                        totalDays,
                        (int) presentDays,
                        (int) absentDays,
                        perDaySalary,
                        attendanceSalary,
                        totalAllowance,
                        totalAdvance,
                        netPayable
                )
        );
    }

    @Override
    public ApiResponse<?> getMonthlyReportForAllEmployees(int year, int month,
                                                          PageRequest pageRequest) {

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        return queryResultHandler.fetchAndRespond(

                pageRequest,

                // PAGE MODE
                pageable -> employeeRepository.findAll(pageable)
                        .map(emp -> buildMonthlyReport(emp, ym, start, end)),

                // SORT MODE
                sort -> employeeRepository.findAll(sort)
                        .stream()
                        .map(emp -> buildMonthlyReport(emp, ym, start, end))
                        .toList(),

                // FULL LIST MODE
                () -> employeeRepository.findAll()
                        .stream()
                        .map(emp -> buildMonthlyReport(emp, ym, start, end))
                        .toList(),

                report -> report,
                "Monthly report not found"
        );
    }
    private EmployeeMonthlyReportResponse buildMonthlyReport(
            Employee employee,
            YearMonth ym,
            LocalDate start,
            LocalDate end
    ){
        int totalDays = ym.lengthOfMonth();

        long present = attendanceRepository
                .countByEmployeeIdAndStatusAndAttendanceDateBetween(
                        employee.getId(), AttendanceStatus.PRESENT, start, end);

        long absent = totalDays - present;

        double perDay = employee.getSalary() / totalDays;
        double earned = perDay * present;

        Double advances = advanceRepository
                .findTotalAdvance(employee.getId(), start, end);
        advances = advances == null ? 0.0 : advances;

        Double allowances = allowanceRepository
                .findTotalAllowance(employee.getId(), start, end);
        allowances = allowances == null ? 0.0 : allowances;

        double net = earned + allowances - advances;

        return reportMapper.toMonthlyReportResponse(
                employee, ym, totalDays,
                (int) present, (int) absent,
                perDay, earned, allowances, advances, net
        );
    }




}
