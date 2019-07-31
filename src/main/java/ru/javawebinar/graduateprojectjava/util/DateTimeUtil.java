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
    private static LocalTime localTime;

    public static LocalTime getLocalTimeNow(){
        if(!isFlagTest())return LocalTime.now();
        else return localTime;
    }
    public static LocalDate getLocalDateNow(){
        if(!isFlagTest())return LocalDate.now();
        else return localDate;
    }
    public static synchronized boolean checkTimeForSaveStatistic(){
        LocalDate now=getLocalDateNow();
        if(localDate.equals(now))return false;
        localDate=now;
        return true;
    }
    public static boolean getTimeForUser(){
        LocalTime now=getLocalTimeNow();
        return now.isAfter(TIME_9)&&now.isBefore(TIME_11);
    }
    public static boolean getTimeForAdmin(){
        LocalTime now=getLocalTimeNow();
        return now.isBefore(TIME_9);
    }
    public static boolean getTimeForStatistic(){
        LocalTime now=getLocalTimeNow();
        return now.isAfter(TIME_1101)&&now.isBefore(TIME_2359);
    }
    public static LocalDate today(){
        if(!isFlagTest())
            return LocalDate.now();
        else
            return getLocalDateNow();
    }
    private static boolean isFlagTest(){
        return flagTest;
    }

    public static void testOn(){
        flagTest=true;
    }

    public static void setLocalDate(LocalDate localDate) {
        DateTimeUtil.localDate = localDate;
    }

    public static void setLocalTime(LocalTime localTime) {
        DateTimeUtil.localTime = localTime;
    }
}
