package com.leadcrm.application.service;

import com.leadcrm.application.command.CreateFollowRecordCommand;
import com.leadcrm.application.command.UpdateFollowRecordCommand;
import com.leadcrm.domain.lead.entity.FollowRecord;
import com.leadcrm.infrastructure.persistence.entity.FollowRecordJpaEntity;
import com.leadcrm.infrastructure.persistence.repository.FollowJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowApplicationService {

    private final FollowJpaRepository followJpaRepository;

    public FollowApplicationService(FollowJpaRepository followJpaRepository) {
        this.followJpaRepository = followJpaRepository;
    }

    public List<FollowRecord> getFollowRecordsByLeadId(Long leadId) {
        return followJpaRepository.findByLeadIdOrderByCreatedAtDesc(leadId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public FollowRecord createFollowRecord(CreateFollowRecordCommand command) {
        FollowRecordJpaEntity entity = new FollowRecordJpaEntity();
        entity.setLeadId(command.getLeadId());
        entity.setOperatorId(command.getOperatorId());
        entity.setAgentId(command.getAgentId());
        entity.setContactMethod(command.getContactMethod());
        entity.setContactResult(command.getContactResult());
        entity.setCustomerIntention(command.getCustomerIntention());
        entity.setCurrentStage(command.getCurrentStage());
        entity.setNotes(command.getNotes());
        entity.setNextAction(command.getNextAction());
        entity.setNextActionDate(command.getNextActionDate());

        FollowRecordJpaEntity saved = followJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Transactional
    public FollowRecord updateFollowRecord(UpdateFollowRecordCommand command) {
        Optional<FollowRecordJpaEntity> optionalEntity = followJpaRepository.findById(command.getId());
        if (!optionalEntity.isPresent()) {
            throw new RuntimeException("Follow record not found");
        }

        FollowRecordJpaEntity entity = optionalEntity.get();
        if (command.getContactMethod() != null) {
            entity.setContactMethod(command.getContactMethod());
        }
        if (command.getContactResult() != null) {
            entity.setContactResult(command.getContactResult());
        }
        if (command.getCustomerIntention() != null) {
            entity.setCustomerIntention(command.getCustomerIntention());
        }
        if (command.getCurrentStage() != null) {
            entity.setCurrentStage(command.getCurrentStage());
        }
        if (command.getNotes() != null) {
            entity.setNotes(command.getNotes());
        }
        if (command.getNextAction() != null) {
            entity.setNextAction(command.getNextAction());
        }
        if (command.getNextActionDate() != null) {
            entity.setNextActionDate(command.getNextActionDate());
        }

        FollowRecordJpaEntity saved = followJpaRepository.save(entity);
        return toDomain(saved);
    }

    public FollowRecord getFollowRecordById(Long id) {
        return followJpaRepository.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new RuntimeException("Follow record not found"));
    }

    public java.util.Map<String, Object> getFollowProgress(Long leadId) {
        Long totalRecords = followJpaRepository.countByLeadId(leadId);
        String latestStage = followJpaRepository.findLatestStageByLeadId(leadId);

        java.util.Map<String, Object> progress = new java.util.HashMap<>();
        progress.put("progress", totalRecords > 0 ? Math.min(100, totalRecords * 20) : 0);
        progress.put("currentStage", latestStage != null ? latestStage : "NEW_LEAD");

        return progress;
    }

    private FollowRecord toDomain(FollowRecordJpaEntity entity) {
        FollowRecord record = new FollowRecord();
        record.setId(entity.getId());
        record.setLeadId(entity.getLeadId());
        record.setOperatorId(entity.getOperatorId());
        record.setAgentId(entity.getAgentId());
        record.setNotes(entity.getNotes());
        record.setNextAction(entity.getNextAction());
        record.setNextActionDate(entity.getNextActionDate());
        record.setCreatedAt(entity.getCreatedAt());

        if (entity.getContactMethod() != null) {
            record.setContactMethod(FollowRecord.ContactMethod.valueOf(entity.getContactMethod()));
        }
        if (entity.getContactResult() != null) {
            record.setContactResult(FollowRecord.ContactResult.valueOf(entity.getContactResult()));
        }
        if (entity.getCustomerIntention() != null) {
            record.setCustomerIntention(FollowRecord.CustomerIntention.valueOf(entity.getCustomerIntention()));
        }
        if (entity.getCurrentStage() != null) {
            record.setCurrentStage(FollowRecord.FollowStage.valueOf(entity.getCurrentStage()));
        }

        return record;
    }
}
