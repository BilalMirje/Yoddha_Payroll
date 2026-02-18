package com.standard.controller;

import com.standard.entity.dtos.employeeAttendance.AttendanceMarkRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.service.EmployeeAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class EmployeeAttendanceController {

    private final EmployeeAttendanceService attendanceService;

    // ---------- MARK (DATE via PARAM | default = today) ----------
    @PostMapping("/mark")
    public ResponseEntity<?> mark(
            @RequestParam(required = false) LocalDate date,
            @RequestBody AttendanceMarkRequest request){

        LocalDate finalDate = (date != null) ? date : LocalDate.now();

        return ResponseEntity.ok(
                attendanceService.markAttendance(request, finalDate)
        );
    }

    // ---------- UPDATE ----------
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam(required = false) LocalDate date,
            @RequestBody AttendanceMarkRequest request){

        LocalDate finalDate = (date != null) ? date : LocalDate.now();

        return ResponseEntity.ok(
                attendanceService.updateAttendance(request, finalDate)
        );
    }

    // ---------- BULK ----------
    @PostMapping("/mark-all-present")
    public ResponseEntity<?> markAllPresent(@RequestParam LocalDate date){
        return ResponseEntity.ok(attendanceService.markAllPresent(date));
    }

    @PostMapping("/mark-all-absent")
    public ResponseEntity<?> markAllAbsent(@RequestParam LocalDate date){
        return ResponseEntity.ok(attendanceService.markAllAbsent(date));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return ResponseEntity.ok(attendanceService.deleteAttendance(id));
    }

    @DeleteMapping("/delete-by-date")
    public ResponseEntity<?> deleteByDate(
            @RequestParam Long employeeId,
            @RequestParam LocalDate date){
        return ResponseEntity.ok(
                attendanceService.deleteAttendanceByDate(employeeId, date)
        );
    }

    @DeleteMapping("/delete-all-by-date")
    public ResponseEntity<?> deleteAllByDate(@RequestParam LocalDate date){
        return ResponseEntity.ok(
                attendanceService.deleteAllAttendanceByDate(date)
        );
    }

    // ---------- GET BY DATE (Custom PageRequest Standard) ----------
    @GetMapping("/get-by-date")
    public ResponseEntity<?> getByDate(
            @RequestParam LocalDate date,
            PageRequest pageRequest){
        return ResponseEntity.ok(
                attendanceService.getAttendanceByDate(date, pageRequest)
        );
    }

    // ---------- MONTH SUMMARY (Single Employee) ----------
    @GetMapping("/monthly-summary")
    public ResponseEntity<?> monthlySummary(
            @RequestParam Long employeeId,
            @RequestParam int year,
            @RequestParam int month){
        return ResponseEntity.ok(
                attendanceService.getMonthlySummary(employeeId, year, month)
        );
    }

    // ---------- MONTH SUMMARY (All Employees - Paginated) ----------
    @GetMapping("/monthly-summary/all")
    public ResponseEntity<?> monthlySummaryAll(
            @RequestParam int year,
            @RequestParam int month,
            PageRequest pageRequest){
        return ResponseEntity.ok(
                attendanceService.getMonthlySummaryForAllEmployees(year, month, pageRequest)
        );
    }
}
