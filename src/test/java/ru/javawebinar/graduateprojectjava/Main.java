package ru.javawebinar.graduateprojectjava;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static ru.javawebinar.graduateprojectjava.TestUtil.mockAuthorize;
import static ru.javawebinar.graduateprojectjava.UserTestData.USER1;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;

public class Main {
    public static void main(String[] args) {
        testOn();
        setLocalDate(LocalDate.of(2019,7,3));
        setLocalTime(LocalTime.of(10,0));

        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");

        mockAuthorize(USER1);

        System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

        RestaurantService service=context.getBean(RestaurantService.class);

        service.getRestaurantsWithDishForVote().forEach(System.out::println);
    }
}
