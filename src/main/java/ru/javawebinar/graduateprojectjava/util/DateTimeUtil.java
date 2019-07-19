package ru.javawebinar.graduateprojectjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {
    private static final LocalTime time9=LocalTime.of(9,0);
    private static final LocalTime time11=LocalTime.of(11,0);
    private static boolean flagTest=false;

    public static boolean getTimeForUser(){
        LocalTime now=LocalTime.now();
        if(!isFlagTest())
            return now.isAfter(time9)&&now.isBefore(time11);
        else
            return true;
    }
    public static boolean getTimeForAdmin(){
        LocalTime now=LocalTime.now();
        if(!isFlagTest())
            return now.isBefore(time9);
        else
            return true;
    }
    public static LocalDate today(){
        if(!isFlagTest())
            return LocalDate.now();
        else
            return LocalDate.of(2019,7,3);
    }
    private static boolean isFlagTest(){
        return flagTest;
    }

    public static void testOn(){
        flagTest=true;
    }
}
