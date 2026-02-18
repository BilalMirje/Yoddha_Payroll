package com.standard.service.impl;

import com.standard.entity.Employee;
import com.standard.entity.EmployeeAttendance;
import com.standard.entity.dtos.employeeAttendance.AttendanceMarkRequest;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.AttendanceMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.EmployeeAttendanceRepository;
import com.standard.repository.EmployeeRepository;
import com.standard.service.EmployeeAttendanceService;
import com.standard.util.enums.AttendanceStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.standard.entity.dtos.pagination.PageRequest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeAttendanceRepository attendanceRepository;
    private final AttendanceMapper mapper;
    private final QueryResultHandler queryResultHandler;

    // ---------- MARK SINGLE ----------
    @Override
    public ApiResponse<?> markAttendance(AttendanceMarkRequest request, LocalDate date) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        EmployeeAttendance attendance =
                attendanceRepository.findByEmployeeIdAndAttendanceDate(
                        request.getEmployeeId(), date
                ).orElse(new EmployeeAttendance());

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(date);
        attendance.setStatus(request.getStatus());

        attendanceRepository.save(attendance);

        return ApiResponseUtil.success("Attendance marked successfully");
    }

    // ---------- UPDATE SINGLE ----------
    @Override
    public ApiResponse<?> updateAttendance(AttendanceMarkRequest request, LocalDate date) {

        EmployeeAttendance attendance =
                attendanceRepository.findByEmployeeIdAndAttendanceDate(
                        request.getEmployeeId(), date
                ).orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendance.setStatus(request.getStatus());
        attendanceRepository.save(attendance);

        return ApiResponseUtil.success("Attendance updated successfully");
    }

    // ---------- BULK PRESENT ----------
    @Override
    public ApiResponse<?> markAllPresent(LocalDate date) {

        List<Employee> employees = employeeRepository.findAll();

        employees.forEach(emp -> {
            EmployeeAttendance attendance =
                    attendanceRepository.findByEmployeeIdAndAttendanceDate(emp.getId(), date)
                            .orElse(new EmployeeAttendance());

            attendance.setEmployee(emp);
            attendance.setAttendanceDate(date);
            attendance.setStatus(AttendanceStatus.PRESENT);

            attendanceRepository.save(attendance);
        });

        return ApiResponseUtil.success("All employees marked PRESENT");
    }


    // ---------- BULK ABSENT ----------
    @Override
    public ApiResponse<?> markAllAbsent(LocalDate date) {

        List<Employee> employees = employeeRepository.findAll();

        employees.forEach(emp -> {
            EmployeeAttendance attendance =
                    attendanceRepository.findByEmployeeIdAndAttendanceDate(emp.getId(), date)
                            .orElse(new EmployeeAttendance());

            attendance.setEmployee(emp);
            attendance.setAttendanceDate(date);
            attendance.setStatus(AttendanceStatus.ABSENT);

            attendanceRepository.save(attendance);
        });

        return ApiResponseUtil.updated("All employees marked ABSENT");
    }


    // ---------- DELETE ----------
    @Override
    public ApiResponse<?> deleteAttendance(Long id) {

        EmployeeAttendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendanceRepository.delete(attendance);

        return ApiResponseUtil.deleted();
    }

    @Override
    public ApiResponse<?> deleteAttendanceByDate(Long employeeId, LocalDate date) {

        if (!attendanceRepository.existsByEmployeeIdAndAttendanceDate(employeeId, date)) {
            throw new ResourceNotFoundException("Attendance record not found");
        }

        attendanceRepository.deleteByEmployeeIdAndAttendanceDate(employeeId, date);

        return ApiResponseUtil.deleted();
    }

    @Override
    public ApiResponse<?> deleteAllAttendanceByDate(LocalDate date) {

        attendanceRepository.deleteByAttendanceDate(date);

        return ApiResponseUtil.deleted();
    }

    // ---------- GET BY DATE (STANDARD PAGINATION PATTERN) ----------
    @Override
    public ApiResponse<?> getAttendanceByDate(LocalDate date, PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> attendanceRepository.findByAttendanceDate(date, pageable),
                sort -> attendanceRepository.findByAttendanceDate(date, sort),
                () -> attendanceRepository.findByAttendanceDate(date),
                mapper::toResponse,
                "Attendance not found"
        );
    }

    // ---------- MONTH SUMMARY (SINGLE) ----------
    @Override
    public ApiResponse<?> getMonthlySummary(Long employeeId, int year, int month) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        long present = attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                employeeId, AttendanceStatus.PRESENT, start, end);

        long absent = attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                employeeId, AttendanceStatus.ABSENT, start, end);

        return ApiResponseUtil.fetched(mapper.toSummary(employee, present, absent));
    }

    // ---------- MONTH SUMMARY (ALL EMPLOYEES) ----------
    @Override
    public ApiResponse<?> getMonthlySummaryForAllEmployees(int year, int month, PageRequest pageRequest) {

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        return queryResultHandler.fetchAndRespond(
                pageRequest,

                pageable -> employeeRepository.findAll(pageable)
                        .map(emp -> mapper.toSummary(
                                emp,
                                attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                                        emp.getId(), AttendanceStatus.PRESENT, start, end),
                                attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                                        emp.getId(), AttendanceStatus.ABSENT, start, end)
                        )),

                sort -> employeeRepository.findAll(sort)
                        .stream()
                        .map(emp -> mapper.toSummary(
                                emp,
                                attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                                        emp.getId(), AttendanceStatus.PRESENT, start, end),
                                attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                                        emp.getId(), AttendanceStatus.ABSENT, start, end)
                        ))
                        .toList(),

                () -> employeeRepository.findAll()
                        .stream()
                        .map(emp -> mapper.toSummary(
                                emp,
                                attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                                        emp.getId(), AttendanceStatus.PRESENT, start, end),
                                attendanceRepository.countByEmployeeIdAndStatusAndAttendanceDateBetween(
                                        emp.getId(), AttendanceStatus.ABSENT, start, end)
                        ))
                        .toList(),

                emp -> emp,
                "Attendance summary not found"
        );
    }
}
