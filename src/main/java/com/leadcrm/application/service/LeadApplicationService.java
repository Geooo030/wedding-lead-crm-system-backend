package com.leadcrm.application.service;

import com.leadcrm.application.command.CreateLeadCommand;
import com.leadcrm.application.command.UpdateLeadCommand;
import com.leadcrm.application.query.LeadQuery;
import com.leadcrm.domain.lead.aggregate.Lead;
import com.leadcrm.domain.lead.service.LeadDomainService;
import com.leadcrm.domain.lead.service.LeadImportService;
import com.leadcrm.domain.lead.vo.*;
import com.leadcrm.infrastructure.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LeadApplicationService {
    
    private final LeadRepository leadRepository;
    private final LeadDomainService leadDomainService;
    private final LeadImportService leadImportService;
    
    public LeadApplicationService(LeadRepository leadRepository, LeadDomainService leadDomainService, 
                                LeadImportService leadImportService) {
        this.leadRepository = leadRepository;
        this.leadDomainService = leadDomainService;
        this.leadImportService = leadImportService;
    }
    
    @Transactional
    public Lead createLead(CreateLeadCommand command) {
        CompanyInfo companyInfo = new CompanyInfo(
            command.getCompanyName(),
            command.getCompanyType(),
            command.getBusinessScope(),
            command.getWebsite()
        );
        
        ContactInfo contactInfo = new ContactInfo(
            command.getContactPhone(),
            command.getContactEmail()
        );
        
        AddressInfo addressInfo = new AddressInfo(
            command.getCountry(),
            command.getRegion(),
            command.getAddress()
        );
        
        LeadSource leadSource = new LeadSource(
            LeadSource.SourceType.valueOf(command.getLeadSource()),
            command.getLeadChannel(),
            command.getSourceUrl()
        );
        
        Lead lead = leadDomainService.createLead(
            companyInfo,
            contactInfo,
            addressInfo,
            leadSource,
            command.getNotes(),
            command.getAgentId()
        );
        
        return leadRepository.save(lead);
    }
    
    @Transactional
    public Lead updateLead(UpdateLeadCommand command) {
        Optional<Lead> optionalLead = leadRepository.findById(command.getId());
        if (!optionalLead.isPresent()) {
            throw new RuntimeException("Lead not found");
        }
        
        Lead lead = optionalLead.get();
        
        if (command.getCompanyName() != null) {
            CompanyInfo companyInfo = new CompanyInfo(
                command.getCompanyName(),
                command.getCompanyType(),
                command.getBusinessScope(),
                command.getWebsite()
            );
            leadDomainService.updateLead(lead, companyInfo, null, null, command.getNotes());
        }
        
        if (command.getContactPhone() != null || command.getContactEmail() != null) {
            ContactInfo contactInfo = new ContactInfo(
                command.getContactPhone(),
                command.getContactEmail()
            );
            leadDomainService.updateLead(lead, null, contactInfo, null, command.getNotes());
        }
        
        if (command.getCountry() != null) {
            AddressInfo addressInfo = new AddressInfo(
                command.getCountry(),
                command.getRegion(),
                command.getAddress()
            );
            leadDomainService.updateLead(lead, null, null, addressInfo, command.getNotes());
        }
        
        if (command.getStatus() != null) {
            leadDomainService.changeLeadStatus(lead, LeadStatus.valueOf(command.getStatus()));
        }
        
        if (command.getAgentId() != null) {
            lead.setAgentId(command.getAgentId());
        }
        
        return leadRepository.save(lead);
    }
    
    public Optional<Lead> getLeadById(Long id) {
        return leadRepository.findById(id);
    }
    
    public List<Lead> searchLeads(LeadQuery query) {
        LeadStatus[] statuses = null;
        if (query.getStatuses() != null && query.getStatuses().length > 0) {
            statuses = new LeadStatus[query.getStatuses().length];
            for (int i = 0; i < query.getStatuses().length; i++) {
                statuses[i] = LeadStatus.valueOf(query.getStatuses()[i]);
            }
        }
        
        Priority.PriorityLevel priorityLevel = null;
        if (query.getPriorityLevel() != null) {
            priorityLevel = Priority.PriorityLevel.valueOf(query.getPriorityLevel());
        }
        
        return leadRepository.search(
            query.getKeyword(),
            query.getAgentId(),
            statuses,
            priorityLevel,
            query.getCompanyType(),
            query.getRegion()
        );
    }
    
    @Transactional
    public void deleteLead(Long id) {
        leadRepository.deleteById(id);
    }
    
    @Transactional
    public int importLeads(List<Lead> leads) {
        return leadImportService.importLeads(leads);
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
    
    public List<String> getDistinctCountries() {
        return leadRepository.findDistinctCountries();
    }
    
    public List<Object[]> getAgentSummary() {
        return leadRepository.getAgentSummary();
    }
}