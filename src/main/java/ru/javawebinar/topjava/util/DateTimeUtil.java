package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        public static <T extends Comparable<T>>  boolean isBetween(T ld, T s, T end) {
            return ld.compareTo(s) >= 0 && ld.compareTo(end) <= 0;
    }


//    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
//    }
//
//    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
//        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
//    }



    //todo ==>>   4: Проверку isBetweenDate сделать в DateTimeUtil.
    // Попробуйте использовать дженерики и объединить ее с isBetweenTime (см. Generics Tutorials)

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }


}

