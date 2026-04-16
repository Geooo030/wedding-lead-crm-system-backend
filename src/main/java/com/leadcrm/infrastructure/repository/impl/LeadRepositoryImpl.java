package com.leadcrm.infrastructure.repository.impl;

import com.leadcrm.domain.lead.aggregate.Lead;
import com.leadcrm.domain.lead.entity.FollowRecord;
import com.leadcrm.domain.lead.vo.*;
import com.leadcrm.infrastructure.persistence.entity.LeadJpaEntity;
import com.leadcrm.infrastructure.persistence.entity.FollowRecordJpaEntity;
import com.leadcrm.infrastructure.persistence.repository.LeadJpaRepository;
import com.leadcrm.infrastructure.repository.LeadRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LeadRepositoryImpl implements LeadRepository {
    
    private final LeadJpaRepository leadJpaRepository;
    
    public LeadRepositoryImpl(LeadJpaRepository leadJpaRepository) {
        this.leadJpaRepository = leadJpaRepository;
    }
    
    @Override
    public Lead save(Lead lead) {
        LeadJpaEntity jpaEntity = toJpaEntity(lead);
        LeadJpaEntity saved = leadJpaRepository.save(jpaEntity);
        return toDomainModel(saved);
    }
    
    @Override
    public Optional<Lead> findById(Long id) {
        return leadJpaRepository.findById(id).map(this::toDomainModel);
    }
    
    @Override
    public void deleteById(Long id) {
        leadJpaRepository.deleteById(id);
    }
    
    @Override
    public List<Lead> findAll() {
        return leadJpaRepository.findAll().stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Lead> findByAgentId(Long agentId) {
        return leadJpaRepository.findByAgentId(agentId).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Lead> findByStatus(LeadStatus status) {
        return leadJpaRepository.findByStatus(status.name()).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Lead> findByPriority(Priority.PriorityLevel priorityLevel) {
        return leadJpaRepository.findByPriorityLevel(priorityLevel.name()).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Lead> findByCountry(String country) {
        return leadJpaRepository.findByCountry(country).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Lead> search(String keyword, Long agentId, LeadStatus[] statuses, 
                           Priority.PriorityLevel priorityLevel, String companyType, String region) {
        // 简化实现，实际需要根据状态数组等进行更复杂的查询
        String status = statuses != null && statuses.length > 0 ? statuses[0].name() : null;
        String priority = priorityLevel != null ? priorityLevel.name() : null;
        
        return leadJpaRepository.search(agentId, status, priority, companyType, region, keyword).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Object[]> countByCountry() {
        return leadJpaRepository.countByCountry();
    }
    
    @Override
    public List<Object[]> countByStatus() {
        return leadJpaRepository.countByStatus();
    }
    
    @Override
    public List<Object[]> countByPriorityLevel() {
        return leadJpaRepository.countByPriorityLevel();
    }
    
    @Override
    public List<String> findDistinctCountries() {
        return leadJpaRepository.findDistinctCountries();
    }
    
    @Override
    public List<Object[]> getAgentSummary() {
        return leadJpaRepository.getAgentSummary();
    }
    
    @Override
    public boolean existsByCompanyNameAndContactPhone(String companyName, String contactPhone) {
        return leadJpaRepository.existsByCompanyNameAndContactPhone(companyName, contactPhone);
    }
    
    private LeadJpaEntity toJpaEntity(Lead lead) {
        LeadJpaEntity entity = new LeadJpaEntity();
        entity.setId(lead.getId());
        entity.setCompanyName(lead.getCompanyInfo().getCompanyName());
        entity.setCompanyType(lead.getCompanyInfo().getCompanyType());
        entity.setBusinessScope(lead.getCompanyInfo().getBusinessScope());
        entity.setWebsite(lead.getCompanyInfo().getWebsite());
        entity.setContactPhone(lead.getContactInfo().getContactPhone());
        entity.setContactEmail(lead.getContactInfo().getContactEmail());
        entity.setCountry(lead.getAddressInfo().getCountry());
        entity.setRegion(lead.getAddressInfo().getRegion());
        entity.setAddress(lead.getAddressInfo().getAddress());
        entity.setPriorityScore(lead.getPriority().getPriorityScore());
        entity.setPriorityLevel(lead.getPriority().getPriorityLevel().name());
        entity.setLeadSource(lead.getLeadSource().getLeadSource().name());
        entity.setLeadChannel(lead.getLeadSource().getLeadChannel());
        entity.setSourceUrl(lead.getLeadSource().getSourceUrl());
        entity.setNotes(lead.getNotes());
        entity.setStatus(lead.getStatus().name());
        entity.setAgentId(lead.getAgentId());
        entity.setFollowOperator(lead.getFollowOperator());
        entity.setCreatedAt(lead.getCreatedAt());
        entity.setUpdatedAt(lead.getUpdatedAt());
        return entity;
    }
    
    private Lead toDomainModel(LeadJpaEntity entity) {
        Lead lead = new Lead();
        lead.setId(entity.getId());
        
        CompanyInfo companyInfo = new CompanyInfo(
            entity.getCompanyName(),
            entity.getCompanyType(),
            entity.getBusinessScope(),
            entity.getWebsite()
        );
        lead.setCompanyInfo(companyInfo);
        
        ContactInfo contactInfo = new ContactInfo(
            entity.getContactPhone(),
            entity.getContactEmail()
        );
        lead.setContactInfo(contactInfo);
        
        AddressInfo addressInfo = new AddressInfo(
            entity.getCountry(),
            entity.getRegion(),
            entity.getAddress()
        );
        lead.setAddressInfo(addressInfo);
        
        Priority priority = new Priority(entity.getPriorityScore());
        lead.setPriority(priority);
        
        LeadSource leadSource = new LeadSource(
            LeadSource.SourceType.valueOf(entity.getLeadSource()),
            entity.getLeadChannel(),
            entity.getSourceUrl()
        );
        lead.setLeadSource(leadSource);
        
        lead.setNotes(entity.getNotes());
        lead.setStatus(LeadStatus.valueOf(entity.getStatus()));
        lead.setAgentId(entity.getAgentId());
        lead.setFollowOperator(entity.getFollowOperator());
        lead.setCreatedAt(entity.getCreatedAt());
        lead.setUpdatedAt(entity.getUpdatedAt());
        
        return lead;
    }
}