package ru.javawebinar.graduateprojectjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DateTime {
    boolean checkTimeForSaveStatistic();
    boolean getTimeForUser();
    boolean getTimeForAdmin();
    boolean getTimeForStatistic();
    LocalDate today();
    default void setLocalDate(LocalDate localDate){localDate=null;};
    default void setLocalTime(LocalTime localTime){localTime=null;};
    default LocalDate getLocalDate(){return null;};
    default LocalTime getLocalTime(){return null;};
}
