package ru.javawebinar.graduateprojectjava;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;

import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;

public class Main {
    public static void main(String[] args) {
        testOn();
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");
        RestaurantService service=context.getBean(RestaurantService.class);
        System.out.println(service.getRestaurantsWithDishForVote());
        System.out.println(service.saveUserVote(new Vote(100004),100001));
    }
}
