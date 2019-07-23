package ru.javawebinar.graduateprojectjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {
    private static final LocalTime TIME_9 =LocalTime.of(9,0);
    private static final LocalTime TIME_11 =LocalTime.of(11,0);
    private static final LocalTime TIME_1101 =LocalTime.of(11,1);
    private static final LocalTime TIME_2359 =LocalTime.of(23,59);
    private static boolean flagTest=false;
    private static LocalDate localDate;

    public static synchronized boolean checkTimeForSaveStatistic(){
        LocalDate now=LocalDate.now();
        if(localDate.equals(now))return false;
        localDate=now;
        return true;
    }
    public static boolean getTimeForUser(){
        LocalTime now=LocalTime.now();
        if(!isFlagTest())
            return now.isAfter(TIME_9)&&now.isBefore(TIME_11);
        else
            return true;
    }
    public static boolean getTimeForAdmin(){
        LocalTime now=LocalTime.now();
        if(!isFlagTest())
            return now.isBefore(TIME_9);
        else
            return true;
    }
    public static boolean getTimeForStatistic(){
        LocalTime now=LocalTime.now();
        if(!isFlagTest())
            return now.isAfter(TIME_1101)&&now.isBefore(TIME_2359);
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
