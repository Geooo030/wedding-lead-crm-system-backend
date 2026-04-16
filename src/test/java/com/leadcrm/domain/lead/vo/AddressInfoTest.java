package com.leadcrm.domain.lead.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddressInfoTest {
    
    @Test
    public void testCreateAddressInfo() {
        AddressInfo addressInfo = new AddressInfo(
            "China",
            "Beijing",
            "123 Main St"
        );
        
        assertEquals("China", addressInfo.getCountry());
        assertEquals("Beijing", addressInfo.getRegion());
        assertEquals("123 Main St", addressInfo.getAddress());
    }
    
    @Test
    public void testCreateAddressInfoWithEmptyCountry() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AddressInfo("", "Beijing", "123 Main St");
        });
    }
    
    @Test
    public void testCreateAddressInfoWithNullCountry() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AddressInfo(null, "Beijing", "123 Main St");
        });
    }
    
    @Test
    public void testCreateAddressInfoWithoutRegion() {
        AddressInfo addressInfo = new AddressInfo(
            "China",
            null,
            "123 Main St"
        );
        
        assertEquals("China", addressInfo.getCountry());
        assertNull(addressInfo.getRegion());
        assertEquals("123 Main St", addressInfo.getAddress());
    }
    
    @Test
    public void testCreateAddressInfoWithoutAddress() {
        AddressInfo addressInfo = new AddressInfo(
            "China",
            "Beijing",
            null
        );
        
        assertEquals("China", addressInfo.getCountry());
        assertEquals("Beijing", addressInfo.getRegion());
        assertNull(addressInfo.getAddress());
    }
}