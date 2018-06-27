package com.sgarlea.todo.client;

import com.sgarlea.todo.domain.Task;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "task-service")
@RibbonClient(name = "task-service")
@RequestMapping(path = "/notificationservice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface NotificationClient {

    @RequestMapping(method = RequestMethod.POST, path = "/notifications", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object createNotification(Task task);

    @RequestMapping(method = RequestMethod.PUT, path = "/notifications/{taskId}")
    Object updateNotification(@PathVariable String taskId, Task task);

    @RequestMapping(method = RequestMethod.DELETE, path = "/notifications/{taskId}")
    void deleteNotification(@PathVariable String taskId);

    @RequestMapping(method = RequestMethod.PUT, path = "/notifications/{taskId}/activate")
    Object activateNotification(@PathVariable String taskId);

    @RequestMapping(method = RequestMethod.PUT, path = "/notifications/{taskId}/deactivate")
    Object deactivateNotification(@PathVariable String taskId);

}
