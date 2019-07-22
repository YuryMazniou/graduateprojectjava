package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;

import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.graduateprojectjava.util.RestaurantUtil.*;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.*;

@Controller
public class RestaurantController {
    private static final Logger log = getLogger(RestaurantController.class);

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public List<RestaurantTo> getRestaurantsWithDishForVote(){
        log.info("get list of restaurant with dish for votes");
        List<Dish>dishes=restaurantService.getRestaurantsWithDishForVote();
        return transformToRestaurantTo(dishes);
    }

    public List<TodayTo> getTodayRestaurantStatistic(){
        log.info("get today restaurant statistic");
        return restaurantService.getTodayRestaurantStatistic();
    }

    public Vote saveUserVote(int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("save user's vote {}",user_id);
        return restaurantService.saveUserVote(restaurant_id,user_id);
    }
    ///////////////////crud Dish////////////////
    public Dish createDishForVote(Dish dish){
        int user_id= SecurityUtil.authUserId();
        checkNew(dish);
        log.info("save dish {}",user_id);
        return restaurantService.createDishForVote(user_id,dish);
    }
    public void deleteDishForVote(int dish_id){
        int user_id= SecurityUtil.authUserId();
        log.info("delete dish  {}",user_id);
        restaurantService.deleteDishForVote(dish_id,user_id);
    }
    public void updateDishForVote(Dish dish,int id){
        int user_id= SecurityUtil.authUserId();
        assureIdConsistent(dish,id);
        log.info("update dish  {}",user_id);
        restaurantService.updateDishForVote(dish,user_id);
    }
    public List<Dish> getDishes(int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("get dishes  {}",user_id);
        return restaurantService.getDishes(restaurant_id,user_id);
    }
    //////////////////crud Restaurant///////////////
    public Restaurant createRestaurant(Restaurant restaurant){
        int user_id= SecurityUtil.authUserId();
        checkNew(restaurant);
        log.info("create restaurant  {}",user_id);
        return restaurantService.saveRestaurant(restaurant,user_id);
    }
    public void deleteRestaurant(int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("delete restaurant  {}",user_id);
        restaurantService.deleteRestaurant(restaurant_id,user_id);
    }
    public void updateRestaurant(Restaurant restaurant, int id){
        int user_id= SecurityUtil.authUserId();
        assureIdConsistent(restaurant,id);
        log.info("update restaurant  {}",user_id);
        restaurantService.updateRestaurant(restaurant,user_id);
    }

    public List<Restaurant> getRestaurantsForUser(){
        int user_id= SecurityUtil.authUserId();
        log.info("get user's restaurants  {}",user_id);
        return restaurantService.getRestaurantsForUser(user_id);
    }
}
