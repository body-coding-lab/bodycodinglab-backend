package com.bcl.fitmate.backend.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.format(FORMATTER) : null;
    }

    public static LocalDateTime parse(String date) {
        return (date != null && !date.isEmpty()) ? LocalDateTime.parse(date, FORMATTER) : null;
    }
}
