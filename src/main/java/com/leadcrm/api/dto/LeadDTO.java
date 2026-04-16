package com.leadcrm.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LeadDTO {
    private Long id;
    private String companyName;
    private String companyType;
    private String businessScope;
    private String website;
    private String contactPhone;
    private String contactEmail;
    private String country;
    private String region;
    private String address;
    private Integer priorityScore;
    private String priorityLevel;
    private String leadSource;
    private String leadChannel;
    private String sourceUrl;
    private String notes;
    private String status;
    private Long agentId;
    private String followOperator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}