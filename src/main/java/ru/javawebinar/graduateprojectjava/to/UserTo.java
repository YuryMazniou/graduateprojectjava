package ru.javawebinar.graduateprojectjava.to;

import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.User;

import java.util.Date;
import java.util.List;

public class UserTo {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Date registered;
    private List<Restaurant> restaurants;

    public UserTo(User user){
        this(user.getId(),user.getName(),user.getEmail(),user.getPassword(),user.getRegistered());
    }
    public UserTo(Integer id, String name, String email, String password,Date registered) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = registered;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
