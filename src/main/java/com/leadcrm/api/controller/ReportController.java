package com.leadcrm.api.controller;

import com.leadcrm.api.dto.ApiResponse;
import com.leadcrm.api.dto.DashboardStatsDTO;
import com.leadcrm.api.dto.ReportDTO;
import com.leadcrm.application.service.ReportApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportApplicationService reportApplicationService;

    public ReportController(ReportApplicationService reportApplicationService) {
        this.reportApplicationService = reportApplicationService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        DashboardStatsDTO stats = reportApplicationService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyReport() {
        ReportDTO report = reportApplicationService.getDailyReport();
        return ResponseEntity.ok(ApiResponse.success(report));
    }

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyReport() {
        ReportDTO report = reportApplicationService.getWeeklyReport();
        return ResponseEntity.ok(ApiResponse.success(report));
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyReport() {
        ReportDTO report = reportApplicationService.getMonthlyReport();
        return ResponseEntity.ok(ApiResponse.success(report));
    }
}