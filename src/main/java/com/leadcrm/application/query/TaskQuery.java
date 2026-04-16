package com.leadcrm.application.query;

import lombok.Data;

@Data
public class TaskQuery {
    private Long agentId;
    private String status;
    private String priority;
    private Long leadId;
    private int page;
    private int size;
}