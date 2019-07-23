package ru.javawebinar.graduateprojectjava.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="history_restaurant_object")
public class HistoryRestaurantObject extends AbstractBaseEntity {
    private Integer counts;
    private String description;
    private LocalDate votes_date;

    public HistoryRestaurantObject(){

    }
    public HistoryRestaurantObject(Integer counts, String description) {
        this(null, counts,description, LocalDate.now());
    }
    public HistoryRestaurantObject(Integer id, Integer counts, String description, LocalDate votes_date) {
        super(id);
        this.counts = counts;
        this.description = description;
        this.votes_date = votes_date;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getVotes_date() {
        return votes_date;
    }

    public void setVotes_date(LocalDate localDate) {
        this.votes_date = localDate;
    }

    @Override
    public String toString() {
        return "HistoryRestaurantObject{" +
                "count=" + counts +
                ", description='" + description + '\'' +
                ", localDate=" + votes_date +
                ", id=" + id +
                '}';
    }
}
