package com.leadcrm.controller;

import com.leadcrm.dto.ApiResponse;
import com.leadcrm.entity.AgentTask;
import com.leadcrm.service.AgentTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agent/tasks")
public class AgentTaskController {
    
    private final AgentTaskService agentTaskService;
    
    public AgentTaskController(AgentTaskService agentTaskService) {
        this.agentTaskService = agentTaskService;
    }
    
    @GetMapping
    public ResponseEntity<?> getTasks() {
        List<AgentTask> tasks = agentTaskService.getRecentTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }
    
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody AgentTask task) {
        AgentTask saved = agentTaskService.createTask(task);
        return ResponseEntity.ok(ApiResponse.success("任务创建成功", saved));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        AgentTask task = agentTaskService.findById(id);
        if (task == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("任务不存在"));
        }
        return ResponseEntity.ok(ApiResponse.success(task));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestBody AgentTask task) {
        AgentTask existing = agentTaskService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("任务不存在"));
        }
        existing.setStatus(task.getStatus());
        existing.setResult(task.getResult());
        existing.setErrorMessage(task.getErrorMessage());
        
        if (task.getStatus() == AgentTask.TaskStatus.running) {
            existing.setStartedAt(java.time.LocalDateTime.now());
        } else if (task.getStatus() == AgentTask.TaskStatus.completed || 
                   task.getStatus() == AgentTask.TaskStatus.failed) {
            existing.setCompletedAt(java.time.LocalDateTime.now());
        }
        
        AgentTask saved = agentTaskService.save(existing);
        return ResponseEntity.ok(ApiResponse.success("状态更新成功", saved));
    }
    
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingTasks() {
        List<AgentTask> tasks = agentTaskService.getPendingTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }
}