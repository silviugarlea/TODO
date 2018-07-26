package com.sgarlea.todo.domain;

import java.util.Arrays;
import java.util.Optional;

public enum TaskStatus {

    ACTIVE,
    COMPLETED,
    ARCHIVED;

    public static Optional<TaskStatus> retrieve(String status) {
        return Arrays.stream(TaskStatus.values()).
                filter(s -> s.name().equals(status))
                .findFirst();
    }
}
