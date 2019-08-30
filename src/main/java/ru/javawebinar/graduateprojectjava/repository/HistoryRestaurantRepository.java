package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.HistoryRestaurantObject;

import java.util.List;

@Transactional(readOnly = true)
public interface HistoryRestaurantRepository extends JpaRepository<HistoryRestaurantObject,Integer> {

    @Query("SELECT o FROM HistoryRestaurantObject o WHERE o.description=:description")
    List<HistoryRestaurantObject> getRestaurantHistoryVotes(@Param("description")String description);
}
