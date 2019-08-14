package ru.javawebinar.graduateprojectjava.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.javawebinar.graduateprojectjava.model.AbstractBaseEntity;

import java.time.LocalDate;

import static ru.javawebinar.graduateprojectjava.util.DateTimeUtil.*;

public class AbstractRestaurantControllerTest extends AbstractControllerTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        testOn();
        setLocalDate(LocalDate.of(2019,7,3));
    }
    @AfterEach
    void offTest(){
        //SecurityUtil.setAuthUserId(AbstractBaseEntity.START_SEQ);
        testOff();
    }
}
