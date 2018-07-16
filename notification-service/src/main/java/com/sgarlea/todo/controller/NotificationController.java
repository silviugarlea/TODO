package com.sgarlea.todo.controller;

import com.sgarlea.todo.controller.adapter.NotificationAdapter;
import com.sgarlea.todo.controller.resources.NotificationResource;
import com.sgarlea.todo.domain.Notification;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/notificationservice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NotificationController {

    private final INotificationService notificationService;

    private final NotificationAdapter adapter;

    @Autowired
    public NotificationController(INotificationService notificationService, NotificationAdapter adapter) {
        this.notificationService = notificationService;
        this.adapter = adapter;
    }

    @PostMapping(path = "/notifications", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<NotificationResource> createNotification(@Valid @RequestBody Task task) {
        Notification notification = notificationService.add(task);
        NotificationResource resource = adapter.createResource(notification);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping(path = "/notifications/{taskId}")
    public ResponseEntity<NotificationResource> updateNotification(@PathVariable String taskId, @RequestBody Task task) {
        Notification notification = notificationService.save(taskId, task);
        NotificationResource resource = adapter.createResource(notification);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping(path = "/notifications/{taskId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String taskId) {
        notificationService.remove(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/notifications/{taskId}/activate")
    public ResponseEntity<NotificationResource> activateNotification(@PathVariable String taskId) {
        Notification notification = notificationService.toggleActivation(taskId, Boolean.TRUE);
        NotificationResource resource = adapter.createResource(notification);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping(path = "/notifications/{taskId}/deactivate")
    public ResponseEntity<NotificationResource> deactivateNotification(@PathVariable String taskId) {
        Notification notification = notificationService.toggleActivation(taskId, Boolean.FALSE);
        NotificationResource resource = adapter.createResource(notification);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
