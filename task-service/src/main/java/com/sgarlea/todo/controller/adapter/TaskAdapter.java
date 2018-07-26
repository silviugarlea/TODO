package com.sgarlea.todo.controller.adapter;

import com.sgarlea.todo.controller.resources.TaskResource;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.domain.TaskStatus;
import com.sgarlea.todo.exception.InvalidResourceException;
import com.sgarlea.todo.service.util.DateAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskAdapter {

    private DateAdapter dateAdapter;

    @Autowired
    public TaskAdapter(DateAdapter dateAdapter) {
        this.dateAdapter = dateAdapter;
    }

    public TaskResource createResource(Task task) {
        return new TaskResource().setTaskId(task.getId())
                .setTitle(task.getTitle())
                .setDescription(task.getDescription())
                .setStatus(task.getStatus().name())
                .setDueDate(dateAdapter.format(task.getDueDate()));
    }

    public Task createEntity(TaskResource resource) {
        TaskStatus status = TaskStatus.retrieve(resource.getStatus()).orElseThrow(() -> new InvalidResourceException("Invalid status."));
        return new Task().setId(resource.getTaskId())
                .setTitle(resource.getTitle())
                .setDescription(resource.getDescription())
                .setStatus(status)
                .setDueDate(dateAdapter.parse(resource.getDueDate()));
    }

}
