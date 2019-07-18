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
import ru.javawebinar.graduateprojectjava.util.DateTimeUtil;
import ru.javawebinar.graduateprojectjava.util.RestaurantUtil;
import ru.javawebinar.graduateprojectjava.util.exception.WrongTimeException;

import java.time.LocalDate;
import java.util.List;

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
        LocalDate today = DateTimeUtil.today();
        if (DateTimeUtil.getTimeForUser()) {
            List<Dish> dishes = dishRepository.getDishForVote(today);
            return RestaurantUtil.transformToRestaurantTo(dishes);
        } else
            throw new WrongTimeException("Wrong time to show Dish for Vote");
    }

    @Transactional
    public Vote saveUserVote(Vote vote, int user_id) {
        LocalDate today = DateTimeUtil.today();
        if (DateTimeUtil.getTimeForUser()) {
            Vote voteToday=voteRepository.getVoteToday(today,user_id);
            if(voteToday==null){
                vote.setUser(userRepository.getOne(user_id));
                return voteRepository.save(vote);
            }
            else {
                voteToday.setRestaurant_id(vote.getRestaurant_id());
                return voteRepository.save(voteToday);
            }
        } else
            throw new WrongTimeException("Wrong time for Vote");
    }
}
