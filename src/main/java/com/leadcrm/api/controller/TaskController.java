package com.leadcrm.api.controller;

import com.leadcrm.application.command.CreateTaskCommand;
import com.leadcrm.application.command.UpdateTaskCommand;
import com.leadcrm.application.query.TaskQuery;
import com.leadcrm.application.service.TaskApplicationService;
import com.leadcrm.api.dto.ApiResponse;
import com.leadcrm.api.dto.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agent/tasks")
public class TaskController {
    
    private final TaskApplicationService taskApplicationService;
    
    public TaskController(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }
    
    @GetMapping
    public ResponseEntity<?> getTasks(
            @RequestParam(required = false) Long agentId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long leadId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        TaskQuery query = new TaskQuery();
        query.setAgentId(agentId);
        query.setStatus(status);
        query.setPriority(priority);
        query.setLeadId(leadId);
        query.setPage(page);
        query.setSize(size);
        
        List<com.leadcrm.domain.task.aggregate.AgentTask> tasks = taskApplicationService.searchTasks(query);
        List<TaskDTO> taskDTOs = tasks.stream().map(this::toDTO).collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return taskApplicationService.getTaskById(id)
                .map(task -> ResponseEntity.ok(ApiResponse.success(toDTO(task))))
                .orElse(ResponseEntity.ok(ApiResponse.error("Task not found")));
    }
    
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskCommand command) {
        com.leadcrm.domain.task.aggregate.AgentTask task = taskApplicationService.createTask(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(task)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody UpdateTaskCommand command) {
        command.setId(id);
        com.leadcrm.domain.task.aggregate.AgentTask task = taskApplicationService.updateTask(command);
        return ResponseEntity.ok(ApiResponse.success(toDTO(task)));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        com.leadcrm.domain.task.aggregate.AgentTask task = taskApplicationService.changeTaskStatus(id, request.getStatus(), request.getOperatorId());
        return ResponseEntity.ok(ApiResponse.success(toDTO(task)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskApplicationService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingTasks() {
        TaskQuery query = new TaskQuery();
        query.setStatus("PENDING");
        List<com.leadcrm.domain.task.aggregate.AgentTask> tasks = taskApplicationService.searchTasks(query);
        List<TaskDTO> taskDTOs = tasks.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(taskDTOs));
    }
    
    @GetMapping("/stats/by-status")
    public ResponseEntity<?> countByStatus() {
        List<Object[]> stats = taskApplicationService.countByStatus();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/by-priority")
    public ResponseEntity<?> countByPriority() {
        List<Object[]> stats = taskApplicationService.countByPriority();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/agent-summary")
    public ResponseEntity<?> getAgentSummary() {
        List<Object[]> summary = taskApplicationService.getAgentSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
    
    private TaskDTO toDTO(com.leadcrm.domain.task.aggregate.AgentTask task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTaskInfo().getTitle());
        dto.setDescription(task.getTaskInfo().getDescription());
        dto.setStatus(task.getStatus().name());
        dto.setPriority(task.getPriority().name());
        dto.setAgentId(task.getAgentId());
        dto.setLeadId(task.getLeadId());
        dto.setDeadline(task.getDeadline());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setCompletedAt(task.getCompletedAt());
        dto.setCreatedBy(task.getCreatedBy());
        dto.setCompletedBy(task.getCompletedBy());
        return dto;
    }
    
    static class StatusUpdateRequest {
        private String status;
        private Long operatorId;
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public Long getOperatorId() {
            return operatorId;
        }
        
        public void setOperatorId(Long operatorId) {
            this.operatorId = operatorId;
        }
    }
}