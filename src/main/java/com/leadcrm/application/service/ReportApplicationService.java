package com.leadcrm.application.service;

import com.leadcrm.api.dto.DashboardStatsDTO;
import com.leadcrm.api.dto.ReportDTO;
import com.leadcrm.infrastructure.persistence.repository.LeadJpaRepository;
import com.leadcrm.infrastructure.persistence.repository.TaskJpaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportApplicationService {

    private final LeadJpaRepository leadJpaRepository;
    private final TaskJpaRepository taskJpaRepository;

    public ReportApplicationService(LeadJpaRepository leadJpaRepository, TaskJpaRepository taskJpaRepository) {
        this.leadJpaRepository = leadJpaRepository;
        this.taskJpaRepository = taskJpaRepository;
    }

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setTotalLeads(leadJpaRepository.count());
        stats.setNewLeadsToday(leadJpaRepository.countByCreatedAtBetween(
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.now()
        ));
        stats.setByCountry(leadJpaRepository.countByCountry());
        stats.setByStatus(leadJpaRepository.countByStatus());
        stats.setByPriority(leadJpaRepository.countByPriorityLevel());
        stats.setAgentSummary(leadJpaRepository.getAgentSummary());
        return stats;
    }

    public ReportDTO getDailyReport() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalLeads", leadJpaRepository.count());
        metrics.put("newLeads", leadJpaRepository.countByCreatedAtBetween(startOfDay, endOfDay));
        metrics.put("byCountry", leadJpaRepository.countByCountry());
        metrics.put("byStatus", leadJpaRepository.countByStatus());
        metrics.put("byPriority", leadJpaRepository.countByPriorityLevel());

        ReportDTO report = new ReportDTO();
        report.setReportType("daily");
        report.setReportDate(today);
        report.setMetrics(metrics);
        report.setAgentSummary(leadJpaRepository.getAgentSummary());
        report.setCreatedAt(LocalDateTime.now());
        return report;
    }

    public ReportDTO getWeeklyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(6);
        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalLeads", leadJpaRepository.count());
        metrics.put("newLeadsThisWeek", leadJpaRepository.countByCreatedAtBetween(startDateTime, endDateTime));
        metrics.put("startDate", startOfWeek);
        metrics.put("endDate", today);
        metrics.put("byCountry", leadJpaRepository.countByCountry());
        metrics.put("byStatus", leadJpaRepository.countByStatus());
        metrics.put("byPriority", leadJpaRepository.countByPriorityLevel());

        ReportDTO report = new ReportDTO();
        report.setReportType("weekly");
        report.setReportDate(today);
        report.setMetrics(metrics);
        report.setAgentSummary(leadJpaRepository.getAgentSummary());
        report.setCreatedAt(LocalDateTime.now());
        return report;
    }

    public ReportDTO getMonthlyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDateTime startDateTime = startOfMonth.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalLeads", leadJpaRepository.count());
        metrics.put("newLeadsThisMonth", leadJpaRepository.countByCreatedAtBetween(startDateTime, endDateTime));
        metrics.put("startDate", startOfMonth);
        metrics.put("endDate", today);
        metrics.put("byCountry", leadJpaRepository.countByCountry());
        metrics.put("byStatus", leadJpaRepository.countByStatus());
        metrics.put("byPriority", leadJpaRepository.countByPriorityLevel());

        ReportDTO report = new ReportDTO();
        report.setReportType("monthly");
        report.setReportDate(today);
        report.setMetrics(metrics);
        report.setAgentSummary(leadJpaRepository.getAgentSummary());
        report.setCreatedAt(LocalDateTime.now());
        return report;
    }
}