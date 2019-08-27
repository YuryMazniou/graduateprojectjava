package ru.javawebinar.graduateprojectjava;

import ru.javawebinar.graduateprojectjava.util.DateTime;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeTest implements DateTime {
    private static final LocalTime TIME_9 =LocalTime.of(9,0);
    private static final LocalTime TIME_11 =LocalTime.of(11,0);
    private static final LocalTime TIME_1101 =LocalTime.of(11,1);
    private static final LocalTime TIME_2359 =LocalTime.of(23,59);

    private LocalDate localDate=LocalDate.of(1,1,1);
    private LocalTime localTime=LocalTime.of(0,0,1);

    @Override
    public synchronized boolean checkTimeForSaveStatistic(){
        LocalDate now=getLocalDate();
        if(localDate.equals(now))return false;
        localDate=now;
        return true;
    }

    @Override
    public boolean getTimeForUser(){
        LocalTime now=getLocalTime();
        return now.isAfter(TIME_9)&&now.isBefore(TIME_11);
    }

    @Override
    public boolean getTimeForAdmin(){
        LocalTime now=getLocalTime();
        return now.isBefore(TIME_9);
    }

    @Override
    public boolean getTimeForStatistic(){
        LocalTime now=getLocalTime();
        return now.isAfter(TIME_1101)&&now.isBefore(TIME_2359);
    }

    @Override
    public LocalDate today(){
        return getLocalDate();
    }

    @Override
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public LocalTime getLocalTime() {
        return localTime;
    }
}
