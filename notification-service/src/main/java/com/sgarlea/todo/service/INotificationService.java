package com.sgarlea.todo.service;

import com.sgarlea.todo.domain.Notification;
import com.sgarlea.todo.domain.Task;

public interface INotificationService {

    Notification add(Task task);

    Notification save(String taskId, Task task);

    void remove(String taskId);

    Notification toggleActivation(String taskId, Boolean active);

    void removeAll();
}
