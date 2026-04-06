package com.leadcrm.controller;

import com.leadcrm.dto.ApiResponse;
import com.leadcrm.dto.FollowRecordRequest;
import com.leadcrm.dto.FollowRecordWithOperator;
import com.leadcrm.entity.FollowRecord;
import com.leadcrm.entity.Lead;
import com.leadcrm.entity.User;
import com.leadcrm.repository.LeadRepository;
import com.leadcrm.repository.UserRepository;
import com.leadcrm.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.leadcrm.entity.FollowRecord.FollowStage.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    
    private final FollowService followService;
    private final UserRepository userRepository;
    private final LeadRepository leadRepository;
    
    public FollowController(FollowService followService, UserRepository userRepository, LeadRepository leadRepository) {
        this.followService = followService;
        this.userRepository = userRepository;
        this.leadRepository = leadRepository;
    }
    
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<?> getFollowRecordsByLeadId(@PathVariable Long leadId) {
        List<FollowRecord> records = followService.findByLeadId(leadId);
        
        List<FollowRecordWithOperator> result = records.stream().map(record -> {
            String operatorUsername = "未知";
            if (record.getOperatorId() != null) {
                User operator = userRepository.findById(record.getOperatorId()).orElse(null);
                if (operator != null) {
                    operatorUsername = operator.getUsername();
                }
            }
            return FollowRecordWithOperator.from(record, operatorUsername);
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @PostMapping
    public ResponseEntity<?> createFollowRecord(@RequestBody FollowRecordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        FollowRecord record = request.toFollowRecord();
        
        if (user != null) {
            record.setOperatorId(user.getId());
        } else {
            record.setOperatorId(1L);
        }
        
        FollowRecord saved = followService.save(record);
        
        // 根据当前阶段自动更新客户状态和跟进人
        if (request.getLeadId() != null) {
            Lead lead = leadRepository.findById(request.getLeadId()).orElse(null);
            if (lead != null) {
                // 更新跟进人为当前用户
                if (user != null) {
                    lead.setFollowOperator(user.getUsername());
                } else {
                    lead.setFollowOperator("admin");
                }
                
                // 根据当前阶段更新客户状态
                if (request.getCurrentStage() != null) {
                    FollowRecord.FollowStage followStage = FollowRecord.FollowStage.codeOf(request.getCurrentStage());
                    if (followStage != null) {
                        switch (followStage) {
                            case first_contact:
                                lead.setStatus(Lead.LeadStatus.contacting);
                                break;
                            case requirement:
                            case quotation:
                                lead.setStatus(Lead.LeadStatus.negotiating);
                                break;
                            case deal:
                                lead.setStatus(Lead.LeadStatus.converted);
                                break;
                            case rejected:
                                lead.setStatus(Lead.LeadStatus.lost);
                                break;
                        }
                    }
                }
                leadRepository.save(lead);
            }
        }
        
        return ResponseEntity.ok(ApiResponse.success("创建成功", saved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFollowRecord(@PathVariable Long id, @RequestBody FollowRecordRequest request) {
        FollowRecord existing = followService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("跟进记录不存在"));
        }
        
        FollowRecord record = request.toFollowRecord();
        record.setId(id);
        record.setOperatorId(existing.getOperatorId());
        
        FollowRecord saved = followService.save(record);
        return ResponseEntity.ok(ApiResponse.success("更新成功", saved));
    }
    
    @GetMapping("/progress/{leadId}")
    public ResponseEntity<?> getFollowProgress(@PathVariable Long leadId) {
        List<FollowRecord> records = followService.findByLeadId(leadId);
        
        // 计算当前阶段进度
        int progress = 0;
        String currentStage = "new_lead";
        
        if (!records.isEmpty()) {
            FollowRecord latest = records.get(0);
            if (latest.getCurrentStage() != null) {
                currentStage = latest.getCurrentStage().name();
                
                switch (latest.getCurrentStage()) {
                    case new_lead:
                        progress = 0;
                        break;
                    case first_contact:
                        progress = 25;
                        break;
                    case requirement:
                        progress = 50;
                        break;
                    case quotation:
                        progress = 75;
                        break;
                    case deal:
                        progress = 100;
                        break;
                    case rejected:
                        progress = 0;
                        break;
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("progress", progress);
        result.put("currentStage", currentStage);
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
