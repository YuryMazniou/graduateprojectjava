package ru.javawebinar.graduateprojectjava;

import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.to.AllTimeTo;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;

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
    public static Dish DISH_CREATE=new Dish(null,"create",new BigDecimal("10.5000"),LocalDate.of(2019,7,3));
    public static Dish DISH_UPDATE=new Dish(DISH1.getId(),"update dish",new BigDecimal("1.1000"),DISH1.getTime_create_dish());

    public static final Vote VOTE_ADMIN2=new Vote(100019,100005,LocalDate.of(2019,7,3));
    public static final Vote VOTE_UPDATE=new Vote(100014,100004,LocalDate.of(2019,7,3));
    public static final Vote VOTE_GET=new Vote(100014,100005,LocalDate.of(2019,7,3));

    public static Restaurant RESTAURANT_CREATE=new Restaurant("Create Restaurant");
    public static final Restaurant RESTAURANT_USER100002=new Restaurant(100004,"Garage");
    public static Restaurant RESTAURANT_UPDATE=new Restaurant(RESTAURANT_USER100002.getId(),RESTAURANT_USER100002.getDescription());

    public static final RestaurantTo RESTAURANT_TO_1 =new RestaurantTo(100004,"Garage", DISH_LIST1);
    public static final RestaurantTo RESTAURANT_TO_2 =new RestaurantTo(100005,"PizzaMania", DISH_LIST2);

    public static final TodayTo TODAY_TO_1=new TodayTo(2,"Garage",true);
    public static final TodayTo TODAY_TO_2=new TodayTo(1,"PizzaMania",false);
    public static final List<TodayTo>TODAY_TO_LIST=List.of(TODAY_TO_1,TODAY_TO_2);

    public static final AllTimeTo ALL_TIME_TO1=new AllTimeTo("Garage",LocalDate.of(2019,7,2),false);
    public static final AllTimeTo ALL_TIME_TO2=new AllTimeTo("PizzaMania",LocalDate.of(2019,7,2),true);
    public static final AllTimeTo ALL_TIME_TO3=new AllTimeTo("Garage",LocalDate.of(2019,7,1),true);
    public static final AllTimeTo ALL_TIME_TO4=new AllTimeTo("PizzaMania",LocalDate.of(2019,7,1),false);
    public static final List<AllTimeTo>ALL_TIME_TO_LIST=List.of(ALL_TIME_TO1,ALL_TIME_TO2,ALL_TIME_TO3,ALL_TIME_TO4);

    public static void assertMatchR(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("list_of_dish").isEqualTo(expected);
    }
    public static void assertMatchD(List<Dish> actual, List<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user","restaurant").isEqualTo(expected);
    }
    public static <T> void assertMatchList(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualTo(expected);
    }
    public static void assertMatchRestList(List<Restaurant> actual, List<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes","user").isEqualTo(expected);
    }
}
