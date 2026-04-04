package com.leadcrm.controller;

import com.leadcrm.dto.ApiResponse;
import com.leadcrm.entity.FollowRecord;
import com.leadcrm.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    
    private final FollowService followService;
    
    public FollowController(FollowService followService) {
        this.followService = followService;
    }
    
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<?> getFollowRecordsByLeadId(@PathVariable String leadId) {
        List<FollowRecord> records = followService.findByLeadId(leadId);
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @PostMapping
    public ResponseEntity<?> createFollowRecord(@RequestBody FollowRecord record) {
        FollowRecord saved = followService.save(record);
        return ResponseEntity.ok(ApiResponse.success("创建成功", saved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFollowRecord(@PathVariable String id, @RequestBody FollowRecord record) {
        FollowRecord existing = followService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("跟进记录不存在"));
        }
        record.setId(id);
        FollowRecord saved = followService.save(record);
        return ResponseEntity.ok(ApiResponse.success("更新成功", saved));
    }
    
    @GetMapping("/progress/{leadId}")
    public ResponseEntity<?> getFollowProgress(@PathVariable String leadId) {
        List<FollowRecord> records = followService.findByLeadId(leadId);
        
        // 计算当前阶段进度
        int progress = 0;
        String currentStage = "new_lead";
        
        if (!records.isEmpty()) {
            FollowRecord latest = records.get(0);
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
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("progress", progress);
        result.put("currentStage", currentStage);
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
