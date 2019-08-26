package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.graduateprojectjava.model.Restaurant;
import ru.javawebinar.graduateprojectjava.util.exception.ErrorType;
import ru.javawebinar.graduateprojectjava.web.AbstractRestaurantControllerTest;
import ru.javawebinar.graduateprojectjava.web.json.JsonUtil;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.TestUtil.*;
import static ru.javawebinar.graduateprojectjava.UserTestData.*;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;


class RestaurantCrudControllerTest extends AbstractRestaurantControllerTest {

    private static final String ADMIN_CRUD_REST= RestaurantCrudController.ADMIN_CRUD_REST;

    @Test
    void createRestaurant()throws Exception {
        setLocalTime(LocalTime.of(8,0));
        Restaurant expected = new Restaurant(RESTAURANT_CREATE.getDescription());
        ResultActions action = mockMvc.perform(post(ADMIN_CRUD_REST)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatchRestList(restaurantService.getRestaurantsForUser(100002),List.of(expected,RESTAURANT_USER100002));
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid=new Restaurant();
        mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_CRUD_REST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    void createWrongTimeRestaurant()throws Exception {
        setLocalTime(LocalTime.of(12,0));
        Restaurant expected = new Restaurant(RESTAURANT_CREATE.getDescription());
        mockMvc.perform(post(ADMIN_CRUD_REST)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(content().string("{\"url\":\"http://localhost/restaurants/admin/restaurant\",\"type\":\"WRONG_TIME\",\"details\":[\"this action cannot be done at this time\"]}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteRestaurant()throws Exception {
        setLocalTime(LocalTime.of(8,0));
        mockMvc.perform(delete(ADMIN_CRUD_REST +'/'+100004)
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getRestaurantsForUser(100002));
    }

    @Test
    void updateRestaurant()throws Exception {
        setLocalTime(LocalTime.of(8,0));
        Restaurant updated = RESTAURANT_UPDATE;
        mockMvc.perform(put(ADMIN_CRUD_REST + '/' + 100004)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatchRestList(restaurantService.getRestaurantsForUser(100002), List.of(updated));
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid=new Restaurant();
        mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_CRUD_REST + '/' + 100004)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    void getRestaurantsForUser()throws Exception {
        setLocalTime(LocalTime.of(8,0));
        mockMvc.perform(get(ADMIN_CRUD_REST)
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonR(RESTAURANT_USER100002));
    }
}