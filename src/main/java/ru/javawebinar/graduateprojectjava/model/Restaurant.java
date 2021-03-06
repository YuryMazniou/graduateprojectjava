package ru.javawebinar.graduateprojectjava.model;

import org.hibernate.validator.constraints.SafeHtml;
import ru.javawebinar.graduateprojectjava.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "restaurants",uniqueConstraints = {@UniqueConstraint(columnNames = {"description"}, name = "restaurants_unique_name")})
public class Restaurant extends AbstractBaseEntity {
    @Column(name = "description")
    @NotBlank
    @SafeHtml(groups = {View.Web.class})
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(groups = View.Persist.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("time_create_dish desc")
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(String description) {
        this(null,description);
    }

    public Restaurant(Integer id,String description) {
        super(id);
        this.description = description;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                " id=" + id +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
