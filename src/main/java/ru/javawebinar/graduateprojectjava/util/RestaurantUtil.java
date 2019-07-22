package ru.javawebinar.graduateprojectjava.util;

import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.to.RestaurantTo;
import ru.javawebinar.graduateprojectjava.to.TodayTo;

import java.util.*;
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
                RestaurantTo to=new RestaurantTo(idRestaurant,d.getRestaurant().getDescription(), new ArrayList<Dish>(Arrays.asList(d)));
                map.put(idRestaurant,to);
            }
        }
        return map.values().stream().sorted((r1,r2)->r1.getDescription().compareTo(r2.getDescription())).collect(Collectors.toList());
    }

    public static List<TodayTo> transformToTodayTo(List<Vote>votes, List<Restaurant>restaurants){
        Map<Integer,Integer>mapVotes=new HashMap<>();
        votes.forEach(vote -> mapVotes.put(vote.getRestaurant_id(),mapVotes.getOrDefault(vote.getRestaurant_id(),0)+1));
        Map<Integer,String>mapRest=new HashMap<>();
        restaurants.forEach(r->mapRest.put(r.getId(), mapRest.getOrDefault(r.getId(),"")+r.getDescription()));
        int bestCount=0;
        for (Integer i:mapVotes.values()) {
            if(i>=bestCount)bestCount=i;
        }
        int finalBestCount = bestCount;
        return mapVotes.keySet().stream()
                .map(id->new TodayTo(mapVotes.get(id),mapRest.get(id),mapVotes.get(id)== finalBestCount))
                .sorted((today1,today2)->today1.getDescription().compareTo(today2.getDescription()))
                .collect(Collectors.toList());
    }
}
