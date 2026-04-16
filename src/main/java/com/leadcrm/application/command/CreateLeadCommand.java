package com.leadcrm.application.command;

import lombok.Data;

@Data
public class CreateLeadCommand {
    private String companyName;
    private String companyType;
    private String businessScope;
    private String website;
    private String contactPhone;
    private String contactEmail;
    private String country;
    private String region;
    private String address;
    private String leadSource;
    private String leadChannel;
    private String sourceUrl;
    private String notes;
    private Long agentId;
}