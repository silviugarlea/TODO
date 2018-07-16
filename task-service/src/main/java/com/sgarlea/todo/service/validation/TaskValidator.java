package com.sgarlea.todo.service.validation;

import com.sgarlea.todo.domain.Task;
import com.sgarlea.todo.exception.InvalidResourceException;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TaskValidator {

    public void validate(Task task) {
        validateDueDate(task.getDueDate());
    }

    private void validateDueDate(Date dueDate) {
        if (dueDate != null && dueDate.before(new Date())) {
            throw new InvalidResourceException("Due date is in the past.");
        }
    }

}
