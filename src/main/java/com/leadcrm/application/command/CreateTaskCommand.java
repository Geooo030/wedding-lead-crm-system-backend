package com.leadcrm.application.command;

import lombok.Data;

@Data
public class CreateTaskCommand {
    private String title;
    private String description;
    private String priority;
    private Long agentId;
    private Long leadId;
    private String deadline;
    private Long createdBy;
}