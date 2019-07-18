package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class RestaurantController {
    private static final Logger log = getLogger(RestaurantController.class);

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public List<RestaurantTo> getRestaurantsWithDishForVote(){
        log.info("get list of restaurant with dish for votes");
        return restaurantService.getRestaurantsWithDishForVote();
    }

    public Vote saveUserVote(){
        int idUser= SecurityUtil.authUserId();
        log.info("save user's vote {}",idUser);
        return restaurantService.saveUserVote(new Vote(),idUser);
    }
}
