package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.util.exception.NotFoundException;
import ru.javawebinar.graduateprojectjava.web.AbstractRestaurantControllerTest;
import ru.javawebinar.graduateprojectjava.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduateprojectjava.RestaurantServiceData.*;
import static ru.javawebinar.graduateprojectjava.TestUtil.*;
import static ru.javawebinar.graduateprojectjava.TestUtil.readFromJson;
import static ru.javawebinar.graduateprojectjava.UserTestData.ADMIN2;
import static ru.javawebinar.graduateprojectjava.UserTestData.USER2;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoteCrudControllerTest extends AbstractRestaurantControllerTest {
    private static final String PROFILE_CRUD_VOTE=VoteCrudController.PROFILE_CRUD_VOTE;

    @Test
    void saveUserVote() throws Exception {
        dateTime.setLocalTime(LocalTime.of(10,0));
        Vote expected = new Vote(VOTE_CREATE.getRestaurant_id(),VOTE_CREATE.getTime_create_vote()) ;
        ResultActions action = mockMvc.perform(put(PROFILE_CRUD_VOTE+"/100005")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN2))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Vote returned = readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatchV(List.of(restaurantService.getVoteToday(100003)),List.of(expected));
    }

    @Test
    void deleteVote() throws Exception {
        dateTime.setLocalTime(LocalTime.of(10,0));
        mockMvc.perform(delete(PROFILE_CRUD_VOTE +'/'+100016)
                .with(userHttpBasic(USER2)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class,()->restaurantService.getVoteToday(100001));
    }

    @Test
    void getVoteToday() throws Exception {
        dateTime.setLocalTime(LocalTime.of(10,0));
        mockMvc.perform(get(PROFILE_CRUD_VOTE)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_GET));
    }
}