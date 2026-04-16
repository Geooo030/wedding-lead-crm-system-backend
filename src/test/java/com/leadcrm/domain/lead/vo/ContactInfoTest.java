package com.leadcrm.domain.lead.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactInfoTest {
    
    @Test
    public void testCreateContactInfoWithPhone() {
        ContactInfo contactInfo = new ContactInfo(
            "1234567890",
            null
        );
        
        assertEquals("1234567890", contactInfo.getContactPhone());
        assertNull(contactInfo.getContactEmail());
    }
    
    @Test
    public void testCreateContactInfoWithEmail() {
        ContactInfo contactInfo = new ContactInfo(
            null,
            "test@example.com"
        );
        
        assertNull(contactInfo.getContactPhone());
        assertEquals("test@example.com", contactInfo.getContactEmail());
    }
    
    @Test
    public void testCreateContactInfoWithBoth() {
        ContactInfo contactInfo = new ContactInfo(
            "1234567890",
            "test@example.com"
        );
        
        assertEquals("1234567890", contactInfo.getContactPhone());
        assertEquals("test@example.com", contactInfo.getContactEmail());
    }
    
    @Test
    public void testCreateContactInfoWithNeither() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ContactInfo(null, null);
        });
    }
    
    @Test
    public void testCreateContactInfoWithEmptyBoth() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ContactInfo("", "");
        });
    }
}