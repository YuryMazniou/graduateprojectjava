package ru.javawebinar.graduateprojectjava.to;

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
}
