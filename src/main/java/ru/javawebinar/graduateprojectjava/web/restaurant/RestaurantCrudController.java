package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduateprojectjava.AuthorizedUser;
import ru.javawebinar.graduateprojectjava.View;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
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
    public ResponseEntity<Restaurant> createRestaurant(@Validated(View.Web.class) @RequestBody Restaurant restaurant,@AuthenticationPrincipal AuthorizedUser authUser){
        log.info("create restaurant  {}",authUser.getId());
        checkNew(restaurant);
        Restaurant created=restaurantService.saveRestaurant(restaurant,authUser.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_CRUD_REST + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int restaurant_id,@AuthenticationPrincipal AuthorizedUser authUser){
        log.info("delete restaurant  {}",authUser.getId());
        restaurantService.deleteRestaurant(restaurant_id,authUser.getId());
    }

    @PutMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@Validated(View.Web.class) @RequestBody Restaurant restaurant,@PathVariable int restaurant_id,@AuthenticationPrincipal AuthorizedUser authUser){
        log.info("update restaurant  {}",authUser.getId());
        assureIdConsistent(restaurant,restaurant_id);
        restaurantService.saveRestaurant(restaurant,authUser.getId());
    }

    @GetMapping
    public List<Restaurant> getRestaurantsForUser(@AuthenticationPrincipal AuthorizedUser authUser){
        log.info("get user's restaurants  {}",authUser.getId());
        return restaurantService.getRestaurantsForUser(authUser.getId());
    }
}
