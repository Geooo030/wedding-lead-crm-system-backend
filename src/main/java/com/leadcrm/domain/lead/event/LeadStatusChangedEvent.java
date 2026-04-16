package com.leadcrm.domain.lead.event;

import com.leadcrm.domain.lead.vo.LeadStatus;
import java.time.LocalDateTime;

public class LeadStatusChangedEvent {
    private final Long leadId;
    private final LeadStatus oldStatus;
    private final LeadStatus newStatus;
    private final LocalDateTime changedAt;
    
    public LeadStatusChangedEvent(Long leadId, LeadStatus oldStatus, LeadStatus newStatus) {
        this.leadId = leadId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = LocalDateTime.now();
    }
    
    public Long getLeadId() {
        return leadId;
    }
    
    public LeadStatus getOldStatus() {
        return oldStatus;
    }
    
    public LeadStatus getNewStatus() {
        return newStatus;
    }
    
    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}