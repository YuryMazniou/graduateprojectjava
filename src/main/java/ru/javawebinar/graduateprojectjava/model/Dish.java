package ru.javawebinar.graduateprojectjava.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dish extends AbstractBaseEntity {
    private Integer restaurant_id;
    private String description;
    private BigDecimal price;
    private LocalDateTime time_create_dish;

    public Dish() {
    }

    public Dish(Integer restaurant_id, String description, BigDecimal price, LocalDateTime time_create_dish) {
        this(null,restaurant_id,description,price,time_create_dish);
    }

    public Dish(Integer id,Integer restaurant_id, String description, BigDecimal price, LocalDateTime time_create_dish) {
        super(id);
        this.restaurant_id = restaurant_id;
        this.description = description;
        this.price = price;
        this.time_create_dish = time_create_dish;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTime_create_dish() {
        return time_create_dish;
    }

    public void setTime_create_dish(LocalDateTime time_create_dish) {
        this.time_create_dish = time_create_dish;
    }
}
