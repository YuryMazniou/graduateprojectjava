package ru.javawebinar.graduateprojectjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.javawebinar.graduateprojectjava.model.Dish;

import java.util.List;

public class RestaurantForVoteTo {
    private final int restaurant_id;
    private final String description;
    private final List<DishTo> list_of_dish;

    @JsonCreator
    public RestaurantForVoteTo(@JsonProperty("restaurant_id") int restaurant_id, @JsonProperty("description") String description, @JsonProperty("list_of_dish") List<DishTo> list_of_dish) {
        this.restaurant_id = restaurant_id;
        this.description = description;
        this.list_of_dish = list_of_dish;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public String getDescription() {
        return description;
    }

    public List<DishTo> getList_of_dish() {
        return list_of_dish;
    }

    @Override
    public String toString() {
        return "RestaurantForVoteTo{" +
                "restaurant_id=" + restaurant_id +
                ", description='" + description + '\'' +
                ", list_of_dish=" + list_of_dish +
                '}';
    }
}
