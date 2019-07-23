package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.graduateprojectjava.model.HistoryRestaurantObject;

public interface HistoryRestaurantRepository extends JpaRepository<HistoryRestaurantObject,Integer> {
}
