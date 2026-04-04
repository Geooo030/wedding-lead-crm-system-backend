package com.leadcrm.dto;

import com.leadcrm.entity.FollowRecord;
import lombok.Data;

@Data
public class FollowRecordRequest {
    private Long leadId;
    private String contactMethod;
    private String contactResult;
    private String customerIntention;
    private String currentStage;
    private String notes;
    private String nextAction;
    
    public FollowRecord toFollowRecord() {
        FollowRecord record = new FollowRecord();
        record.setLeadId(leadId);
        record.setNotes(notes);
        record.setNextAction(nextAction);
        
        if (contactMethod != null && !contactMethod.isEmpty()) {
            try {
                record.setContactMethod(FollowRecord.ContactMethod.valueOf(contactMethod));
            } catch (IllegalArgumentException e) {
                record.setContactMethod(FollowRecord.ContactMethod.phone);
            }
        }
        
        if (contactResult != null && !contactResult.isEmpty()) {
            try {
                record.setContactResult(FollowRecord.ContactResult.valueOf(contactResult));
            } catch (IllegalArgumentException e) {
                record.setContactResult(FollowRecord.ContactResult.reached);
            }
        }
        
        if (customerIntention != null && !customerIntention.isEmpty()) {
            try {
                record.setCustomerIntention(FollowRecord.CustomerIntention.valueOf(customerIntention));
            } catch (IllegalArgumentException e) {
                record.setCustomerIntention(FollowRecord.CustomerIntention.medium);
            }
        }
        
        if (currentStage != null && !currentStage.isEmpty()) {
            try {
                record.setCurrentStage(FollowRecord.FollowStage.valueOf(currentStage));
            } catch (IllegalArgumentException e) {
                record.setCurrentStage(FollowRecord.FollowStage.new_lead);
            }
        }
        
        return record;
    }
}
