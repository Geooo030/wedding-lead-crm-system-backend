package com.leadcrm.domain.lead.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyInfoTest {
    
    @Test
    public void testCreateCompanyInfo() {
        CompanyInfo companyInfo = new CompanyInfo(
            "Test Company",
            "Technology",
            "Software development",
            "www.test.com"
        );
        
        assertEquals("Test Company", companyInfo.getCompanyName());
        assertEquals("Technology", companyInfo.getCompanyType());
        assertEquals("Software development", companyInfo.getBusinessScope());
        assertEquals("www.test.com", companyInfo.getWebsite());
    }
    
    @Test
    public void testCreateCompanyInfoWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CompanyInfo("", "Technology", "Software development", "www.test.com");
        });
    }
    
    @Test
    public void testCreateCompanyInfoWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CompanyInfo(null, "Technology", "Software development", "www.test.com");
        });
    }
}