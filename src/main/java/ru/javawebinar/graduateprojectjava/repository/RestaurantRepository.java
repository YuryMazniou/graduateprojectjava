package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    @Query("SELECT r FROM Restaurant r WHERE r.user.id=:id")
    List<Restaurant> getRestaurantsForUser(@Param("id") int id);
}
