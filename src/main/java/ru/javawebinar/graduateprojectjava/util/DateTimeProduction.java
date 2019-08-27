package ru.javawebinar.graduateprojectjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeProduction implements DateTime {
    private static final LocalTime TIME_9 =LocalTime.of(9,0);
    private static final LocalTime TIME_11 =LocalTime.of(11,0);
    private static final LocalTime TIME_1101 =LocalTime.of(11,1);
    private static final LocalTime TIME_2359 =LocalTime.of(23,59);

    private static LocalDate localDate=LocalDate.of(1,1,1);

    @Override
    public synchronized boolean checkTimeForSaveStatistic(){
        LocalDate now=LocalDate.now();
        if(localDate.equals(now))return false;
        localDate=now;
        return true;
    }

    @Override
    public boolean getTimeForUser(){
        LocalTime now=LocalTime.now();
        return now.isAfter(TIME_9)&&now.isBefore(TIME_11);
    }

    @Override
    public boolean getTimeForAdmin(){
        LocalTime now=LocalTime.now();
        return now.isBefore(TIME_9);
    }

    @Override
    public boolean getTimeForStatistic(){
        LocalTime now=LocalTime.now();
        return now.isAfter(TIME_1101)&&now.isBefore(TIME_2359);
    }

    @Override
    public LocalDate today(){
        return LocalDate.now();
    }
}
