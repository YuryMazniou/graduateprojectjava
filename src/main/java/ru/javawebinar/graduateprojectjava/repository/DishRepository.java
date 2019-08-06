package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish,Integer> {
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.time_create_dish=:today")
    List<Dish> getDishForVote(@Param("today") LocalDate today);

    @Override
    @Transactional
    Dish save(Dish entity);

    @Query("SELECT d FROM Dish d WHERE d.id=:dish_id AND d.user.id=:user_id AND d.time_create_dish=:today AND d.restaurant.id=:restaurant_id")
    Dish getDish(@Param("dish_id")int dish_id,@Param("user_id")int user_id,@Param("today")LocalDate today,@Param("restaurant_id")int restaurant_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.user.id=:userId AND d.time_create_dish=:today")
    int delete(@Param("id") int id, @Param("userId") int userId,@Param("today")LocalDate today);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.user.id=:user_id AND d.restaurant.id=:restaurant_id AND d.time_create_dish=:today")
    List<Dish> getDishes(@Param("user_id")int user_id,@Param("today")LocalDate today,@Param("restaurant_id")int restaurant_id);
}
