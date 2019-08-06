package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Restaurant;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    @Query("SELECT r FROM Restaurant r WHERE r.user.id=:restaurant_id")
    List<Restaurant> getRestaurantsForUser(@Param("restaurant_id") int restaurant_id);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.user WHERE r.id=:restaurant_id AND r.user.id=:user_id")
    Restaurant getRestaurant(@Param("restaurant_id")int restaurant_id,@Param("user_id")int user_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:restaurant_id AND r.user.id=:userId")
    int delete(@Param("restaurant_id") int restaurant_id, @Param("userId") int userId);

    @Query("SELECT r FROM Restaurant  r JOIN FETCH r.dishes WHERE r.id=:restaurant_id AND r.user.id=:user_id")
    Restaurant getRestaurantWithDish(@Param("restaurant_id")int restaurant_id,@Param("user_id")int user_id);
}
