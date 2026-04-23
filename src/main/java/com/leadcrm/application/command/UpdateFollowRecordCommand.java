package com.leadcrm.application.command;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateFollowRecordCommand {
    private Long id;
    private String contactMethod;
    private String contactResult;
    private String customerIntention;
    private String currentStage;
    private String notes;
    private String nextAction;
    private LocalDate nextActionDate;
}