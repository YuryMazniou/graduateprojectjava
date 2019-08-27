package ru.javawebinar.graduateprojectjava.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;

public class AbstractRestaurantControllerTest extends AbstractControllerTest {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        dateTime.setLocalDate(LocalDate.of(2019,7,3));
    }

    @AfterEach
    void offTest(){
        dateTime.setLocalDate(LocalDate.of(1,1,1));
        dateTime.setLocalTime(LocalTime.of(0,0,1));
    }
}
