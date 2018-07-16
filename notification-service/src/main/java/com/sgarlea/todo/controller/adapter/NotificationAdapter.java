package com.sgarlea.todo.controller.adapter;

import com.sgarlea.todo.controller.resources.NotificationResource;
import com.sgarlea.todo.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationAdapter {

    public NotificationResource createResource(Notification notification) {
        return new NotificationResource().setNotificationId(notification.getId())
                .setTaskId(notification.getTask().getId())
                .setActive(notification.getActive());
    }

}
