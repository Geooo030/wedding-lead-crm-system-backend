package com.leadcrm.dto;

import com.leadcrm.entity.FollowRecord;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FollowRecordWithOperator {
    private Long id;
    private Long leadId;
    private Long operatorId;
    private String operatorUsername;
    private Long agentId;
    private FollowRecord.ContactMethod contactMethod;
    private FollowRecord.ContactResult contactResult;
    private FollowRecord.CustomerIntention customerIntention;
    private FollowRecord.FollowStage currentStage;
    private String notes;
    private String nextAction;
    private LocalDate nextActionDate;
    private LocalDateTime createdAt;

    public static FollowRecordWithOperator from(FollowRecord record, String operatorUsername) {
        FollowRecordWithOperator dto = new FollowRecordWithOperator();
        dto.setId(record.getId());
        dto.setLeadId(record.getLeadId());
        dto.setOperatorId(record.getOperatorId());
        dto.setOperatorUsername(operatorUsername);
        dto.setAgentId(record.getAgentId());
        dto.setContactMethod(record.getContactMethod());
        dto.setContactResult(record.getContactResult());
        dto.setCustomerIntention(record.getCustomerIntention());
        dto.setCurrentStage(record.getCurrentStage());
        dto.setNotes(record.getNotes());
        dto.setNextAction(record.getNextAction());
        dto.setNextActionDate(record.getNextActionDate());
        dto.setCreatedAt(record.getCreatedAt());
        return dto;
    }
}
