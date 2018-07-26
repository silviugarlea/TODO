package com.sgarlea.todo.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sgarlea.todo.client.NotificationClient;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.domain.TaskStatus;
import com.sgarlea.todo.exception.InvalidResourceException;
import com.sgarlea.todo.exception.NoResourceException;
import com.sgarlea.todo.exception.ServiceUnavaibleException;
import com.sgarlea.todo.repository.ITaskRepository;
import com.sgarlea.todo.service.ITaskService;
import com.sgarlea.todo.service.util.DateAdapter;
import com.sgarlea.todo.service.validation.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    private final TaskValidator taskValidator;

    private final NotificationClient notificationClient;

    private final DateAdapter dateAdapter;

    @Autowired
    public TaskService(ITaskRepository taskRepository, TaskValidator taskValidator, NotificationClient notificationClient, DateAdapter dateAdapter) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.notificationClient = notificationClient;
        this.dateAdapter = dateAdapter;
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultAdd")
    public Task add(Task task) {
        taskValidator.validate(task);
        Task newTask = taskRepository.insert(task);
        if (shouldNotify(task)) {
            notificationClient.createNotification(newTask);
        }
        return newTask;
    }

    private Task defaultAdd(Task task) {
        throw new ServiceUnavaibleException("Service unavailable. Cannot add task.");
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAllByStatusIsNot(TaskStatus.ARCHIVED);
    }

    @Override
    public Task find(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoResourceException("Task not found: " + id));
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultSave")
    public Task save(String id, Task task) {
        taskRepository.findById(id).orElseThrow(() -> new NoResourceException("Task not found: " + id));
        taskValidator.validate(task);
        Task updatedTask = taskRepository.save(task);
        notificationClient.updateNotification(id, task);
        return updatedTask;
    }

    private Task defaultSave(String id, Task task) {
        throw new ServiceUnavaibleException("Service unavailable. Cannot save task.");
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultDelete")
    public void delete(String id) {
        taskRepository.deleteById(id);
        notificationClient.deleteNotification(id);
    }

    private void defaultDelete(String id) {
        throw new ServiceUnavaibleException("Service unavailable. Cannot delete task.");
    }

    @Override
    public List<Task> findArchive() {
        return taskRepository.findAllByStatus(TaskStatus.ARCHIVED);
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultChangeStatus")
    public Task archive(String id) {
        Task task = updateStatus(id, TaskStatus.ARCHIVED);
        notificationClient.deactivateNotification(id);
        return task;
    }

    private Task defaultChangeStatus(String id) {
        throw new ServiceUnavaibleException("Service unavailable. Cannat archive task.");
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultChangeStatus")
    public Task restore(String id) {
        Task task = updateStatus(id, TaskStatus.ACTIVE);
        if (shouldNotify(task)) {
            notificationClient.activateNotification(id);
        }
        return task;
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultChangeStatus")
    public Task complete(String id) {
        Task task = updateStatus(id, TaskStatus.COMPLETED);
        notificationClient.deactivateNotification(id);
        return task;
    }

    private Task updateStatus(String id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoResourceException("Task not found: " + id));
        if (!TaskStatus.COMPLETED.equals(task.getStatus())) {
            task.setStatus(status);
        }
        return taskRepository.save(task);
    }

    private Boolean shouldNotify(Task task) {
        if (task.getDueDate() == null) {
            return Boolean.FALSE;
        }
        Long daysTillNotification = dateAdapter.computeDaysBetween(task.getDueDate(), new Date());
        return daysTillNotification <= 3;
    }

}
