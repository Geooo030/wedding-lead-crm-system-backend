package com.leadcrm.api.controller;

import com.leadcrm.application.command.CreateFollowRecordCommand;
import com.leadcrm.application.command.UpdateFollowRecordCommand;
import com.leadcrm.application.service.FollowApplicationService;
import com.leadcrm.api.dto.ApiResponse;
import com.leadcrm.domain.lead.entity.FollowRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowApplicationService followApplicationService;

    public FollowController(FollowApplicationService followApplicationService) {
        this.followApplicationService = followApplicationService;
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<?> getFollowRecordsByLeadId(@PathVariable Long leadId) {
        List<FollowRecord> records = followApplicationService.getFollowRecordsByLeadId(leadId);
        List<Map<String, Object>> dtos = records.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PostMapping
    public ResponseEntity<?> createFollowRecord(@RequestBody CreateFollowRecordCommand command) {
        FollowRecord record = followApplicationService.createFollowRecord(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(record)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFollowRecord(@PathVariable Long id, @RequestBody UpdateFollowRecordCommand command) {
        command.setId(id);
        FollowRecord record = followApplicationService.updateFollowRecord(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(record)));
    }

    @GetMapping("/progress/{leadId}")
    public ResponseEntity<?> getFollowProgress(@PathVariable Long leadId) {
        Map<String, Object> progress = followApplicationService.getFollowProgress(leadId);
        return ResponseEntity.ok(ApiResponse.success(progress));
    }

    private Map<String, Object> toDTO(FollowRecord record) {
        Map<String, Object> dto = new java.util.HashMap<>();
        dto.put("id", record.getId());
        dto.put("leadId", record.getLeadId());
        dto.put("operatorId", record.getOperatorId());
        dto.put("agentId", record.getAgentId());
        dto.put("contactMethod", record.getContactMethod() != null ? record.getContactMethod().name() : null);
        dto.put("contactResult", record.getContactResult() != null ? record.getContactResult().name() : null);
        dto.put("customerIntention", record.getCustomerIntention() != null ? record.getCustomerIntention().name() : null);
        dto.put("currentStage", record.getCurrentStage() != null ? record.getCurrentStage().name() : null);
        dto.put("notes", record.getNotes());
        dto.put("nextAction", record.getNextAction());
        dto.put("nextActionDate", record.getNextActionDate());
        dto.put("createdAt", record.getCreatedAt());
        return dto;
    }
}
