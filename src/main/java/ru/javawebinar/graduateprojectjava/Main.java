package ru.javawebinar.graduateprojectjava;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");
        RestaurantService service=context.getBean(RestaurantService.class);
        System.out.println(service.getRestaurantsWithDishForVote());
    }
}
