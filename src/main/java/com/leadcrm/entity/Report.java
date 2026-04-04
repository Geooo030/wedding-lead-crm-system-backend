package com.leadcrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reports", uniqueConstraints = {
    @UniqueConstraint(name = "uk_type_date", columnNames = {"report_type", "report_date"})
})
public class Report {
    @Id
    @Column(length = 36)
    private String id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", length = 20, nullable = false)
    private ReportType reportType;
    
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;
    
    @Column(columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String metrics;
    
    @Column(name = "agent_summary", columnDefinition = "TEXT")
    private String agentSummary;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }
    
    public enum ReportType {
        daily, weekly, monthly
    }
}