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
import ru.javawebinar.graduateprojectjava.to.RestaurantStatisticTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import ru.javawebinar.graduateprojectjava.util.exception.NotFoundException;
import ru.javawebinar.graduateprojectjava.util.exception.WrongTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
        dateTime.setLocalDate(LocalDate.of(2019,7,3));
    }
    @AfterEach
    void offTest(){
        dateTime.setLocalDate(LocalDate.of(1,1,1));
        dateTime.setLocalTime(LocalTime.of(0,0,1));
    }

    @Test
    void getHistoryDishForAdmin(){
        List<RestaurantStatisticTo> realLiest=service.getHistoryDishForAdmin(100004,100002);
        assertMatchList(realLiest,List.of(STATISTIC_TO1,STATISTIC_TO2));
    }

    @Test
    void getNotFoundHistoryDishForAdmin(){
        assertThrows(NotFoundException.class,()->service.getHistoryDishForAdmin(1,100002));
        assertThrows(NotFoundException.class,()->service.getHistoryDishForAdmin(100004,1));
    }

    @Test
    void testGetRestaurantsWithDishForVote() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        List<RestaurantForVoteTo>list=service.getRestaurantsWithDishForVote();
        assertMatchList(list,List.of(RESTAURANT_TO_1, RESTAURANT_TO_2));
    }

    @Test
    void testWrongTimeGetRestaurantsWithDishForVote(){
        dateTime.setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.getRestaurantsWithDishForVote());
    }

    @Test
    void testGetTodayRestaurantsStatistic() {
        dateTime.setLocalTime(LocalTime.of(12,0));
        List<TodayTo>list=service.getTodayRestaurantsStatistic();
        assertMatchList(list,TODAY_TO_LIST);
    }

    @Test
    void testWrongTimeGetTodayRestaurantsStatistic(){
        dateTime.setLocalTime(LocalTime.of(10,0));
        assertThrows(WrongTimeException.class,()->service.getTodayRestaurantsStatistic());
    }

    @Test
    void testGetAllTimeRestaurantStatistic() {
        dateTime.setLocalDate(LocalDate.of(2019,7,2));
        dateTime.setLocalTime(LocalTime.of(12,0));
        List<AllTimeTo>allTimeTos=service.getAllTimeRestaurantStatistic();
        assertMatchList(allTimeTos,ALL_TIME_TO_LIST);
    }

    @Test
    void testSaveUserVote() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        Vote vote=service.saveUserVote(100005,100003);
        assertMatch(vote,VOTE_ADMIN2);
    }
    @Test
    void testUpdateUserVote() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        Vote vote=service.saveUserVote(100004,100001);
        assertMatch(vote,VOTE_UPDATE);
    }
    @Test
    void testWrongTimeSaveVote() {
        dateTime.setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.saveUserVote(100004,100001));
    }

    @Test
    void testDeleteVote() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        service.deleteVote(100016,100001);
    }

    @Test
    void testWrongTimeDeleteVote() {
        dateTime.setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.deleteVote(100014,100001));
    }

    @Test
    void testNotFoundDeleteVote() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        assertThrows(NotFoundException.class,()->service.deleteVote(100013,100001));
    }

    @Test
    void testGetVoteToday() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        Vote vote=service.getVoteToday(100001);
        assertMatch(vote,VOTE_GET);
    }

    @Test
    void testNotFoundGetVoteToday() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        assertThrows(NotFoundException.class,()->service.getVoteToday(1));
    }

    @Test
    void testCreateDishForVote() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        Dish newDish=new Dish(DISH_CREATE.getDescription(),DISH_CREATE.getPrice());
        Dish created=service.saveDishForVote(newDish,100004,100002);
        newDish.setId(created.getId());
        assertMatchD(service.getDishes(100004,100002),List.of(newDish,DISH1,DISH2,DISH3));
    }

    @Test
    void testUpdateDishForVote() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        Dish update=DISH_UPDATE;
        service.saveDishForVote(update,100004,100002);
        assertMatchD(service.getDishes(100004,100002),List.of(update,DISH2,DISH3));
    }

    @Test
    void testNotFoundSaveDishForVote(){
        dateTime.setLocalTime(LocalTime.of(8,0));
        assertThrows(NotFoundException.class,()->service.saveDishForVote(DISH1,100004,100003));
    }

    @Test
    void testWrongTimeSaveDishForVote(){
        dateTime.setLocalTime(LocalTime.of(10,0));
        Dish update=new Dish(DISH_UPDATE.getDescription(),DISH_UPDATE.getPrice());
        assertThrows(WrongTimeException.class,()->service.saveDishForVote(update,100004,100002));
    }

    @Test
    void testDeleteDishForVote() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        service.deleteDishForVote(100008,100002);
    }

    @Test
    void testWrongTimeDeleteDishForVote() {
        dateTime.setLocalTime(LocalTime.of(10,0));
        assertThrows(WrongTimeException.class,()->service.deleteDishForVote(100006,100002));
    }

    @Test
    void testNotFoundDeleteDishForVote() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        assertThrows(NotFoundException.class,()->service.deleteDishForVote(1,100002));
    }

    @Test
    void testGetDishes() {
        List<Dish>dishes=service.getDishes(100004,100002);
        assertMatchD(dishes,DISH_LIST1);
    }

    @Test
    void testSaveRestaurant() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        Restaurant newRest=new Restaurant(RESTAURANT_CREATE.getDescription());
        Restaurant create=service.saveRestaurant(newRest,100002);
        newRest.setId(create.getId());
        assertMatch(service.getRestaurantsForUser(100002),List.of(newRest,RESTAURANT_USER100002));
    }

    @Test
    void testUpdateRestaurant() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        Restaurant update=RESTAURANT_UPDATE;
        update.setDescription("update name");
        service.saveRestaurant(update,100002);
        assertMatchRestList(service.getRestaurantsForUser(100002),List.of(update));
    }

    @Test
    void testWrongTimeSaveRestaurant(){
        dateTime.setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.saveRestaurant(new Restaurant(RESTAURANT_CREATE.getDescription()),100002));
    }

    @Test
    void testNotFoundSaveRestaurant(){
        dateTime.setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.saveRestaurant(RESTAURANT_UPDATE,1));
    }

    @Test
    void testDeleteRestaurant() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        service.deleteRestaurant(100004,100002);
    }

    @Test
    void testWrongTimeDeleteRestaurant() {
        dateTime.setLocalTime(LocalTime.of(12,0));
        assertThrows(WrongTimeException.class,()->service.deleteRestaurant(100004,100002));
    }

    @Test
    void testNotFoundDeleteRestaurant() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        assertThrows(NotFoundException.class,()->service.deleteRestaurant(100004,1));
    }

    @Test
    void testGetRestaurantsForUser() {
        dateTime.setLocalTime(LocalTime.of(8,0));
        assertMatchRestList(service.getRestaurantsForUser(100002),List.of(RESTAURANT_USER100002));
    }
}