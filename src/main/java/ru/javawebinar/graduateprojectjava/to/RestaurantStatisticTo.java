package ru.javawebinar.graduateprojectjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class RestaurantStatisticTo {
    private final List<DishTo> listOfDish;
    private final Integer count;
    private final LocalDate votesDate;

    @JsonCreator
    public RestaurantStatisticTo(@JsonProperty("listOfDish") List<DishTo> listOfDish,
                                 @JsonProperty("count") Integer count,@JsonProperty("votesDate") LocalDate votesDate) {
        this.listOfDish = listOfDish;
        this.count = count;
        this.votesDate = votesDate;
    }

    public List<DishTo> getListOfDish() {
        return listOfDish;
    }

    public Integer getCount() {
        return count;
    }

    public LocalDate getVotesDate() {
        return votesDate;
    }

    @Override
    public String toString() {
        return "RestaurantStatisticTo{" +
                "listOfDish=" + listOfDish +
                ", count=" + count +
                ", votesDate=" + votesDate +
                '}';
    }
}
