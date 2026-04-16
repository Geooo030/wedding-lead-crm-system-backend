package com.leadcrm.domain.lead.event;

import java.time.LocalDateTime;
import java.util.Map;

public class LeadUpdatedEvent {
    private final Long leadId;
    private final LocalDateTime updatedAt;
    private final Map<String, Object> changes;
    
    public LeadUpdatedEvent(Long leadId, Map<String, Object> changes) {
        this.leadId = leadId;
        this.updatedAt = LocalDateTime.now();
        this.changes = changes;
    }
    
    public Long getLeadId() {
        return leadId;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public Map<String, Object> getChanges() {
        return changes;
    }
}