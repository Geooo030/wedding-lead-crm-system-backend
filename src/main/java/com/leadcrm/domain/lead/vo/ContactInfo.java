package com.leadcrm.domain.lead.vo;

import lombok.Value;

@Value
public class ContactInfo {
    private String contactPhone;
    private String contactEmail;
    
    public ContactInfo(String contactPhone, String contactEmail) {
        if ((contactPhone == null || contactPhone.trim().isEmpty()) && 
            (contactEmail == null || contactEmail.trim().isEmpty())) {
            throw new IllegalArgumentException("At least one contact method must be provided");
        }
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }
}