package ru.javawebinar.graduateprojectjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.graduateprojectjava.model.Role;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.service.UserService;
import ru.javawebinar.graduateprojectjava.web.AbstractControllerTest;
import ru.javawebinar.graduateprojectjava.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduateprojectjava.TestUtil.readFromJson;
import static ru.javawebinar.graduateprojectjava.UserTestData.*;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID1))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN1));
    }

    @Test
    void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID1))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN1,ADMIN2,USER2);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(userService.get(USER_ID1), updated);
    }

    @Test
    void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN1,ADMIN2,expected,USER1,USER2);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN1,ADMIN2,USER1,USER2));
    }
}