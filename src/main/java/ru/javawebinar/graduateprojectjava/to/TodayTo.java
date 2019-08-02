package ru.javawebinar.graduateprojectjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TodayTo {
    private final Integer count;
    private final String description;
    private final boolean excess;

    @JsonCreator
    public TodayTo(@JsonProperty("count") Integer count,@JsonProperty("description") String description,@JsonProperty("excess") boolean excess) {
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
}
