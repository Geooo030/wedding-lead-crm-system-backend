package com.leadcrm.repository;

import com.leadcrm.entity.FollowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FollowRecordRepository extends JpaRepository<FollowRecord, String> {
    List<FollowRecord> findByLeadIdOrderByCreatedAtDesc(String leadId);
    Long countByCreatedAtAfter(LocalDateTime createdAt);
}