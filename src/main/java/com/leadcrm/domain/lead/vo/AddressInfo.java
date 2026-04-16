package com.leadcrm.domain.lead.vo;

import lombok.Value;

@Value
public class AddressInfo {
    private String country;
    private String region;
    private String address;
    
    public AddressInfo(String country, String region, String address) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty");
        }
        this.country = country;
        this.region = region;
        this.address = address;
    }
}