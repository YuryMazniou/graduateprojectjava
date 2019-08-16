package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.graduateprojectjava.web.AbstractRestaurantControllerTest;
import java.time.LocalTime;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.TestUtil.userHttpBasic;
import static ru.javawebinar.graduateprojectjava.UserTestData.*;
import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.setLocalTime;

class StatisticControllerTest extends AbstractRestaurantControllerTest {
    private static final String STATISTIC_URL=StatisticController.STATISTIC_REST;

    @Test
    void getRestaurantsWithDishForVote()throws Exception {
        setLocalTime(LocalTime.of(10,0));
        mockMvc.perform(get(STATISTIC_URL+"/listforvotes")
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonStatisticListForVote(List.of(RESTAURANT_TO_1, RESTAURANT_TO_2)));
    }

    @Test
    void getTodayRestaurantsStatistic()throws Exception {
        setLocalTime(LocalTime.of(12,0));
        mockMvc.perform(get(STATISTIC_URL+"/resultofday"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonStatisticToday(TODAY_TO_LIST));
    }

    @Test
    void getAllTimeRestaurantStatistic()throws Exception {
        mockMvc.perform(get(STATISTIC_URL+"/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonStatisticAllTime(ALL_TIME_TO_LIST));
    }

    @Test
    void getHistoryDishForRestaurant() throws Exception {
        mockMvc.perform(get(STATISTIC_URL+"/dish/"+100004)
                .with(userHttpBasic(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonStatisticDish(List.of(STATISTIC_TO1,STATISTIC_TO2)));
    }
}