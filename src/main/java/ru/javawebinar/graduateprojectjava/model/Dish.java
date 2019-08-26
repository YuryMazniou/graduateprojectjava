package ru.javawebinar.graduateprojectjava.model;

import org.hibernate.validator.constraints.SafeHtml;
import ru.javawebinar.graduateprojectjava.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(groups = View.Persist.class)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(groups = View.Persist.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "description")
    @NotBlank
    @SafeHtml(groups = {View.Web.class})
    private String description;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "time_create_dish", nullable = false)
    private LocalDate time_create_dish;

    public Dish() {
    }

    public Dish( String description, BigDecimal price) {
        this(null,description,price,null);
    }

    public Dish(Integer id, String description, BigDecimal price, LocalDate time_create_dish) {
        super(id);
        this.description = description;
        this.price = price;
        this.time_create_dish = time_create_dish;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                " id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", time_create_dish=" + time_create_dish +
                '}';
    }
}
