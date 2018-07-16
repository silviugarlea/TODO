package com.sgarlea.todo.service.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateAdapter {

    private static final String DEFAULT_DATE_FORMAT = "";

    public Long computeDaysBetween(Date first, Date second) {
        Long timeDifference = first.getTime() - second.getTime();
        return timeDifference / (1000 * 60 * 60 * 24);
    }

    public Date parse(String dateString, String format) {
        if (dateString == null) {
            return null;
        }
        if (format == null) {
            throw new RuntimeException("ana are mere");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("ana are mere");
        }
    }

    public Date parse(String dateString) {
        return parse(dateString, DEFAULT_DATE_FORMAT);
    }

    public String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null) {
            throw new RuntimeException("ana are mere");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public String format(Date date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

}
