package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;

import java.math.BigDecimal;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.graduateprojectjava.util.RestaurantAndUsersUtil.*;
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

    public Vote saveUserVote(int restaurant_id){
        int idUser= SecurityUtil.authUserId();
        log.info("save user's vote {}",idUser);
        return restaurantService.saveUserVote(restaurant_id,idUser);
    }

    public Dish createDishForVote(Dish dish){
        int user_id= SecurityUtil.authUserId();
        checkNew(dish);
        return restaurantService.saveDishForVote(user_id,dish);
    }
    public void deleteDishForVote(){

    }

    public void updateDishForVote(Dish dish,int id){
        int user_id= SecurityUtil.authUserId();
        assureIdConsistent(dish,id);
        restaurantService.saveDishForVote(dish,user_id);
    }
    public Dish getDish(int dish_id){
        int user_id= SecurityUtil.authUserId();
        return restaurantService.getDish(dish_id,user_id);
    }

    public Restaurant createRestaurant(Restaurant restaurant){
        int user_id= SecurityUtil.authUserId();
        checkNew(restaurant);
        return restaurantService.saveRestaurant(restaurant,user_id);
    }
    public void deleteRestaurant(int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        restaurantService.deleteRestaurant(restaurant_id,user_id);
    }
    public void updateRestaurant(Restaurant restaurant, int id){
        int user_id= SecurityUtil.authUserId();
        assureIdConsistent(restaurant,id);
        restaurantService.updateRestaurant(restaurant,user_id);
    }

    public List<Restaurant> getRestaurantsForUser(){
        int user_id= SecurityUtil.authUserId();
        return restaurantService.getRestaurantsForUser(user_id);
    }
}
