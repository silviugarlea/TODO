package com.sgarlea.todo.service.impl;

import com.sgarlea.todo.client.NotificationClient;
import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.domain.TaskStatus;
import com.sgarlea.todo.repository.ITaskRepository;
import com.sgarlea.todo.service.ITaskService;
import com.sgarlea.todo.service.validation.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    private ITaskRepository taskRepository;

    private TaskValidator taskValidator;

    private NotificationClient notificationClient;

    @Autowired
    public TaskService(ITaskRepository taskRepository, TaskValidator taskValidator, NotificationClient notificationClient) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.notificationClient = notificationClient;
    }

    @Override
    public Task add(Task task) {
        if (taskValidator.hasValidDueDate(task)) {
            Task newTask = taskRepository.insert(task);
            if (shouldNotify(task)) {
                notificationClient.createNotification(newTask);
            }
            return newTask;
        }
        return null;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAllByStatusIsNot(TaskStatus.ARCHIVED);
    }

    @Override
    public List<Task> findArchive() {
        return taskRepository.findAllByStatus(TaskStatus.ARCHIVED);
    }

    @Override
    public Task find(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task save(String id, Task task) {
        if (taskValidator.hasValidDueDate(task) && taskRepository.findById(id).isPresent()) {
            Task updatedTask = taskRepository.save(task);
            notificationClient.updateNotification(id, task);
            return updatedTask;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        taskRepository.deleteById(id);
        notificationClient.deleteNotification(id);
    }

    @Override
    public Task archive(String id) {
        Task task = updateStatus(id, TaskStatus.ARCHIVED);
        notificationClient.deactivateNotification(id);
        return task;
    }

    @Override
    public Task restore(String id) {
        Task task = updateStatus(id, TaskStatus.ACTIVE);
        if (task != null && shouldNotify(task)) {
            notificationClient.activateNotification(id);
        }
        return task;
    }

    @Override
    public Task complete(String id) {
        Task task = updateStatus(id, TaskStatus.COMPLETED);
        notificationClient.deactivateNotification(id);
        return task;
    }

    private Task updateStatus(String id, TaskStatus status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (!TaskStatus.COMPLETED.equals(task.getStatus())) {
                task.setStatus(status);
            }
            return taskRepository.save(task);
        }
        return null;
    }

    private Boolean shouldNotify(Task task) {
        Long daysTillNotification = calculateDaysBetween(task.getDueDate(), new Date());
        return daysTillNotification <= 3;
    }

    private Long calculateDaysBetween(Date first, Date second) {
        Long timeDifference = first.getTime() - second.getTime();
        return timeDifference / (1000 * 60 * 60 * 24);
    }

}
