package com.leadcrm.application.command;

import lombok.Data;

@Data
public class UpdateLeadCommand {
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
    private String notes;
    private String status;
    private Long agentId;
}