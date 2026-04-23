package com.leadcrm.api.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ReportDTO {
    private String reportType;
    private LocalDate reportDate;
    private Map<String, Object> metrics;
    private List<Object[]> agentSummary;
    private LocalDateTime createdAt;
}