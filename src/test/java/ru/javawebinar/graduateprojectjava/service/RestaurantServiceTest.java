package ru.javawebinar.graduateprojectjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import ru.javawebinar.graduateprojectjava.util.JpaUtil;

import java.util.List;

import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;

public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }
    @Test
    public void getRestaurantsWithDishForVote() {
        testOn();
        List<RestaurantTo>list=service.getRestaurantsWithDishForVote();
        assertMatchR(list,List.of(RESTAURANT1,RESTAURANT2));
        assertMatchD(list.get(0).getList_of_dish(),DISH_LIST1);
        assertMatchD(list.get(1).getList_of_dish(),DISH_LIST2);
    }

    @Test
    public void getTodayRestaurantsStatistic() {
        testOn();
        List<TodayTo>list=service.getTodayRestaurantsStatistic();

    }

    @Test
    public void getAllTimeRestaurantStatistic() {
    }

    @Test
    public void saveUserVote() {
    }

    @Test
    public void deleteVote() {
    }

    @Test
    public void getVoteToday() {
    }

    @Test
    public void createDishForVote() {
    }

    @Test
    public void updateDishForVote() {
    }

    @Test
    public void deleteDishForVote() {
    }

    @Test
    public void getDishes() {
    }

    @Test
    public void saveRestaurant() {
    }

    @Test
    public void updateRestaurant() {
    }

    @Test
    public void deleteRestaurant() {
    }

    @Test
    public void getRestaurantsForUser() {
    }
}