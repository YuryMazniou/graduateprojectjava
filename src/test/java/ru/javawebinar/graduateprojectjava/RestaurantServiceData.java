package ru.javawebinar.graduateprojectjava;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.to.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.graduateprojectjava.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.graduateprojectjava.TestUtil.readListFromJsonMvcResult;

public class RestaurantServiceData {
    public static final Dish DISH1=new Dish(100008,"stake",new BigDecimal("5.5000"), LocalDate.of(2019,7,3));
    public static final Dish DISH2=new Dish(100009,"vegetables",new BigDecimal("4.5000"),LocalDate.of(2019,7,3));
    public static final Dish DISH3=new Dish(100010,"wine",new BigDecimal("9.5100"),LocalDate.of(2019,7,3));
    public static final Dish DISH4=new Dish(100011,"chicken",new BigDecimal("5.1500"),LocalDate.of(2019,7,3));
    public static final Dish DISH5=new Dish(100012,"fruit",new BigDecimal("4.1500"),LocalDate.of(2019,7,3));
    public static final Dish DISH6=new Dish(100013,"milk",new BigDecimal("2.1500"),LocalDate.of(2019,7,3));
    public static final List<Dish>DISH_LIST1=List.of(DISH1,DISH2,DISH3);
    public static final List<Dish>DISH_LIST2=List.of(DISH4,DISH5,DISH6);
    public static final Dish DISH_CREATE=new Dish(null,"create",new BigDecimal("10.5000"),LocalDate.of(2019,7,3));
    public static final Dish DISH_UPDATE=new Dish(DISH1.getId(),"update dish",new BigDecimal("1.1000"),DISH1.getTime_create_dish());

    public static final Vote VOTE_ADMIN2=new Vote(100021,100005,LocalDate.of(2019,7,3));
    public static final Vote VOTE_CREATE=new Vote(100005,LocalDate.of(2019,7,3));
    public static final Vote VOTE_UPDATE=new Vote(100016,100004,LocalDate.of(2019,7,3));
    public static final Vote VOTE_GET=new Vote(100016,100005,LocalDate.of(2019,7,3));

    public static final Restaurant RESTAURANT_CREATE=new Restaurant("Create Restaurant");
    public static final Restaurant RESTAURANT_USER100002=new Restaurant(100004,"Garage");
    public static final Restaurant RESTAURANT_UPDATE=new Restaurant(RESTAURANT_USER100002.getId(),"update");

    public static final DishTo DISH_TO1=new DishTo("stake",new BigDecimal("5.5000"));
    public static final DishTo DISH_TO2=new DishTo("vegetables",new BigDecimal("4.5000"));
    public static final DishTo DISH_TO3=new DishTo("wine",new BigDecimal("9.5100"));
    public static final DishTo DISH_TO4=new DishTo("chicken",new BigDecimal("5.1500"));
    public static final DishTo DISH_TO5=new DishTo("fruit",new BigDecimal("4.1500"));
    public static final DishTo DISH_TO6=new DishTo("milk",new BigDecimal("2.1500"));

    public static final RestaurantForVoteTo RESTAURANT_TO_1 =new RestaurantForVoteTo(100004,"Garage",List.of(DISH_TO1,DISH_TO2,DISH_TO3) );
    public static final RestaurantForVoteTo RESTAURANT_TO_2 =new RestaurantForVoteTo(100005,"PizzaMania",List.of(DISH_TO4,DISH_TO5,DISH_TO6));

    public static final TodayTo TODAY_TO_1=new TodayTo(2,"Garage",true);
    public static final TodayTo TODAY_TO_2=new TodayTo(1,"PizzaMania",false);
    public static final List<TodayTo>TODAY_TO_LIST=List.of(TODAY_TO_1,TODAY_TO_2);

    public static final AllTimeTo ALL_TIME_TO1=new AllTimeTo("Garage",LocalDate.of(2019,7,2),false);
    public static final AllTimeTo ALL_TIME_TO2=new AllTimeTo("PizzaMania",LocalDate.of(2019,7,2),true);
    public static final AllTimeTo ALL_TIME_TO3=new AllTimeTo("Garage",LocalDate.of(2019,7,1),true);
    public static final AllTimeTo ALL_TIME_TO4=new AllTimeTo("PizzaMania",LocalDate.of(2019,7,1),false);
    public static final List<AllTimeTo>ALL_TIME_TO_LIST=List.of(ALL_TIME_TO1,ALL_TIME_TO2,ALL_TIME_TO3,ALL_TIME_TO4);

    public static final RestaurantStatisticTo STATISTIC_TO1=new RestaurantStatisticTo(List.of(new DishTo("stake",new BigDecimal("5.5000"))),12345,LocalDate.of(2019,7,2));
    public static final RestaurantStatisticTo STATISTIC_TO2=new RestaurantStatisticTo(List.of(new DishTo("chicken",new BigDecimal("5.1500"))),1234,LocalDate.of(2019,7,1));

    public static void assertMatchD(List<Dish> actual, List<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user","restaurant").isEqualTo(expected);
    }
    public static void assertMatchV(List<Vote> actual, List<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
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

    public static <T> void assertMatch(Iterable<T> expected){
        assertThat(expected).isEmpty();
    }

    public static ResultMatcher contentJsonR(Restaurant ... expected) {
        return result -> assertMatchRestList(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJsonD(Dish ... expected) {
        return result -> assertMatchD(readListFromJsonMvcResult(result, Dish.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }

    public static ResultMatcher contentJsonStatisticDish(List<RestaurantStatisticTo> expected){
        return result -> assertMatchList(readListFromJsonMvcResult(result,RestaurantStatisticTo.class),expected);
    }

    public static ResultMatcher contentJsonStatisticListForVote(List<RestaurantForVoteTo> expected){
        return result -> assertMatchList(readListFromJsonMvcResult(result,RestaurantForVoteTo.class),expected);
    }

    public static ResultMatcher contentJsonStatisticToday(List<TodayTo> expected){
        return result -> assertMatchList(readListFromJsonMvcResult(result,TodayTo.class),expected);
    }

    public static ResultMatcher contentJsonStatisticAllTime(List<AllTimeTo> expected){
        return result -> assertMatchList(readListFromJsonMvcResult(result,AllTimeTo.class),expected);
    }
}
