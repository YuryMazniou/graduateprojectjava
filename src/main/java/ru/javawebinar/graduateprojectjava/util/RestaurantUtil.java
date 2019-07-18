package ru.javawebinar.graduateprojectjava.util;

import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static List<RestaurantTo> transformToRestaurantTo(List<Dish>dishes){
        Map<Integer,RestaurantTo>map=new HashMap<>();
        for (Dish d:dishes) {
            int idRestaurant=d.getRestaurant().getId();
            if(map.containsKey(idRestaurant)){
                map.get(idRestaurant).getList_of_dish().add(d);
            }
            else{
                RestaurantTo to=new RestaurantTo(idRestaurant,d.getRestaurant().getDescription(),List.of(d));
                map.put(idRestaurant,to);
            }
        }
        return map.values().stream().sorted().collect(Collectors.toList());
    }
}
