package com.leadcrm.application.command;

import lombok.Data;

@Data
public class UpdateTaskCommand {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String deadline;
}