package ru.javawebinar.graduateprojectjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity
@Table(name = "votes",uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id","time_create_vote"}, name = "vote_unique_idx")})
public class Vote extends AbstractBaseEntity {
    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "restaurant_id")
    @NotNull
    private Integer restaurant_id;

    @Column(name = "time_create_vote", nullable = false)
    @NotNull
    private LocalDateTime time_create_vote;

    public Vote() {
    }

    public Vote(Integer restaurant_id, LocalDateTime time_create_vote) {
        this(null,restaurant_id,time_create_vote);
    }

    public Vote(Integer id,Integer restaurant_id,LocalDateTime time_create_vote) {
        super(id);
        this.restaurant_id=restaurant_id;
        this.time_create_vote = time_create_vote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", restaurant_id=" + restaurant_id +
                ", time_create_vote=" + time_create_vote +
                ", id=" + id +
                '}';
    }
}
