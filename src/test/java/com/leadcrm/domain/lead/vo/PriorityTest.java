package com.leadcrm.domain.lead.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PriorityTest {
    
    @Test
    public void testCreatePriorityWithHotLevel() {
        Priority priority = new Priority(90);
        assertEquals(90, priority.getPriorityScore());
        assertEquals(Priority.PriorityLevel.HOT, priority.getPriorityLevel());
    }
    
    @Test
    public void testCreatePriorityWithWarmLevel() {
        Priority priority = new Priority(60);
        assertEquals(60, priority.getPriorityScore());
        assertEquals(Priority.PriorityLevel.WARM, priority.getPriorityLevel());
    }
    
    @Test
    public void testCreatePriorityWithColdLevel() {
        Priority priority = new Priority(20);
        assertEquals(20, priority.getPriorityScore());
        assertEquals(Priority.PriorityLevel.COLD, priority.getPriorityLevel());
    }
    
    @Test
    public void testCreatePriorityWithBoundaryValues() {
        // Test hot boundary
        Priority priorityHot = new Priority(80);
        assertEquals(Priority.PriorityLevel.HOT, priorityHot.getPriorityLevel());
        
        // Test warm boundary
        Priority priorityWarm = new Priority(40);
        assertEquals(Priority.PriorityLevel.WARM, priorityWarm.getPriorityLevel());
        
        // Test cold boundary
        Priority priorityCold = new Priority(39);
        assertEquals(Priority.PriorityLevel.COLD, priorityCold.getPriorityLevel());
    }
}