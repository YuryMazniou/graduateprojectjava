package ru.javawebinar.graduateprojectjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dishes",uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id","time_create_dish"}, name = "dishes_idx")})
public class Dish extends AbstractBaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "price")
    @NotEmpty
    private BigDecimal price;

    @Column(name = "time_create_dish", nullable = false)
    @NotNull
    private LocalDate time_create_dish;

    public Dish() {
    }

    public Dish( String description, BigDecimal price, LocalDate time_create_dish) {
        this(null,description,price,time_create_dish);
    }

    public Dish(Integer id, String description, BigDecimal price, LocalDate time_create_dish) {
        super(id);
        this.description = description;
        this.price = price;
        this.time_create_dish = time_create_dish;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public LocalDate getTime_create_dish() {
        return time_create_dish;
    }

    public void setTime_create_dish(LocalDate time_create_dish) {
        this.time_create_dish = time_create_dish;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "restaurant=" + restaurant +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", time_create_dish=" + time_create_dish +
                ", id=" + id +
                '}';
    }
}
