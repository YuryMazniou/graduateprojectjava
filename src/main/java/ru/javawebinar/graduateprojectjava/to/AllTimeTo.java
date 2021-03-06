package ru.javawebinar.graduateprojectjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AllTimeTo {
    private final String description;
    private final LocalDate localDate;
    private final boolean excess;

    @JsonCreator
    public AllTimeTo(@JsonProperty("description") String description,@JsonProperty("localDate") LocalDate localDate,@JsonProperty("excess") boolean excess) {
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

    @Override
    public String toString() {
        return "AllTimeTo{" +
                "description='" + description + '\'' +
                ", localDate=" + localDate +
                ", excess=" + excess +
                '}';
    }
}
