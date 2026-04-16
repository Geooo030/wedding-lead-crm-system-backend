package com.leadcrm.domain.lead.service.impl;

import com.leadcrm.domain.lead.aggregate.Lead;
import com.leadcrm.domain.lead.service.LeadDomainService;
import com.leadcrm.domain.lead.vo.*;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LeadDomainServiceImpl implements LeadDomainService {
    
    @Override
    public Lead createLead(CompanyInfo companyInfo, ContactInfo contactInfo, AddressInfo addressInfo, 
                          LeadSource leadSource, String notes, Long agentId) {
        Lead lead = new Lead();
        lead.setCompanyInfo(companyInfo);
        lead.setContactInfo(contactInfo);
        lead.setAddressInfo(addressInfo);
        lead.setLeadSource(leadSource);
        lead.setNotes(notes);
        lead.setAgentId(agentId);
        calculatePriority(lead);
        return lead;
    }
    
    @Override
    public void updateLead(Lead lead, CompanyInfo companyInfo, ContactInfo contactInfo, 
                          AddressInfo addressInfo, String notes) {
        if (companyInfo != null) {
            lead.setCompanyInfo(companyInfo);
        }
        if (contactInfo != null) {
            lead.setContactInfo(contactInfo);
        }
        if (addressInfo != null) {
            lead.setAddressInfo(addressInfo);
        }
        if (notes != null) {
            lead.setNotes(notes);
        }
        calculatePriority(lead);
    }
    
    @Override
    public void changeLeadStatus(Lead lead, LeadStatus newStatus) {
        lead.changeStatus(newStatus);
    }
    
    @Override
    public void calculatePriority(Lead lead) {
        int score = 0;
        
        // 基于公司信息计算分数
        if (lead.getCompanyInfo() != null) {
            String companyType = lead.getCompanyInfo().getCompanyType();
            if (companyType != null && (companyType.contains("婚纱") || companyType.contains("婚礼"))) {
                score += 20;
            }
        }
        
        // 基于联系信息计算分数
        if (lead.getContactInfo() != null) {
            if (lead.getContactInfo().getContactPhone() != null) {
                score += 15;
            }
            if (lead.getContactInfo().getContactEmail() != null) {
                score += 10;
            }
        }
        
        // 基于来源计算分数
        if (lead.getLeadSource() != null) {
            if (lead.getLeadSource().getLeadSource() == LeadSource.SourceType.AI) {
                score += 25;
            }
        }
        
        // 基于跟进记录计算分数
        if (lead.getFollowRecords() != null && !lead.getFollowRecords().isEmpty()) {
            score += lead.getFollowRecords().size() * 5;
        }
        
        lead.updatePriority(score);
    }
    
    @Override
    public boolean isDuplicate(Lead lead, List<Lead> existingLeads) {
        for (Lead existing : existingLeads) {
            if (isSameCompany(lead, existing)) {
                return true;
            }
            if (isSameContact(lead, existing)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isSameCompany(Lead lead1, Lead lead2) {
        if (lead1.getCompanyInfo() == null || lead2.getCompanyInfo() == null) {
            return false;
        }
        return lead1.getCompanyInfo().getCompanyName().equals(lead2.getCompanyInfo().getCompanyName());
    }
    
    private boolean isSameContact(Lead lead1, Lead lead2) {
        if (lead1.getContactInfo() == null || lead2.getContactInfo() == null) {
            return false;
        }
        if (lead1.getContactInfo().getContactPhone() != null && 
            lead1.getContactInfo().getContactPhone().equals(lead2.getContactInfo().getContactPhone())) {
            return true;
        }
        if (lead1.getContactInfo().getContactEmail() != null && 
            lead1.getContactInfo().getContactEmail().equals(lead2.getContactInfo().getContactEmail())) {
            return true;
        }
        return false;
    }
}