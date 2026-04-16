package com.leadcrm.infrastructure.persistence.repository;

import com.leadcrm.infrastructure.persistence.entity.LeadJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LeadJpaRepository extends JpaRepository<LeadJpaEntity, Long> {
    
    List<LeadJpaEntity> findByAgentId(Long agentId);
    
    List<LeadJpaEntity> findByStatus(String status);
    
    List<LeadJpaEntity> findByPriorityLevel(String priorityLevel);
    
    List<LeadJpaEntity> findByCountry(String country);
    
    @Query("SELECT l FROM LeadJpaEntity l WHERE l.agentId = :agentId AND (:status IS NULL OR l.status = :status) AND (:priorityLevel IS NULL OR l.priorityLevel = :priorityLevel) AND (:companyType IS NULL OR l.companyType = :companyType) AND (:region IS NULL OR l.region = :region) AND (:keyword IS NULL OR l.companyName LIKE %:keyword% OR l.contactPhone LIKE %:keyword% OR l.contactEmail LIKE %:keyword%)")
    List<LeadJpaEntity> search(@Param("agentId") Long agentId, @Param("status") String status, 
                              @Param("priorityLevel") String priorityLevel, @Param("companyType") String companyType, 
                              @Param("region") String region, @Param("keyword") String keyword);
    
    @Query("SELECT l.country, COUNT(l) FROM LeadJpaEntity l GROUP BY l.country")
    List<Object[]> countByCountry();
    
    @Query("SELECT l.status, COUNT(l) FROM LeadJpaEntity l GROUP BY l.status")
    List<Object[]> countByStatus();
    
    @Query("SELECT l.priorityLevel, COUNT(l) FROM LeadJpaEntity l GROUP BY l.priorityLevel")
    List<Object[]> countByPriorityLevel();
    
    @Query("SELECT DISTINCT l.country FROM LeadJpaEntity l WHERE l.country IS NOT NULL AND l.country != ''")
    List<String> findDistinctCountries();
    
    @Query("SELECT u.username, COUNT(l) FROM LeadJpaEntity l JOIN UserJpaEntity u ON l.agentId = u.id GROUP BY u.username")
    List<Object[]> getAgentSummary();
    
    boolean existsByCompanyNameAndContactPhone(String companyName, String contactPhone);
}