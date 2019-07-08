package ru.javawebinar.graduateprojectjava.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.service.DishService;

import static org.slf4j.LoggerFactory.getLogger;

public class DishController {
    private static final Logger log = getLogger(DishController.class);

    private DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
}
