package com.leadcrm.domain.lead.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FollowRecord {
    private Long id;
    private Long leadId;
    private Long operatorId;
    private Long agentId;
    private ContactMethod contactMethod;
    private ContactResult contactResult;
    private CustomerIntention customerIntention;
    private FollowStage currentStage;
    private String notes;
    private String nextAction;
    private LocalDate nextActionDate;
    private LocalDateTime createdAt;
    
    public FollowRecord() {
        this.createdAt = LocalDateTime.now();
        this.customerIntention = CustomerIntention.MEDIUM;
        this.currentStage = FollowStage.NEW_LEAD;
    }
    
    public enum ContactMethod {
        PHONE, WHATSAPP, EMAIL, VISIT
    }
    
    public enum ContactResult {
        REACHED, UNREACHABLE, CALLBACK, FAILED
    }
    
    public enum CustomerIntention {
        HIGH, MEDIUM, LOW, NONE
    }
    
    public enum FollowStage {
        NEW_LEAD, FIRST_CONTACT, REQUIREMENT, QUOTATION, DEAL, REJECTED
    }
}