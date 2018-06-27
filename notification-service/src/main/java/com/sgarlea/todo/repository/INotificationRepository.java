package com.sgarlea.todo.repository;

import com.sgarlea.todo.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface INotificationRepository extends MongoRepository<Notification, String> {

    @Query("{ 'task.id' : ?0 }")
    Notification findByTaskId(String taskId);

}
