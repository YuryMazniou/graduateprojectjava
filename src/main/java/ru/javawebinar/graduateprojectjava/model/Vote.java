package ru.javawebinar.graduateprojectjava.model;

import java.time.LocalDateTime;

public class Vote extends AbstractBaseEntity {
    private Integer user_id;
    private Integer restaurant_id;
    private LocalDateTime time_create_vote;

    public Vote() {
    }

    public Vote(Integer user_id, Integer restaurant_id, LocalDateTime time_create_vote) {
        this(null,user_id,restaurant_id,time_create_vote);
    }

    public Vote(Integer id,Integer user_id, Integer restaurant_id, LocalDateTime time_create_vote) {
        super(id);
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.time_create_vote = time_create_vote;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public LocalDateTime getTime_create_vote() {
        return time_create_vote;
    }

    public void setTime_create_vote(LocalDateTime time_create_vote) {
        this.time_create_vote = time_create_vote;
    }
}
