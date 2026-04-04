package com.leadcrm.service;

import com.leadcrm.entity.Lead;
import com.leadcrm.repository.LeadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadService {
    
    private final LeadRepository leadRepository;
    
    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }
    
    public Page<Lead> searchLeads(String country, Lead.LeadStatus status, Lead.PriorityLevel priorityLevel, 
                                   String companyType, String region, String keyword, Pageable pageable) {
        return leadRepository.searchLeads(country, status, priorityLevel, companyType, region, keyword, pageable);
    }
    
    public Lead findById(String id) {
        return leadRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public Lead save(Lead lead) {
        return leadRepository.save(lead);
    }
    
    @Transactional
    public void deleteById(String id) {
        leadRepository.deleteById(id);
    }
    
    public List<Object[]> countByCountry() {
        return leadRepository.countByCountry();
    }
    
    public List<Object[]> countByStatus() {
        return leadRepository.countByStatus();
    }
    
    public List<Object[]> countByPriorityLevel() {
        return leadRepository.countByPriorityLevel();
    }
    
    public Long countByCreatedAtAfter(LocalDateTime startTime) {
        return leadRepository.countByCreatedAtAfter(startTime);
    }
    
    @Transactional
    public int importLeads(List<Lead> leads) {
        int imported = 0;
        for (Lead lead : leads) {
            // 检查重复
            boolean exists = false;
            if (lead.getContactPhone() != null && !lead.getContactPhone().isEmpty()) {
                exists = leadRepository.existsByCompanyNameAndContactPhone(lead.getCompanyName(), lead.getContactPhone());
            }
            
            if (!exists) {
                leadRepository.save(lead);
                imported++;
            }
        }
        return imported;
    }
}