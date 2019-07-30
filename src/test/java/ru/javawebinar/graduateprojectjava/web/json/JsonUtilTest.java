package ru.javawebinar.graduateprojectjava.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.graduateprojectjava.model.Dish;

import java.util.List;

import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;

class JsonUtilTest {

    @Test
    void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(DISH1);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        assertMatchD(List.of(dish), List.of(DISH1));
    }

    @Test
    void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(DISH_LIST1);
        System.out.println(json);
        List<Dish> dishes = JsonUtil.readValues(json, Dish.class);
        assertMatchD(dishes, DISH_LIST1);
    }
}