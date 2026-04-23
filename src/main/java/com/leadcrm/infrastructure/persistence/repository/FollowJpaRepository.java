package com.leadcrm.infrastructure.persistence.repository;

import com.leadcrm.infrastructure.persistence.entity.FollowRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FollowJpaRepository extends JpaRepository<FollowRecordJpaEntity, Long> {

    List<FollowRecordJpaEntity> findByLeadId(Long leadId);

    List<FollowRecordJpaEntity> findByLeadIdOrderByCreatedAtDesc(Long leadId);

    @Query("SELECT COUNT(f) FROM FollowRecordJpaEntity f WHERE f.leadId = :leadId")
    Long countByLeadId(@Param("leadId") Long leadId);

    @Query(value = "SELECT f.current_stage FROM follow_records f WHERE f.lead_id = :leadId ORDER BY f.created_at DESC LIMIT 1", nativeQuery = true)
    String findLatestStageByLeadId(@Param("leadId") Long leadId);
}
