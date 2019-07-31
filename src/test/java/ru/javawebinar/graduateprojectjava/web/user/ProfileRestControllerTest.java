package ru.javawebinar.graduateprojectjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.graduateprojectjava.model.Role;
import ru.javawebinar.graduateprojectjava.model.User;
import ru.javawebinar.graduateprojectjava.service.UserService;
import ru.javawebinar.graduateprojectjava.web.AbstractControllerTest;
import ru.javawebinar.graduateprojectjava.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduateprojectjava.UserTestData.*;
import static ru.javawebinar.graduateprojectjava.UserTestData.contentJson;
import static ru.javawebinar.graduateprojectjava.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN1,ADMIN2,USER2);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER_ID1, "newName", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), updated);
    }
}