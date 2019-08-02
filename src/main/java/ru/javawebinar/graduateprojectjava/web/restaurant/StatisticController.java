package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.to.AllTimeTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantForVoteTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class StatisticController {
    private static final Logger log = getLogger(RestaurantCrudController.class);

    private RestaurantService restaurantService;

    @Autowired
    public StatisticController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    public List<RestaurantForVoteTo> getRestaurantsWithDishForVote(){
        log.info("get list of restaurant with dish for votes");
        return restaurantService.getRestaurantsWithDishForVote();
    }

    public List<TodayTo> getTodayRestaurantsStatistic(){
        log.info("get today restaurant statistic");
        return restaurantService.getTodayRestaurantsStatistic();
    }
    public List<AllTimeTo> getAllTimeRestaurantStatistic(){
        return restaurantService.getAllTimeRestaurantStatistic();
    }
}
