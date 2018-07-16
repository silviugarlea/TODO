package com.sgarlea.todo.controller.resources;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

public class TaskResource extends ResourceSupport {

    private String taskId;

    @NotNull
    @Length(min = 1, max = 30)
    private String title;

    @Length(max = 20000)
    private String description;

    @NotNull
    private String status;

    private String dueDate;

    public String getTaskId() {
        return taskId;
    }

    public TaskResource setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TaskResource setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskResource setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TaskResource setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public TaskResource setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

}