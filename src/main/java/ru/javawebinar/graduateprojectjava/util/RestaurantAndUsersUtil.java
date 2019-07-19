package ru.javawebinar.graduateprojectjava.util;

import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.to.UserTo;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantAndUsersUtil {

    public static List<RestaurantTo> transformToRestaurantTo(List<Dish>dishes){
        Map<Integer,RestaurantTo>map=new HashMap<>();
        for (Dish d:dishes) {
            int idRestaurant=d.getRestaurant().getId();
            if(map.containsKey(idRestaurant)){
                map.get(idRestaurant).getList_of_dish().add(d);
            }
            else{
                RestaurantTo to=new RestaurantTo(idRestaurant,d.getRestaurant().getDescription(), new ArrayList<Dish>(Arrays.asList(d)));
                map.put(idRestaurant,to);
            }
        }
        return map.values().stream().sorted((r1,r2)->r1.getDescription().compareTo(r2.getDescription())).collect(Collectors.toList());
    }
    public static UserTo transformationToUserTo(User user, List<Restaurant>restaurants){
        UserTo userTo=new UserTo(user);
        userTo.setRestaurants(restaurants);
        return userTo;
    }
}
