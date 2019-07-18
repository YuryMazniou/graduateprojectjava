package ru.javawebinar.graduateprojectjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {
    private static final LocalTime time9=LocalTime.of(9,0);
    private static final LocalTime time11=LocalTime.of(11,0);

    public static boolean getTimeForUser(){
        LocalTime now=LocalTime.now();
        return now.isAfter(time9)&&now.isBefore(time11);
    }
    public static boolean getTimeForAdmin(){
        LocalTime now=LocalTime.now();
        return now.isBefore(time9);
    }
    public static LocalDate today(){
        return LocalDate.now();
    }
}
