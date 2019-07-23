package ru.javawebinar.graduateprojectjava.to;

import java.time.LocalDate;

public class AllTimeTo {
    private final String description;
    private final LocalDate localDate;
    private final boolean excess;

    public AllTimeTo(String description, LocalDate localDate,boolean excess) {
        this.description = description;
        this.localDate = localDate;
        this.excess = excess;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public boolean isExcess() {
        return excess;
    }
}
