package com.sgarlea.todo.service;

import com.sgarlea.todo.domain.Task;

import java.util.List;

public interface ITaskService {

    Task add(Task task);

    List<Task> findAll();

    Task find(String id);

    Task save(String id, Task task);

    void delete(String id);

    List<Task> findArchive();

    Task archive(String id);

    Task restore(String id);

    Task complete(String id);

}
