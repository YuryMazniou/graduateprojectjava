package ru.javawebinar.graduateprojectjava;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import static ru.javawebinar.graduateprojectjava.TestUtil.mockAuthorize;
import static ru.javawebinar.graduateprojectjava.UserTestData.USER1;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext("spring/spring-app-test.xml","spring/spring-db.xml");
        DateTimeTest dateTime=context.getBean(DateTimeTest.class);

        dateTime.setLocalDate(LocalDate.of(2019,7,3));
        dateTime.setLocalTime(LocalTime.of(10,0));

        mockAuthorize(USER1);

        System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

        RestaurantService service=context.getBean(RestaurantService.class);

        service.getRestaurantsWithDishForVote().forEach(System.out::println);
    }
}
