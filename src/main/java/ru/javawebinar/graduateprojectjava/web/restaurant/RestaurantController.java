package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.repository.RestaurantRepository;

import static org.slf4j.LoggerFactory.getLogger;

public class RestaurantController {
    private static final Logger log = getLogger(RestaurantController.class);
    private RestaurantRepository rest;

    @Autowired
    public RestaurantController(RestaurantRepository rest) {
        this.rest = rest;
    }
}
