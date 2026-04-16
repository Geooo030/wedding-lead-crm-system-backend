package com.leadcrm.domain.lead.service;

import com.leadcrm.domain.lead.aggregate.Lead;
import com.leadcrm.domain.lead.vo.*;
import java.util.List;

public interface LeadDomainService {
    Lead createLead(CompanyInfo companyInfo, ContactInfo contactInfo, AddressInfo addressInfo, 
                   LeadSource leadSource, String notes, Long agentId);
    
    void updateLead(Lead lead, CompanyInfo companyInfo, ContactInfo contactInfo, 
                   AddressInfo addressInfo, String notes);
    
    void changeLeadStatus(Lead lead, LeadStatus newStatus);
    
    void calculatePriority(Lead lead);
    
    boolean isDuplicate(Lead lead, List<Lead> existingLeads);
}