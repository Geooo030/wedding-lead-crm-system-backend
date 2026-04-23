package com.leadcrm.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class DashboardStatsDTO {
    private Long totalLeads;
    private Long newLeadsToday;
    private List<Object[]> byCountry;
    private List<Object[]> byStatus;
    private List<Object[]> byPriority;
    private List<Object[]> agentSummary;
}