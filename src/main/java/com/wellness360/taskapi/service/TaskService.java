package com.wellness360.taskapi.service;

import com.wellness360.taskapi.exception.TaskNotFoundException;
import com.wellness360.taskapi.model.Task;
import com.wellness360.taskapi.model.TaskRequest;
import com.wellness360.taskapi.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Task> findAll() {
        return tasks.values().stream().sorted(Comparator.comparing(Task::getId)).toList();
    }

    public Task findById(Long id) {
        Task task = tasks.get(id);
        if (task == null) throw new TaskNotFoundException(id);
        return task;
    }

    public Task create(TaskRequest request) {
        Task task = new Task();
        Instant now = Instant.now();
        task.setId(idGenerator.getAndIncrement());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setStatus(request.getStatus() == null ? TaskStatus.pending : request.getStatus());
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        tasks.put(task.getId(), task);
        return task;
    }

    public Task update(Long id, TaskRequest request) {
        Task existing = findById(id);
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setDueDate(request.getDueDate());
        existing.setStatus(request.getStatus() == null ? existing.getStatus() : request.getStatus());
        existing.setUpdatedAt(Instant.now());
        return existing;
    }

    public void delete(Long id) {
        if (tasks.remove(id) == null) throw new TaskNotFoundException(id);
    }

    public Task markComplete(Long id) {
        Task task = findById(id);
        task.setStatus(TaskStatus.completed);
        task.setUpdatedAt(Instant.now());
        return task;
    }
}
