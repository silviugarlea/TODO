package com.sgarlea.todo.service.impl;

import com.sgarlea.todo.domain.Notification;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.exception.NoResourceException;
import com.sgarlea.todo.repository.INotificationRepository;
import com.sgarlea.todo.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {

    private INotificationRepository notificationRepository;

    @Autowired
    public NotificationService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification add(Task task) {
        Notification notification = new Notification().setTask(task);
        return notificationRepository.insert(notification);
    }

    @Override
    public Notification save(String taskId, Task task) {
        Notification notification = notificationRepository.findByTaskId(taskId);
        if (notification != null) {
            notification.setTask(task);
            return notificationRepository.save(notification);
        }
        throw new NoResourceException("No notification for task: " + taskId);
    }

    @Override
    public void remove(String taskId) {
        Notification notification = notificationRepository.findByTaskId(taskId);
        if (notification != null) {
            notificationRepository.delete(notification);
        }
        throw new NoResourceException("No notification for taskL " + taskId);
    }

    @Override
    public Notification toggleActivation(String taskId, Boolean active) {
        Notification notification = notificationRepository.findByTaskId(taskId);
        if (notification != null) {
            notification.setActive(active);
            notificationRepository.save(notification);
            return notification;
        }
        throw new NoResourceException("No notification for taskL " + taskId);
    }
}
