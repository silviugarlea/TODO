package com.sgarlea.todo.controller.resources;

public class NotificationResource {

    private String notificationId;
    private String taskId;
    private Boolean active;

    public String getNotificationId() {
        return notificationId;
    }

    public NotificationResource setNotificationId(String notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public NotificationResource setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public NotificationResource setActive(Boolean active) {
        this.active = active;
        return this;
    }

}
