package com.sgarlea.todo.controller;

import com.sgarlea.todo.controller.adapter.TaskAdapter;
import com.sgarlea.todo.controller.resources.TaskResource;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/taskservice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaskController {

    private ITaskService taskService;

    private TaskAdapter adapter;

    @Autowired
    public TaskController(ITaskService taskService, TaskAdapter adapter) {
        this.taskService = taskService;
        this.adapter = adapter;
    }

    @PostMapping(path = "/tasks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TaskResource> addTask(@Valid @RequestBody final TaskResource resource) {
        Task newTask = taskService.add(adapter.createEntity(resource));
        TaskResource newResource = adapter.createResource(newTask);
        return new ResponseEntity<>(newResource, HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<Collection<TaskResource>> listTasks() {
        List<Task> tasks = taskService.findAll();
        List<TaskResource> resources = tasks.stream()
                .map(adapter::createResource)
                .collect(Collectors.toList());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskResource> listTask(@PathVariable final String id) {
        Task task = taskService.find(id);
        TaskResource resource = adapter.createResource(task);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping(path = "/tasks/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TaskResource> saveTask(@PathVariable final String id, @Valid @RequestBody final TaskResource resource) {
        Task updatedTask = taskService.save(id, adapter.createEntity(resource));
        TaskResource updatedResource = adapter.createResource(updatedTask);
        return new ResponseEntity<>(updatedResource, HttpStatus.OK);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable final String id) {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/archive")
    public ResponseEntity<Collection<TaskResource>> listArchive() {
        List<Task> archive = taskService.findArchive();
        List<TaskResource> resources = archive.stream()
                .map(adapter::createResource)
                .collect(Collectors.toList());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PutMapping(path = "/tasks/{id}/archive")
    public ResponseEntity<TaskResource> archiveTask(@PathVariable final String id) {
        Task archived = taskService.archive(id);
        TaskResource resource = adapter.createResource(archived);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping(path = "/tasks/{id}/restore")
    public ResponseEntity<TaskResource> restoreTask(@PathVariable final String id) {
        Task restored = taskService.restore(id);
        TaskResource resource = adapter.createResource(restored);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping(path = "/tasks/{id}/complete")
    public ResponseEntity<TaskResource> completeTask(@PathVariable final String id) {
        Task completed = taskService.complete(id);
        TaskResource resource = adapter.createResource(completed);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping(path = "delete-all")
    public ResponseEntity<Void> deleteAll() {
        taskService.removeAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
