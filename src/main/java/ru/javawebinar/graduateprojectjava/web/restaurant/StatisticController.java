package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.to.AllTimeTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantForVoteTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantStatisticTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = StatisticController.STATISTIC_REST, produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {
    private static final Logger log = getLogger(StatisticController.class);

    public static final String STATISTIC_REST="/restaurants/profile/statistic";

    private RestaurantService restaurantService;

    @Autowired
    public StatisticController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/listforvotes")
    public List<RestaurantForVoteTo> getRestaurantsWithDishForVote(){
        log.info("get list of restaurant with dish for votes");
        return restaurantService.getRestaurantsWithDishForVote();
    }

    @GetMapping("/resultofday")
    public List<TodayTo> getTodayRestaurantsStatistic(){
        log.info("get today restaurant statistic");
        return restaurantService.getTodayRestaurantsStatistic();
    }

    @GetMapping("/history")
    public List<AllTimeTo> getAllTimeRestaurantStatistic(){
        log.info("get restaurant statistic for all time");
        return restaurantService.getAllTimeRestaurantStatistic();
    }

    @GetMapping("/dish/{restaurant_id}")
    public List<RestaurantStatisticTo> getHistoryDishForRestaurant(@PathVariable int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("get dishes statistic {}",user_id);
        return restaurantService.getHistoryDishForAdmin(restaurant_id,user_id);
    }
}
