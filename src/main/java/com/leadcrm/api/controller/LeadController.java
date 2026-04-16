package com.leadcrm.api.controller;

import com.leadcrm.application.command.CreateLeadCommand;
import com.leadcrm.application.command.UpdateLeadCommand;
import com.leadcrm.application.query.LeadQuery;
import com.leadcrm.application.service.LeadApplicationService;
import com.leadcrm.api.dto.ApiResponse;
import com.leadcrm.api.dto.LeadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
    
    private final LeadApplicationService leadApplicationService;
    
    public LeadController(LeadApplicationService leadApplicationService) {
        this.leadApplicationService = leadApplicationService;
    }
    
    @GetMapping
    public ResponseEntity<?> getLeads(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priorityLevel,
            @RequestParam(required = false) String companyType,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long agentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        LeadQuery query = new LeadQuery();
        query.setCountry(country);
        query.setStatuses(status != null ? status.split(",") : null);
        query.setPriorityLevel(priorityLevel);
        query.setCompanyType(companyType);
        query.setRegion(region);
        query.setKeyword(keyword);
        query.setAgentId(agentId);
        query.setPage(page);
        query.setSize(size);
        
        List<com.leadcrm.domain.lead.aggregate.Lead> leads = leadApplicationService.searchLeads(query);
        List<LeadDTO> leadDTOs = leads.stream().map(this::toDTO).collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(leadDTOs));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getLeadById(@PathVariable Long id) {
        return leadApplicationService.getLeadById(id)
                .map(lead -> ResponseEntity.ok(ApiResponse.success(toDTO(lead))))
                .orElse(ResponseEntity.ok(ApiResponse.error("Lead not found")));
    }
    
    @PostMapping
    public ResponseEntity<?> createLead(@RequestBody CreateLeadCommand command) {
        com.leadcrm.domain.lead.aggregate.Lead lead = leadApplicationService.createLead(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(lead)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLead(@PathVariable Long id, @RequestBody UpdateLeadCommand command) {
        command.setId(id);
        com.leadcrm.domain.lead.aggregate.Lead lead = leadApplicationService.updateLead(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(lead)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable Long id) {
        leadApplicationService.deleteLead(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    @PostMapping("/import")
    public ResponseEntity<?> importLeads(@RequestBody List<com.leadcrm.domain.lead.aggregate.Lead> leads) {
        int imported = leadApplicationService.importLeads(leads);
        return ResponseEntity.ok(ApiResponse.success(imported));
    }
    
    @GetMapping("/stats/by-country")
    public ResponseEntity<?> countByCountry() {
        List<Object[]> stats = leadApplicationService.countByCountry();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/by-status")
    public ResponseEntity<?> countByStatus() {
        List<Object[]> stats = leadApplicationService.countByStatus();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/by-priority")
    public ResponseEntity<?> countByPriorityLevel() {
        List<Object[]> stats = leadApplicationService.countByPriorityLevel();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/agent-summary")
    public ResponseEntity<?> getAgentSummary() {
        List<Object[]> summary = leadApplicationService.getAgentSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
    
    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        List<String> countries = leadApplicationService.getDistinctCountries();
        return ResponseEntity.ok(ApiResponse.success(countries));
    }
    
    private LeadDTO toDTO(com.leadcrm.domain.lead.aggregate.Lead lead) {
        LeadDTO dto = new LeadDTO();
        dto.setId(lead.getId());
        dto.setCompanyName(lead.getCompanyInfo().getCompanyName());
        dto.setCompanyType(lead.getCompanyInfo().getCompanyType());
        dto.setBusinessScope(lead.getCompanyInfo().getBusinessScope());
        dto.setWebsite(lead.getCompanyInfo().getWebsite());
        dto.setContactPhone(lead.getContactInfo().getContactPhone());
        dto.setContactEmail(lead.getContactInfo().getContactEmail());
        dto.setCountry(lead.getAddressInfo().getCountry());
        dto.setRegion(lead.getAddressInfo().getRegion());
        dto.setAddress(lead.getAddressInfo().getAddress());
        dto.setPriorityScore(lead.getPriority().getPriorityScore());
        dto.setPriorityLevel(lead.getPriority().getPriorityLevel().name());
        dto.setLeadSource(lead.getLeadSource().getLeadSource().name());
        dto.setLeadChannel(lead.getLeadSource().getLeadChannel());
        dto.setSourceUrl(lead.getLeadSource().getSourceUrl());
        dto.setNotes(lead.getNotes());
        dto.setStatus(lead.getStatus().name());
        dto.setAgentId(lead.getAgentId());
        dto.setFollowOperator(lead.getFollowOperator());
        dto.setCreatedAt(lead.getCreatedAt());
        dto.setUpdatedAt(lead.getUpdatedAt());
        return dto;
    }
}