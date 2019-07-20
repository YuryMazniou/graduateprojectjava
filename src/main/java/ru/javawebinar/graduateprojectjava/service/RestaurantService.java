package ru.javawebinar.graduateprojectjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.repository.DishRepository;
import ru.javawebinar.graduateprojectjava.repository.RestaurantRepository;
import ru.javawebinar.graduateprojectjava.repository.UserRepository;
import ru.javawebinar.graduateprojectjava.repository.VoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;
    private VoteRepository voteRepository;
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository
            , VoteRepository voteRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Cacheable("restaurantTo")
    public List<Dish> getRestaurantsWithDishForVote() {
        LocalDate today = today();
        checkTime(getTimeForUser());
        return dishRepository.getDishForVote(today);
    }

    @Transactional
    public Vote saveUserVote(int restaurant_id, int user_id) {
        LocalDate today = today();
        checkTime(getTimeForUser());
        Vote voteToday=voteRepository.getVoteToday(user_id,today);
        if(voteToday==null){
            Vote vote=new Vote(restaurant_id);
            vote.setUser(em.getReference(User.class,user_id));
            return voteRepository.save(vote);
        }
        else {
            voteToday.setRestaurant_id(restaurant_id);
            return voteRepository.save(voteToday);
        }
    }
///////////////////crud Dish////////////////
    @Transactional
    public Dish saveDishForVote(int user_id, Dish dish) {
        checkTime(getTimeForAdmin());
        if(!dish.isNew())checkNotFoundWithId(dishRepository.findById(dish.getId()).orElse(null),dish.getId());
        return dishRepository.save(dish);
    }

    public Dish getDish(int dish_id, int user_id) {

        return null;
    }

//////////////////crud Restaurant///////////////
    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant, int user_id) {
        Assert.notNull(restaurant,"restaurant must not be null");
        restaurant.setUser(em.getReference(User.class,user_id));
        return restaurantRepository.save(restaurant);
    }
    @Transactional
    public void updateRestaurant(Restaurant restaurant, int user_id) {
        Assert.notNull(restaurant,"restaurant must not be null");
        Restaurant r=checkNotFoundWithId(restaurantRepository.getRestaurant(restaurant.getId(),user_id),restaurant.getId());
        r.setDescription(restaurant.getDescription());
        restaurantRepository.save(r);
    }
    @Transactional
    public void deleteRestaurant(int restaurant_id, int user_id) {
        checkNotFoundWithId(restaurantRepository.delete(restaurant_id, user_id)!=0, restaurant_id);
    }
    public List<Restaurant> getRestaurantsForUser(int id){
        return restaurantRepository.getRestaurantsForUser(id);
    }
}
