package com.sgarlea.todo.repository;

import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.domain.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByStatusIsNot(TaskStatus status);

    List<Task> findAllByStatus(TaskStatus status);

}
