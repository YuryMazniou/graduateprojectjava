package ru.javawebinar.graduateprojectjava.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.service.UserService;

import static org.slf4j.LoggerFactory.getLogger;

public class UserController {
    private static final Logger log = getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
