package ru.javawebinar.graduateprojectjava;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.graduateprojectjava.model.Role;
import ru.javawebinar.graduateprojectjava.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.graduateprojectjava.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.graduateprojectjava.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.graduateprojectjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID1 = START_SEQ;
    public static final int USER_ID2 = START_SEQ+1;
    public static final int ADMIN_ID1 = START_SEQ + 2;
    public static final int ADMIN_ID2 = START_SEQ + 3;

    public static final User USER1 = new User(USER_ID1, "User1", "user1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User USER2 = new User(USER_ID2, "User2", "user2@yandex.ru", "password2", Role.ROLE_USER);
    public static final User ADMIN1 = new User(ADMIN_ID1, "Admin1", "admin1@gmail.com", "admin1", Role.ROLE_ADMIN,Role.ROLE_USER);
    public static final User ADMIN2= new User(ADMIN_ID2, "Admin2", "admin2@gmail.com", "admin2", Role.ROLE_ADMIN,Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }
}
