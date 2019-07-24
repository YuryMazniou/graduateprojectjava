package ru.javawebinar.graduateprojectjava;

import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantServiceData {
    public static final Dish DISH1=new Dish(100006,"stake",new BigDecimal("5.5000"), LocalDate.of(2019,7,3));
    public static final Dish DISH2=new Dish(100007,"vegetables",new BigDecimal("4.5000"),LocalDate.of(2019,7,3));
    public static final Dish DISH3=new Dish(100008,"wine",new BigDecimal("9.5100"),LocalDate.of(2019,7,3));
    public static final Dish DISH4=new Dish(100009,"chicken",new BigDecimal("5.1500"),LocalDate.of(2019,7,3));
    public static final Dish DISH5=new Dish(100010,"fruit",new BigDecimal("4.1500"),LocalDate.of(2019,7,3));
    public static final Dish DISH6=new Dish(100011,"milk",new BigDecimal("2.1500"),LocalDate.of(2019,7,3));
    public static final List<Dish>DISH_LIST1=List.of(DISH1,DISH2,DISH3);
    public static final List<Dish>DISH_LIST2=List.of(DISH4,DISH5,DISH6);
    public static final RestaurantTo RESTAURANT1=new RestaurantTo(100004,"Garage", DISH_LIST1);
    public static final RestaurantTo RESTAURANT2=new RestaurantTo(100005,"PizzaMania", DISH_LIST2);

    public static void assertMatchR(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("list_of_dish").isEqualTo(expected);
    }
    public static void assertMatchD(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
