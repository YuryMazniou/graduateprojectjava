package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;

import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishCrudController.ADMIN_CRUD_DISH, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishCrudController {
    public static final String ADMIN_CRUD_DISH="/restaurants/admin/dish";

    private static final Logger log = getLogger(DishCrudController.class);

    private RestaurantService restaurantService;

    @Autowired
    public DishCrudController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping(value = "/{restaurant_id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishForVote(@RequestBody Dish dish, @PathVariable int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        checkNew(dish);
        log.info("save dish {}",user_id);
        Dish created = restaurantService.saveDishForVote(dish,restaurant_id,user_id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_CRUD_DISH + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{dish_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishForVote(@PathVariable int dish_id){
        int user_id= SecurityUtil.authUserId();
        log.info("delete dish  {}",user_id);
        restaurantService.deleteDishForVote(dish_id,user_id);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDishForVote(@RequestBody Dish dish,@RequestParam int restaurant_id,@RequestParam int dish_id){
        int user_id= SecurityUtil.authUserId();
        log.info("update dish  {}",user_id);
        assureIdConsistent(dish,dish_id);
        restaurantService.saveDishForVote(dish,restaurant_id,user_id);
    }

    @GetMapping("/{restaurant_id}")
    public List<Dish> getDishes(@PathVariable int restaurant_id){
        int user_id= SecurityUtil.authUserId();
        log.info("get dishes  {}",user_id);
        return restaurantService.getDishes(restaurant_id,user_id);
    }
}


