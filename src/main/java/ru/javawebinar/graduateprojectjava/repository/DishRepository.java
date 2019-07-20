package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
    Dish saveDish(@Param(""))
}
