package com.leadcrm.domain.lead.event;

import java.time.LocalDateTime;

public class LeadCreatedEvent {
    private final Long leadId;
    private final LocalDateTime createdAt;
    
    public LeadCreatedEvent(Long leadId) {
        this.leadId = leadId;
        this.createdAt = LocalDateTime.now();
    }
    
    public Long getLeadId() {
        return leadId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}