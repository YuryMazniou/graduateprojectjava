package ru.javawebinar.graduateprojectjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduateprojectjava.model.*;
import ru.javawebinar.graduateprojectjava.repository.DishRepository;
import ru.javawebinar.graduateprojectjava.repository.HistoryRestaurantRepository;
import ru.javawebinar.graduateprojectjava.repository.RestaurantRepository;
import ru.javawebinar.graduateprojectjava.repository.VoteRepository;
import ru.javawebinar.graduateprojectjava.to.AllTimeTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantStatisticTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantForVoteTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;
import ru.javawebinar.graduateprojectjava.util.DateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import static ru.javawebinar.graduateprojectjava.util.RestaurantUtil.*;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;
    private VoteRepository voteRepository;
    private HistoryRestaurantRepository historyRestaurantRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DateTime dateTime;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository
            , VoteRepository voteRepository,HistoryRestaurantRepository historyRestaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
        this.historyRestaurantRepository=historyRestaurantRepository;
    }

///////////////Statistic///////////

    @Cacheable("restaurantTo")
    public List<RestaurantForVoteTo> getRestaurantsWithDishForVote() {
        LocalDate today = dateTime.today();
        checkTime(dateTime.getTimeForUser());
        List<Dish>dishes=dishRepository.getDishForVote(today);
        return transformToRestaurantTo(dishes);
    }

    @Cacheable("todayTo")
    @Transactional
    public List<TodayTo> getTodayRestaurantsStatistic() {
        checkTime(dateTime.getTimeForStatistic());
        LocalDate today = dateTime.today();
        List<Vote>votes=voteRepository.getVotesToday(today);
        List<Restaurant>restaurants=restaurantRepository.findAll();
        List<TodayTo>statistic=transformToTodayTo(votes,restaurants);
        if(dateTime.checkTimeForSaveStatistic())saveStatistic(statistic);
        return statistic;
    }

    private void saveStatistic(List<TodayTo>statistic){
        for (TodayTo t:statistic) {
            HistoryRestaurantObject obj=new HistoryRestaurantObject(t.getCount()==null?0:t.getCount(),t.getDescription());
            historyRestaurantRepository.save(obj);
        }
    }

    @Transactional
    public List<AllTimeTo> getAllTimeRestaurantStatistic() {
        boolean flag=dateTime.getTimeForStatistic();
        if(flag&&dateTime.checkTimeForSaveStatistic()){
            LocalDate today = dateTime.today();
            List<Vote>votes=voteRepository.getVotesToday(today);
            List<Restaurant>restaurants=restaurantRepository.findAll();
            List<TodayTo>statistic=transformToTodayTo(votes,restaurants);
            saveStatistic(statistic);
            return transformToAllTimeTo(historyRestaurantRepository.findAll());
        }
        else {
            return transformToAllTimeTo(historyRestaurantRepository.findAll());
        }
    }

    public List<RestaurantStatisticTo> getHistoryDishForAdmin(int restaurant_id, int user_id) {
        Restaurant restaurant=checkNotFoundWithId(restaurantRepository.getRestaurantWithDish(restaurant_id,user_id),restaurant_id);
        List<Dish>dishes=restaurant.getDishes();
        List<HistoryRestaurantObject> objects=historyRestaurantRepository.getRestaurantHistoryVotes(restaurant.getDescription());
        return transformToRestaurantStatisticTo(dishes,objects);
    }
    ///////////////////crud Vote////////////////

    @Transactional
    @CacheEvict(value = "todayTo", allEntries = true)
    public Vote saveUserVote(int restaurant_id, int user_id) {
        LocalDate today = dateTime.today();
        checkTime(dateTime.getTimeForUser());
        Vote voteToday=voteRepository.getVoteToday(user_id,today);
        if(voteToday==null){
            Vote vote=new Vote(restaurant_id,today);
            vote.setUser(em.getReference(User.class,user_id));
            return voteRepository.save(vote);
        }
        else {
            voteToday.setRestaurant_id(restaurant_id);
            return voteRepository.save(voteToday);
        }
    }

    @Transactional
    @CacheEvict(value = "todayTo", allEntries = true)
    public void deleteVote(int vote_id, int user_id) {
        LocalDate today = dateTime.today();
        checkTime(dateTime.getTimeForUser());
        checkNotFoundWithId(voteRepository.delete(vote_id,user_id,today)!=0,vote_id);
    }

    public Vote getVoteToday(int user_id) {
        LocalDate today = dateTime.today();
        return checkNotFoundWithId(voteRepository.getVoteToday(user_id,today),user_id);
    }
///////////////////crud Dish////////////////

    @Transactional
    @CacheEvict(value = "restaurantTo", allEntries = true)
    public Dish saveDishForVote(Dish dish,int restaurant_id,int user_id) {
        checkTime(dateTime.getTimeForAdmin());
        Assert.notNull(dish,"restaurant must not be null");
        LocalDate today = dateTime.today();
        dish.setTime_create_dish(today);
        dish.setUser(em.getReference(User.class,user_id));
        dish.setRestaurant(em.getReference(Restaurant.class,restaurant_id));
        if(dish.isNew()) {
            return dishRepository.save(dish);
        }
        else{
            checkNotFoundWithId(dishRepository.getDish(dish.getId(),user_id,today,restaurant_id),dish.getId());
            return dishRepository.save(dish);
        }
    }

    @Transactional
    @CacheEvict(value = "restaurantTo", allEntries = true)
    public void deleteDishForVote(int dish_id,int userId) {
        checkTime(dateTime.getTimeForAdmin());
        LocalDate today = dateTime.today();
        checkNotFoundWithId(dishRepository.delete(dish_id, userId,today)!=0, dish_id);
    }

    public List<Dish> getDishes(int restaurant_id, int user_id) {
        LocalDate today = dateTime.today();
        return dishRepository.getDishes( user_id,today,restaurant_id)
                .stream()
                .sorted((d1,d2)->d1.getDescription().compareTo(d2.getDescription()))
                .collect(Collectors.toList());
    }
//////////////////crud Restaurant///////////////

    @Transactional
    @CacheEvict(value = "todayTo", allEntries = true)
    public Restaurant saveRestaurant(Restaurant restaurant, int user_id) {
        checkTime(dateTime.getTimeForAdmin());
        Assert.notNull(restaurant,"restaurant must not be null");
        restaurant.setUser(em.getReference(User.class, user_id));
        if(restaurant.isNew()) {
            return restaurantRepository.save(restaurant);
        }
        else{
            checkNotFoundWithId(restaurantRepository.getRestaurant(restaurant.getId(),user_id),restaurant.getId());
            return restaurantRepository.save(restaurant);
        }
    }

    @Transactional
    @CacheEvict(value = "todayTo", allEntries = true)
    public void deleteRestaurant(int restaurant_id, int user_id) {
        checkTime(dateTime.getTimeForAdmin());
        checkNotFoundWithId(restaurantRepository.delete(restaurant_id, user_id)!=0, restaurant_id);
    }

    public List<Restaurant> getRestaurantsForUser(int user_id){
        return restaurantRepository.getRestaurantsForUser(user_id)
                .stream()
                .sorted((r1,r2)->r1.getDescription().compareTo(r2.getDescription()))
                .collect(Collectors.toList());
    }
}
