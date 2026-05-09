package com.wellness360.taskapi.service;

import com.wellness360.taskapi.model.TaskRequest;
import com.wellness360.taskapi.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskServiceTest {

    @Test
    void markCompleteShouldSetCompletedStatus() {
        TaskService service = new TaskService();
        TaskRequest request = new TaskRequest();
        request.setTitle("Pay bills");
        request.setDescription("Utility bills");
        request.setDueDate(LocalDate.now().plusDays(1));
        request.setStatus(TaskStatus.pending);

        Long id = service.create(request).getId();
        service.markComplete(id);

        assertEquals(TaskStatus.completed, service.findById(id).getStatus());
    }
}
