package ru.javawebinar.graduateprojectjava.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dish {
    private int dish_id;
    private int restaurant_id;
    private String description;
    private BigDecimal price;
    private LocalDateTime time_create_dish;
}
