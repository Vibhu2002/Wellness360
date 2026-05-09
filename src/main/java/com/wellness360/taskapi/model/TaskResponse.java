package com.wellness360.taskapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        @JsonProperty("due_date") LocalDate dueDate,
        TaskStatus status,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(),
                task.getDueDate(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt());
    }
}
