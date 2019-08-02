package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;

import java.net.URI;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantCrudController.ADMIN_CRUD_REST, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantCrudController {
    public static final String ADMIN_CRUD_REST="/restaurants/admin/restaurant";

    private static final Logger log = getLogger(RestaurantCrudController.class);

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantCrudController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant){
        int user_id= SecurityUtil.authUserId();
        log.info("create restaurant  {}",user_id);
        checkNew(restaurant);
        Restaurant created=restaurantService.saveRestaurant(restaurant,user_id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_CRUD_REST + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("delete restaurant  {}",user_id);
        restaurantService.deleteRestaurant(restaurant_id,user_id);
    }

    @PutMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant restaurant,@PathVariable int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("update restaurant  {}",user_id);
        assureIdConsistent(restaurant,restaurant_id);
        restaurantService.saveRestaurant(restaurant,user_id);
    }

    @GetMapping
    public List<Restaurant> getRestaurantsForUser(){
        int user_id= SecurityUtil.authUserId();
        log.info("get user's restaurants  {}",user_id);
        return restaurantService.getRestaurantsForUser(user_id);
    }
}
