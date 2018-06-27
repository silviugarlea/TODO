package com.sgarlea.todo.controller;

import com.sgarlea.todo.domain.Notification;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/notificationservice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NotificationController {

    private INotificationService notificationService;

    @Autowired
    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(path = "/notifications", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Notification createNotification(@Valid @RequestBody Task task) {
        return notificationService.add(task);
    }

    @PutMapping(path = "/notifications/{taskId}")
    public Notification updateNotification(@PathVariable String taskId, @RequestBody Task task) {
        return notificationService.save(taskId, task);
    }

    @DeleteMapping(path = "/notifications/{taskId}")
    public void deleteNotification(@PathVariable String taskId) {
        notificationService.remove(taskId);
    }

    @PutMapping(path = "/notifications/{taskId}/activate")
    public Notification activateNotification(@PathVariable String taskId) {
        return notificationService.toggleActivation(taskId, Boolean.TRUE);
    }

    @PutMapping(path = "/notifications/{taskId}/deactivate")
    public Notification deactivateNotification(@PathVariable String taskId) {
        return notificationService.toggleActivation(taskId, Boolean.FALSE);
    }

}
