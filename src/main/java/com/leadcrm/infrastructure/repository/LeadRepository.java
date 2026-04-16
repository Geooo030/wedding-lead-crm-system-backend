package com.leadcrm.infrastructure.repository;

import com.leadcrm.domain.lead.aggregate.Lead;
import com.leadcrm.domain.lead.vo.LeadStatus;
import com.leadcrm.domain.lead.vo.Priority;
import java.util.List;
import java.util.Optional;

public interface LeadRepository {
    Lead save(Lead lead);
    Optional<Lead> findById(Long id);
    void deleteById(Long id);
    List<Lead> findAll();
    List<Lead> findByAgentId(Long agentId);
    List<Lead> findByStatus(LeadStatus status);
    List<Lead> findByPriority(Priority.PriorityLevel priorityLevel);
    List<Lead> findByCountry(String country);
    List<Lead> search(String keyword, Long agentId, LeadStatus[] statuses, 
                     Priority.PriorityLevel priorityLevel, String companyType, String region);
    List<Object[]> countByCountry();
    List<Object[]> countByStatus();
    List<Object[]> countByPriorityLevel();
    List<String> findDistinctCountries();
    List<Object[]> getAgentSummary();
    boolean existsByCompanyNameAndContactPhone(String companyName, String contactPhone);
}