package com.leadcrm.domain.lead.vo;

import lombok.Value;

@Value
public class Priority {
    private int priorityScore;
    private PriorityLevel priorityLevel;
    
    public Priority(int priorityScore) {
        this.priorityScore = priorityScore;
        this.priorityLevel = calculatePriorityLevel(priorityScore);
    }
    
    private PriorityLevel calculatePriorityLevel(int score) {
        if (score >= 80) {
            return PriorityLevel.HOT;
        } else if (score >= 40) {
            return PriorityLevel.WARM;
        } else {
            return PriorityLevel.COLD;
        }
    }
    
    public enum PriorityLevel {
        HOT, WARM, COLD
    }
}