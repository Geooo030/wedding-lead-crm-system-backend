package com.leadcrm.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reports", uniqueConstraints = {
    @UniqueConstraint(name = "uk_type_date", columnNames = {"report_type", "report_date"})
})
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", length = 20, nullable = false)
    private ReportType reportType;
    
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;
    
    @Column(columnDefinition = "TEXT")
    private String metrics;
    
    @Column(name = "agent_summary", columnDefinition = "TEXT")
    private String agentSummary;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum ReportType {
        daily, weekly, monthly
    }
}
