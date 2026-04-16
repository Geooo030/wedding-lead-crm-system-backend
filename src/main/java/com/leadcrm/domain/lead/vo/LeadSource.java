package com.leadcrm.domain.lead.vo;

import lombok.Value;

@Value
public class LeadSource {
    private SourceType leadSource;
    private String leadChannel;
    private String sourceUrl;
    
    public LeadSource(SourceType leadSource, String leadChannel, String sourceUrl) {
        if (leadSource == null) {
            throw new IllegalArgumentException("Lead source type cannot be null");
        }
        this.leadSource = leadSource;
        this.leadChannel = leadChannel;
        this.sourceUrl = sourceUrl;
    }
    
    public enum SourceType {
        AI, MANUAL
    }
}