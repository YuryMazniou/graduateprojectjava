package ru.javawebinar.graduateprojectjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.repository.DishRepository;
import ru.javawebinar.graduateprojectjava.repository.RestaurantRepository;
import ru.javawebinar.graduateprojectjava.repository.UserRepository;
import ru.javawebinar.graduateprojectjava.repository.VoteRepository;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;
import static ru.javawebinar.graduateprojectjava.util.RestaurantAndUsersUtil.*;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;
    private VoteRepository voteRepository;
    private UserRepository userRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository
            , VoteRepository voteRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }



    @Cacheable("restaurantTo")
    public List<RestaurantTo> getRestaurantsWithDishForVote() {
        LocalDate today = today();
        checkTimeForVotes(getTimeForUser());
        List<Dish> dishes = dishRepository.getDishForVote(today);
        return transformToRestaurantTo(dishes);
    }

    @Transactional
    public Vote saveUserVote(Vote vote, int user_id) {
        LocalDate today = today();
        checkTimeForVotes(getTimeForUser());
        Vote voteToday=voteRepository.getVoteToday(user_id,today);
        if(voteToday==null){
            vote.setUser(userRepository.getOne(user_id));
            return voteRepository.save(vote);
        }
        else {
            voteToday.setRestaurant_id(vote.getRestaurant_id());
            return voteRepository.save(voteToday);
        }
    }
}
