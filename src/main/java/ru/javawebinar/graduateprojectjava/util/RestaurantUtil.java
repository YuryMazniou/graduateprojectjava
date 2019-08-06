package ru.javawebinar.graduateprojectjava.util;

import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.model.HistoryRestaurantObject;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.to.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static List<RestaurantForVoteTo> transformToRestaurantTo(List<Dish>dishes){
        Map<Integer, RestaurantForVoteTo>map=new HashMap<>();
        for (Dish d:dishes) {
            int idRestaurant=d.getRestaurant().getId();
            if(map.containsKey(idRestaurant)){
                map.get(idRestaurant).getList_of_dish().add(d);
            }
            else{
                RestaurantForVoteTo to=new RestaurantForVoteTo(idRestaurant,d.getRestaurant().getDescription(), new ArrayList<Dish>(Arrays.asList(d)));
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
    public static List<AllTimeTo> transformToAllTimeTo(List<HistoryRestaurantObject>objects){
        Map<LocalDate,Integer>map=new HashMap<>();
        for (HistoryRestaurantObject o:objects) {
            LocalDate date=o.getVotes_date();
            Integer countObj=o.getCounts();
            if(map.containsKey(date)){
                Integer countMaP=map.get(o.getVotes_date());
                if(countObj>countMaP)map.put(date,countObj);
            }
            else
                map.put(date,countObj);
        }
        return objects.stream()
                .map(obj->new AllTimeTo(obj.getDescription(),obj.getVotes_date(), map.get(obj.getVotes_date()).equals(obj.getCounts())))
                .sorted(new Comparator<AllTimeTo>() {
                    @Override
                    public int compare(AllTimeTo o1, AllTimeTo o2) {
                        int dateCompare=o2.getLocalDate().compareTo(o1.getLocalDate());
                        return dateCompare==0?o1.getDescription().compareTo(o2.getDescription()):dateCompare;
                    }
                })
                .collect(Collectors.toList());
    }
    public static List<RestaurantStatisticTo> transformToRestaurantStatisticTo(List<Dish> dishes,List<HistoryRestaurantObject> objects){
        Map<LocalDate,List<DishTo>> mapOfDishTo=new HashMap<>();
        for (Dish d:dishes) {
            if(mapOfDishTo.containsKey(d.getTime_create_dish()))
                mapOfDishTo.get(d.getTime_create_dish()).add(new DishTo(d.getDescription(),d.getPrice()));
            else {
                List<DishTo>dishTos=new ArrayList<>();
                dishTos.add(new DishTo(d.getDescription(), d.getPrice()));
                mapOfDishTo.put(d.getTime_create_dish(), dishTos);
            }
        }
        return objects.stream()
                .map(o->new RestaurantStatisticTo(mapOfDishTo.get(o.getVotes_date()),o.getCounts(),o.getVotes_date()))
                .sorted((r1,r2)->r2.getVotesDate().compareTo(r1.getVotesDate()))
                .collect(Collectors.toList());
    }
}
