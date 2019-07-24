package ru.javawebinar.graduateprojectjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.repository.RestaurantRepository;
import ru.javawebinar.graduateprojectjava.repository.UserRepository;

import java.util.List;

import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.graduateprojectjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository,RestaurantRepository restaurantRepository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) { checkNotFoundWithId(repository.delete(id)!=0, id);}

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
}
