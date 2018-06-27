package com.sgarlea.todo.controller;

import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/taskservice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaskController {

    private ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "/tasks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task addTask(@Valid @RequestBody Task task) {
        return taskService.add(task);
    }

    @GetMapping(path = "/tasks")
    public List<Task> listTasks() {
        return taskService.findAll();
    }

    @GetMapping(path = "/archive")
    public List<Task> listArchive() {
        return taskService.findArchive();
    }

    @GetMapping(path = "/tasks/{id}")
    public Task listTask(@PathVariable String id) {
        return taskService.find(id);
    }

    @PutMapping(path = "/tasks/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task saveTask(@PathVariable String id, @Valid @RequestBody Task task) {
        return taskService.save(id, task);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public void removeTask(@PathVariable String id) {
        taskService.delete(id);
    }

    @PutMapping(path = "/tasks/{id}/archive")
    public Task archiveTask(@PathVariable String id) {
        return taskService.archive(id);
    }

    @PutMapping(path = "/tasks/{id}/restore")
    public Task restoreTask(@PathVariable String id) {
        return taskService.restore(id);
    }

    @PutMapping(path = "/tasks/{id}/complete")
    public Task completeTask(@PathVariable String id) {
        return taskService.complete(id);
    }

}
