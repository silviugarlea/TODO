package com.sgarlea.todo.service.validation;

import com.sgarlea.todo.domain.Task;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TaskValidator {

    private static final Date MINIMUM_START_DATE = initializeStartDate();

    public Boolean hasValidDueDate(Task task) {
        Date dueDate = task.getDueDate();
        if (dueDate != null && dueDate.before(MINIMUM_START_DATE)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private static Date initializeStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.MAY, 25, 16, 0, 0);
        return calendar.getTime();
    }

}
