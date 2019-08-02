package ru.javawebinar.graduateprojectjava.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.to.AllTimeTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantForVoteTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import ru.javawebinar.graduateprojectjava.util.exception.NotFoundException;
import ru.javawebinar.graduateprojectjava.util.exception.WrongTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
        testOn();
        setLocalDate(LocalDate.of(2019,7,3));
    }
    @AfterEach
    void offTest(){
        testOff();
    }
    @Test
    void testGetRestaurantsWithDishForVote() {
        setLocalTime(LocalTime.of(10,0));
        List<RestaurantForVoteTo>list=service.getRestaurantsWithDishForVote();
        assertMatchR(list,List.of(RESTAURANT_TO_1, RESTAURANT_TO_2));
        assertMatchD(list.get(0).getList_of_dish(),DISH_LIST1);
        assertMatchD(list.get(1).getList_of_dish(),DISH_LIST2);
    }

    @Test
    void testWrongTimeGetRestaurantsWithDishForVote(){
        setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.getRestaurantsWithDishForVote());
    }

    @Test
    void testGetTodayRestaurantsStatistic() {
        setLocalTime(LocalTime.of(12,0));
        List<TodayTo>list=service.getTodayRestaurantsStatistic();
        assertMatchList(list,TODAY_TO_LIST);
    }

    @Test
    void testWrongTimeGetTodayRestaurantsStatistic(){
        setLocalTime(LocalTime.of(10,0));
        assertThrows(WrongTimeException.class,()->service.getTodayRestaurantsStatistic());
    }

    @Test
    void testGetAllTimeRestaurantStatistic() {
        setLocalDate(LocalDate.of(2019,7,2));
        setLocalTime(LocalTime.of(12,0));
        List<AllTimeTo>allTimeTos=service.getAllTimeRestaurantStatistic();
        assertMatchList(allTimeTos,ALL_TIME_TO_LIST);
    }

    @Test
    void testSaveUserVote() {
        setLocalTime(LocalTime.of(10,0));
        Vote vote=service.saveUserVote(100005,100003);
        assertMatch(vote,VOTE_ADMIN2);
    }
    @Test
    void testUpdateUserVote() {
        setLocalTime(LocalTime.of(10,0));
        Vote vote=service.saveUserVote(100004,100001);
        assertMatch(vote,VOTE_UPDATE);
    }
    @Test
    void testWrongTimeSaveVote() {
        setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.saveUserVote(100004,100001));
    }

    @Test
    void testDeleteVote() {
        setLocalTime(LocalTime.of(10,0));
        service.deleteVote(100014,100001);
    }

    @Test
    void testWrongTimeDeleteVote() {
        setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.deleteVote(100014,100001));
    }

    @Test
    void testNotFoundDeleteVote() {
        setLocalTime(LocalTime.of(10,0));
        assertThrows(NotFoundException.class,()->service.deleteVote(100013,100001));
    }

    @Test
    void testGetVoteToday() {
        setLocalTime(LocalTime.of(10,0));
        Vote vote=service.getVoteToday(100001);
        assertMatch(vote,VOTE_GET);
    }

    @Test
    void testNotFoundGetVoteToday() {
        setLocalTime(LocalTime.of(10,0));
        assertThrows(NotFoundException.class,()->service.getVoteToday(1));
    }

    @Test
    void testCreateDishForVote() {
        setLocalTime(LocalTime.of(8,0));
        Dish newDish=new Dish(DISH_CREATE.getDescription(),DISH_CREATE.getPrice());
        Dish created=service.saveDishForVote(newDish,100004,100002);
        newDish.setId(created.getId());
        assertMatchD(service.getDishes(100004,100002),List.of(newDish,DISH1,DISH2,DISH3));
    }

    @Test
    void testUpdateDishForVote() {
        setLocalTime(LocalTime.of(8,0));
        Dish update=DISH_UPDATE;
        service.saveDishForVote(update,100004,100002);
        assertMatchD(service.getDishes(100004,100002),List.of(update,DISH2,DISH3));
    }

    @Test
    void testNotFoundSaveDishForVote(){
        setLocalTime(LocalTime.of(8,0));
        assertThrows(NotFoundException.class,()->service.saveDishForVote(DISH1,100004,100003));
    }

    @Test
    void testWrongTimeSaveDishForVote(){
        setLocalTime(LocalTime.of(10,0));
        Dish update=DISH_UPDATE;
        assertThrows(WrongTimeException.class,()->service.saveDishForVote(update,100004,100002));
    }

    @Test
    void testDeleteDishForVote() {
        setLocalTime(LocalTime.of(8,0));
        service.deleteDishForVote(100006,100002);
    }

    @Test
    void testWrongTimeDeleteDishForVote() {
        setLocalTime(LocalTime.of(10,0));
        assertThrows(WrongTimeException.class,()->service.deleteDishForVote(100006,100002));
    }

    @Test
    void testNotFoundDeleteDishForVote() {
        setLocalTime(LocalTime.of(8,0));
        assertThrows(NotFoundException.class,()->service.deleteDishForVote(1,100002));
    }

    @Test
    void testGetDishes() {
        List<Dish>dishes=service.getDishes(100004,100002);
        assertMatchD(dishes,DISH_LIST1);
    }

    @Test
    void testSaveRestaurant() {
        setLocalTime(LocalTime.of(8,0));
        Restaurant newRest=new Restaurant(RESTAURANT_CREATE.getDescription());
        Restaurant create=service.saveRestaurant(newRest,100002);
        newRest.setId(create.getId());
        assertMatch(service.getRestaurantsForUser(100002),List.of(newRest,RESTAURANT_USER100002));
    }

    @Test
    void testUpdateRestaurant() {
        setLocalTime(LocalTime.of(8,0));
        Restaurant update=RESTAURANT_UPDATE;
        update.setDescription("update name");
        service.saveRestaurant(update,100002);
        assertMatchRestList(service.getRestaurantsForUser(100002),List.of(update));
    }

    @Test
    void testWrongTimeSaveRestaurant(){
        setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.saveRestaurant(new Restaurant(RESTAURANT_CREATE.getDescription()),100002));
    }

    @Test
    void testNotFoundSaveRestaurant(){
        setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.saveRestaurant(RESTAURANT_UPDATE,1));
    }

    @Test
    void testDeleteRestaurant() {
        setLocalTime(LocalTime.of(8,0));
        service.deleteRestaurant(100004,100002);
    }

    @Test
    void testWrongTimeDeleteRestaurant() {
        setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.deleteRestaurant(100004,100002));
    }

    @Test
    void testNotFoundDeleteRestaurant() {
        setLocalTime(LocalTime.of(8,0));
        assertThrows(NotFoundException.class,()->service.deleteRestaurant(100004,1));
    }

    @Test
    void testGetRestaurantsForUser() {
        setLocalTime(LocalTime.of(8,0));
        assertMatchRestList(service.getRestaurantsForUser(100002),List.of(RESTAURANT_USER100002));
    }
}