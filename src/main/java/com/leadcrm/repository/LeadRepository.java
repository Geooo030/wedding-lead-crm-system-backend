package com.leadcrm.repository;

import com.leadcrm.entity.Lead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, String> {
    
    Page<Lead> findByCountry(String country, Pageable pageable);
    
    Page<Lead> findByStatus(Lead.LeadStatus status, Pageable pageable);
    
    Page<Lead> findByPriorityLevel(Lead.PriorityLevel priorityLevel, Pageable pageable);
    
    Page<Lead> findByCompanyType(String companyType, Pageable pageable);
    
    Page<Lead> findByRegion(String region, Pageable pageable);
    
    @Query("SELECT l FROM Lead l WHERE " +
           "(:country IS NULL OR l.country = :country) AND " +
           "(:status IS NULL OR l.status = :status) AND " +
           "(:priorityLevel IS NULL OR l.priorityLevel = :priorityLevel) AND " +
           "(:companyType IS NULL OR l.companyType = :companyType) AND " +
           "(:region IS NULL OR l.region = :region) AND " +
           "(:keyword IS NULL OR l.companyName LIKE %:keyword% OR l.contactPhone LIKE %:keyword% OR l.contactEmail LIKE %:keyword%)")
    Page<Lead> searchLeads(
            @Param("country") String country,
            @Param("status") Lead.LeadStatus status,
            @Param("priorityLevel") Lead.PriorityLevel priorityLevel,
            @Param("companyType") String companyType,
            @Param("region") String region,
            @Param("keyword") String keyword,
            Pageable pageable);
    
    @Query("SELECT COUNT(l) FROM Lead l WHERE l.createdAt >= :startTime")
    Long countByCreatedAtAfter(@Param("startTime") LocalDateTime startTime);
    
    @Query("SELECT l.country, COUNT(l) FROM Lead l GROUP BY l.country ORDER BY COUNT(l) DESC")
    List<Object[]> countByCountry();
    
    @Query("SELECT l.status, COUNT(l) FROM Lead l GROUP BY l.status")
    List<Object[]> countByStatus();
    
    @Query("SELECT l.priorityLevel, COUNT(l) FROM Lead l GROUP BY l.priorityLevel")
    List<Object[]> countByPriorityLevel();
    
    boolean existsByCompanyNameAndContactPhone(String companyName, String contactPhone);
}