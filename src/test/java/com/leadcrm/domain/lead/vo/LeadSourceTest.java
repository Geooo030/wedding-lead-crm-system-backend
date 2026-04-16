package com.leadcrm.domain.lead.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeadSourceTest {
    
    @Test
    public void testCreateLeadSourceWithAI() {
        LeadSource leadSource = new LeadSource(
            LeadSource.SourceType.AI,
            "Website",
            "www.example.com"
        );
        
        assertEquals(LeadSource.SourceType.AI, leadSource.getLeadSource());
        assertEquals("Website", leadSource.getLeadChannel());
        assertEquals("www.example.com", leadSource.getSourceUrl());
    }
    
    @Test
    public void testCreateLeadSourceWithManual() {
        LeadSource leadSource = new LeadSource(
            LeadSource.SourceType.MANUAL,
            "Email",
            null
        );
        
        assertEquals(LeadSource.SourceType.MANUAL, leadSource.getLeadSource());
        assertEquals("Email", leadSource.getLeadChannel());
        assertNull(leadSource.getSourceUrl());
    }
    
    @Test
    public void testCreateLeadSourceWithNullSource() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeadSource(null, "Website", "www.example.com");
        });
    }
    
    @Test
    public void testCreateLeadSourceWithoutChannel() {
        LeadSource leadSource = new LeadSource(
            LeadSource.SourceType.AI,
            null,
            "www.example.com"
        );
        
        assertEquals(LeadSource.SourceType.AI, leadSource.getLeadSource());
        assertNull(leadSource.getLeadChannel());
        assertEquals("www.example.com", leadSource.getSourceUrl());
    }
}