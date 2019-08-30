package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.graduateprojectjava.model.Dish;
import ru.javawebinar.graduateprojectjava.web.AbstractRestaurantControllerTest;
import ru.javawebinar.graduateprojectjava.web.json.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.contentJsonD;
import static ru.javawebinar.graduateprojectjava.TestUtil.*;
import static ru.javawebinar.graduateprojectjava.UserTestData.*;
import static ru.javawebinar.graduateprojectjava.util.exception.ErrorType.VALIDATION_ERROR;

class DishCrudControllerTest extends AbstractRestaurantControllerTest {
    private static final String ADMIN_CRUD_DISH=DishCrudController.ADMIN_CRUD_DISH;

    @Test
    void createDishForVote()throws Exception {
        dateTime.setLocalTime(LocalTime.of(8,0));
        Dish expected = new Dish(null,DISH_CREATE.getDescription(),DISH_CREATE.getPrice(),DISH_CREATE.getTime_create_dish());
        ResultActions action = mockMvc.perform(post(ADMIN_CRUD_DISH+'/'+100004)
                .with(userHttpBasic(ADMIN1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatchD(restaurantService.getDishes(100004,100002),List.of(expected,DISH1,DISH2,DISH3));
    }

    @Test
    void deleteDishForVote()throws Exception {
        dateTime.setLocalTime(LocalTime.of(8,0));
        mockMvc.perform(delete(ADMIN_CRUD_DISH +'/'+100008)
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatchD(restaurantService.getDishes(100004,100002),List.of(DISH2,DISH3));
    }

    @Test
    void updateDishForVote()throws Exception {
        dateTime.setLocalTime(LocalTime.of(8,0));
        Dish updated= new Dish(DISH_UPDATE.getId(),DISH_UPDATE.getDescription(),DISH_UPDATE.getPrice(),DISH_UPDATE.getTime_create_dish());
        mockMvc.perform(put(ADMIN_CRUD_DISH + "/update")
                .param("restaurant_id","100004").param("dish_id","100008")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatchD(restaurantService.getDishes(100004,100002), List.of(updated,DISH2,DISH3));
    }

    @Test
    void updateWrongTimeDishForVote()throws Exception {
        dateTime.setLocalTime(LocalTime.of(12,0));
        Dish updated= new Dish(DISH_UPDATE.getDescription(),DISH_UPDATE.getPrice());
        mockMvc.perform(put(ADMIN_CRUD_DISH + "/update")
                .param("restaurant_id","100004").param("dish_id","100008")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(content().string("{\"url\":\"http://localhost/restaurants/admin/dish/update\",\"type\":\"WRONG_TIME\",\"typeMessage\":\"Wrong time\",\"details\":[\"This action cannot be done at this time\"]}"))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void getDishes() throws Exception {
        dateTime.setLocalTime(LocalTime.of(8,0));
        mockMvc.perform(get(ADMIN_CRUD_DISH+'/'+100004)
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonD(DISH1,DISH2,DISH3));
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish( "", null);
        mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_CRUD_DISH+'/'+100004)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish( "", null);
        mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_CRUD_DISH + "/update")
                .param("restaurant_id","100004").param("dish_id","100008")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish( "<script>alert(123)</script>", new BigDecimal("1.1"));
        mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_CRUD_DISH + "/update")
                .param("restaurant_id","100004").param("dish_id","100008")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}