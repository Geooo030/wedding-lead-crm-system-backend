package com.leadcrm.domain.lead.service.impl;

import com.leadcrm.domain.lead.aggregate.Lead;
import com.leadcrm.domain.lead.service.LeadImportService;
import com.leadcrm.domain.lead.service.LeadDomainService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LeadImportServiceImpl implements LeadImportService {
    
    private final LeadDomainService leadDomainService;
    
    public LeadImportServiceImpl(LeadDomainService leadDomainService) {
        this.leadDomainService = leadDomainService;
    }
    
    @Override
    public int importLeads(List<Lead> leads) {
        List<Lead> validatedLeads = validateLeads(leads);
        List<Lead> uniqueLeads = deduplicateLeads(validatedLeads);
        return uniqueLeads.size();
    }
    
    @Override
    public List<Lead> validateLeads(List<Lead> leads) {
        List<Lead> validLeads = new ArrayList<>();
        for (Lead lead : leads) {
            Map<Lead, String> validationResult = validateLead(lead);
            if (validationResult.isEmpty()) {
                validLeads.add(lead);
            }
        }
        return validLeads;
    }
    
    @Override
    public Map<Lead, String> validateLead(Lead lead) {
        Map<Lead, String> errors = new HashMap<>();
        
        if (lead.getCompanyInfo() == null || 
            lead.getCompanyInfo().getCompanyName() == null || 
            lead.getCompanyInfo().getCompanyName().trim().isEmpty()) {
            errors.put(lead, "Company name is required");
        }
        
        if (lead.getContactInfo() == null || 
            (lead.getContactInfo().getContactPhone() == null && 
             lead.getContactInfo().getContactEmail() == null)) {
            errors.put(lead, "At least one contact method is required");
        }
        
        if (lead.getAddressInfo() == null || 
            lead.getAddressInfo().getCountry() == null || 
            lead.getAddressInfo().getCountry().trim().isEmpty()) {
            errors.put(lead, "Country is required");
        }
        
        return errors;
    }
    
    @Override
    public List<Lead> deduplicateLeads(List<Lead> leads) {
        List<Lead> uniqueLeads = new ArrayList<>();
        for (Lead lead : leads) {
            if (!leadDomainService.isDuplicate(lead, uniqueLeads)) {
                uniqueLeads.add(lead);
            }
        }
        return uniqueLeads;
    }
}