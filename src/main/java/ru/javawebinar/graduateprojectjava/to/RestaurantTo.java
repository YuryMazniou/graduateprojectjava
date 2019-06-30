package ru.javawebinar.graduateprojectjava.to;

import ru.javawebinar.graduateprojectjava.model.Dish;

import java.util.List;

public class RestaurantTo {
    private final int restaurant_id;
    private final String description;
    private final List<Dish> list_of_dish;

    public RestaurantTo(int restaurant_id, String description, List<Dish> list_of_dish) {
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

    public List<Dish> getList_of_dish() {
        return list_of_dish;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "restaurant_id=" + restaurant_id +
                ", description='" + description + '\'' +
                ", list_of_dish=" + list_of_dish +
                '}';
    }
}
