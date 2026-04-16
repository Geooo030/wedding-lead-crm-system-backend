package com.leadcrm.application.query;

import lombok.Data;

@Data
public class LeadQuery {
    private String country;
    private String[] statuses;
    private String priorityLevel;
    private String companyType;
    private String region;
    private String keyword;
    private Long agentId;
    private int page;
    private int size;
}