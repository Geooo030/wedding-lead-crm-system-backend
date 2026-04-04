package com.leadcrm.repository;

import com.leadcrm.entity.FollowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FollowRecordRepository extends JpaRepository<FollowRecord, Long> {
    List<FollowRecord> findByLeadIdOrderByCreatedAtDesc(Long leadId);
    Long countByCreatedAtAfter(LocalDateTime createdAt);
}