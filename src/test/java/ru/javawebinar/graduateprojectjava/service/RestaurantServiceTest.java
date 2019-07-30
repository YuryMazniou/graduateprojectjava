package ru.javawebinar.graduateprojectjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;

class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    private RestaurantService service;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }
    @Test
    void getRestaurantsWithDishForVote() {
        testOn();
        setLocalDate(LocalDate.of(2019,7,3));
        List<RestaurantTo>list=service.getRestaurantsWithDishForVote();
        assertMatchR(list,List.of(RESTAURANT_TO_1, RESTAURANT_TO_2));
        assertMatchD(list.get(0).getList_of_dish(),DISH_LIST1);
        assertMatchD(list.get(1).getList_of_dish(),DISH_LIST2);
    }

    @Test
    void getTodayRestaurantsStatistic() {
        testOn();
        setLocalTime(LocalTime.of(12,0));
        setLocalDate(LocalDate.of(2019,7,3));
        List<TodayTo>list=service.getTodayRestaurantsStatistic();
        assertMatchD(list,TODAY_TO_LIST);
    }

    @Test
    void getAllTimeRestaurantStatistic() {

    }

    @Test
    void saveUserVote() {
    }

    @Test
    void deleteVote() {
    }

    @Test
    void getVoteToday() {
    }

    @Test
    void createDishForVote() {
    }

    @Test
    void updateDishForVote() {
    }

    @Test
    void deleteDishForVote() {
    }

    @Test
    void getDishes() {
    }

    @Test
    void saveRestaurant() {
    }

    @Test
    void updateRestaurant() {
    }

    @Test
    void deleteRestaurant() {
    }

    @Test
    void getRestaurantsForUser() {
    }
}