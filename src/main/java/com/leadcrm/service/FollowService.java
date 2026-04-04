package com.leadcrm.service;

import com.leadcrm.entity.FollowRecord;
import com.leadcrm.repository.FollowRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FollowService {
    
    private final FollowRecordRepository followRecordRepository;
    
    public FollowService(FollowRecordRepository followRecordRepository) {
        this.followRecordRepository = followRecordRepository;
    }
    
    public List<FollowRecord> findByLeadId(String leadId) {
        return followRecordRepository.findByLeadIdOrderByCreatedAtDesc(leadId);
    }
    
    public FollowRecord findById(String id) {
        return followRecordRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public FollowRecord save(FollowRecord record) {
        return followRecordRepository.save(record);
    }
}