package com.leadcrm.domain.lead.vo;

import lombok.Value;

@Value
public class CompanyInfo {
    private String companyName;
    private String companyType;
    private String businessScope;
    private String website;
    
    public CompanyInfo(String companyName, String companyType, String businessScope, String website) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        this.companyName = companyName;
        this.companyType = companyType;
        this.businessScope = businessScope;
        this.website = website;
    }
}