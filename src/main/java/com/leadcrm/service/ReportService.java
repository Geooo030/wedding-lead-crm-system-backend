package com.leadcrm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadcrm.entity.Lead;
import com.leadcrm.entity.Report;
import com.leadcrm.repository.LeadRepository;
import com.leadcrm.repository.ReportRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReportService {
    
    private final ReportRepository reportRepository;
    private final LeadRepository leadRepository;
    private final ObjectMapper objectMapper;
    
    public ReportService(ReportRepository reportRepository, LeadRepository leadRepository, ObjectMapper objectMapper) {
        this.reportRepository = reportRepository;
        this.leadRepository = leadRepository;
        this.objectMapper = objectMapper;
    }
    
    public Report getLatestReport(Report.ReportType type) {
        return reportRepository.findTopByReportTypeOrderByReportDateDesc(type).orElse(null);
    }
    
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // 总客户数
        stats.put("totalLeads", leadRepository.count());
        
        // 今日新增
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        stats.put("newLeadsToday", leadRepository.countByCreatedAtAfter(todayStart));
        
        // 按国家分布
        stats.put("byCountry", leadRepository.countByCountry());
        
        // 按状态分布
        stats.put("byStatus", leadRepository.countByStatus());
        
        // 按优先级分布
        stats.put("byPriority", leadRepository.countByPriorityLevel());
        
        return stats;
    }
}