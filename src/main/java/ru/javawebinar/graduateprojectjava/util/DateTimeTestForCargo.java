package ru.javawebinar.graduateprojectjava.util;

import java.time.LocalDate;

public class DateTimeTestForCargo implements DateTime {
    @Override
    public boolean checkTimeForSaveStatistic() {
        return true;
    }

    @Override
    public boolean getTimeForUser() {
        return true;
    }

    @Override
    public boolean getTimeForAdmin() {
        return true;
    }

    @Override
    public boolean getTimeForStatistic() {
        return true;
    }

    @Override
    public LocalDate today() {
        return LocalDate.of(2019,7,3);
    }
}
