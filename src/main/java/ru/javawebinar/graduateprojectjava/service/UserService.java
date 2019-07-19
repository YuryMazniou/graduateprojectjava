package ru.javawebinar.graduateprojectjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.repository.RestaurantRepository;
import ru.javawebinar.graduateprojectjava.repository.UserRepository;
import ru.javawebinar.graduateprojectjava.to.UserTo;
import ru.javawebinar.graduateprojectjava.util.RestaurantAndUsersUtil;
import ru.javawebinar.graduateprojectjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");
    private final UserRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserService(UserRepository repository,RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository=restaurantRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    public UserTo getWithRestaurants(int id)throws NotFoundException {
        User u=checkNotFoundWithId(repository.findById(id).orElse(null), id);
        List<Restaurant>restaurants=restaurantRepository.getRestaurantsForUser(id);
        return RestaurantAndUsersUtil.transformationToUserTo(u,restaurants);
    }
}
