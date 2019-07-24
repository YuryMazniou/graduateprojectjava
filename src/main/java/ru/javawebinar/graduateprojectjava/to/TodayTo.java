package ru.javawebinar.graduateprojectjava.to;

import java.util.Objects;

public class TodayTo {
    private final Integer count;
    private final String description;
    private final boolean excess;

    public TodayTo(Integer count, String description,boolean excess) {
        this.count = count;
        this.description = description;
        this.excess=excess;
    }

    public Integer getCount() {
        return count;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "TodayTo{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", excess=" + excess +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodayTo todayTo = (TodayTo) o;
        return excess == todayTo.excess &&
                Objects.equals(count, todayTo.count) &&
                Objects.equals(description, todayTo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, description, excess);
    }
}
