package com.leadcrm.controller;

import com.leadcrm.dto.ApiResponse;
import com.leadcrm.entity.Report;
import com.leadcrm.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    private final ReportService reportService;
    
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    @GetMapping("/daily")
    public ResponseEntity<?> getDailyReport(@RequestParam(required = false) String date) {
        Report report = reportService.getLatestReport(Report.ReportType.daily);
        return ResponseEntity.ok(ApiResponse.success(report));
    }
    
    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyReport() {
        Report report = reportService.getLatestReport(Report.ReportType.weekly);
        return ResponseEntity.ok(ApiResponse.success(report));
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyReport() {
        Report report = reportService.getLatestReport(Report.ReportType.monthly);
        return ResponseEntity.ok(ApiResponse.success(report));
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getDashboardStats()));
    }
}