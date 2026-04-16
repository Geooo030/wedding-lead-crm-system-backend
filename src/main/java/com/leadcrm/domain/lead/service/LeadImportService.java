package com.leadcrm.domain.lead.service;

import com.leadcrm.domain.lead.aggregate.Lead;
import java.util.List;
import java.util.Map;

public interface LeadImportService {
    int importLeads(List<Lead> leads);
    
    List<Lead> validateLeads(List<Lead> leads);
    
    Map<Lead, String> validateLead(Lead lead);
    
    List<Lead> deduplicateLeads(List<Lead> leads);
}