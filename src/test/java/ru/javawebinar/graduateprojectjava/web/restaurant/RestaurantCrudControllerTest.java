package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.web.AbstractRestaurantControllerTest;
import ru.javawebinar.graduateprojectjava.web.SecurityUtil;
import ru.javawebinar.graduateprojectjava.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.TestUtil.readFromJson;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;


class RestaurantCrudControllerTest extends AbstractRestaurantControllerTest {

    private static final String ADMIN_CRUD_REST= RestaurantCrudController.ADMIN_CRUD_REST;

    @Test
    void createRestaurant()throws Exception {
        SecurityUtil.setId(100002);
        setLocalTime(LocalTime.of(8,0));
        Restaurant expected = new Restaurant(RESTAURANT_CREATE.getDescription());
        ResultActions action = mockMvc.perform(post(ADMIN_CRUD_REST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatchRestList(restaurantService.getRestaurantsForUser(100002),List.of(expected,RESTAURANT_USER100002));
    }

    @Test
    void deleteRestaurant()throws Exception {
        SecurityUtil.setId(100002);
        setLocalTime(LocalTime.of(8,0));
        mockMvc.perform(delete(ADMIN_CRUD_REST +'/'+100004))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getRestaurantsForUser(100002));
    }

    @Test
    void updateRestaurant()throws Exception {
        SecurityUtil.setId(100002);
        setLocalTime(LocalTime.of(8,0));
        Restaurant updated = RESTAURANT_UPDATE;
        mockMvc.perform(put(ADMIN_CRUD_REST + '/' + 100004)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatchRestList(restaurantService.getRestaurantsForUser(100002), List.of(updated));
    }

    @Test
    void getRestaurantsForUser()throws Exception {
        SecurityUtil.setId(100002);
        setLocalTime(LocalTime.of(8,0));
        mockMvc.perform(get(ADMIN_CRUD_REST))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonR(RESTAURANT_USER100002));
    }
}