package ru.javawebinar.graduateprojectjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.graduateprojectjava.model.Role;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.graduateprojectjava.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {
    @Autowired
    private UserService service;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass",  false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(created, newUser);
        assertMatch(service.getAll(), ADMIN1,ADMIN2, newUser, USER1,USER2);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(ADMIN_ID1);
        assertMatch(service.getAll(), ADMIN2,USER1,USER2);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = service.get(USER_ID1);
        assertMatch(user, USER1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("user1@yandex.ru");
        assertMatch(user, USER1);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.getRoles().add(Role.ROLE_ADMIN);
        service.update(updated);
        assertMatch(service.get(USER_ID1), updated);
    }

    @Test
    void updateRoles() throws Exception{
        User updated = new User(ADMIN1);
        updated.getRoles().remove(Role.ROLE_USER);
        service.update(updated);
        assertMatch(service.get(ADMIN_ID1), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN1,ADMIN2, USER1,USER2);
    }

    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "u", "mail@yandex.ru", "password",  true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "p",  true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
    }
}
