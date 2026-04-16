package com.leadcrm.domain.lead.aggregate;

import com.leadcrm.domain.lead.entity.FollowRecord;
import com.leadcrm.domain.lead.vo.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Lead {
    private Long id;
    private CompanyInfo companyInfo;
    private ContactInfo contactInfo;
    private AddressInfo addressInfo;
    private Priority priority;
    private LeadSource leadSource;
    private String notes;
    private LeadStatus status;
    private Long agentId;
    private String followOperator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FollowRecord> followRecords;
    
    public Lead() {
        this.status = LeadStatus.NEW_LEAD;
        this.priority = new Priority(0);
        this.leadSource = new LeadSource(LeadSource.SourceType.MANUAL, null, null);
        this.followRecords = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void changeStatus(LeadStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addFollowRecord(FollowRecord record) {
        this.followRecords.add(record);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updatePriority(int score) {
        this.priority = new Priority(score);
        this.updatedAt = LocalDateTime.now();
    }
}