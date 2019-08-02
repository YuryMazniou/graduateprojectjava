package ru.javawebinar.graduateprojectjava.web;


import ru.javawebinar.graduateprojectjava.model.AbstractBaseEntity;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static void setId(int id) {
        SecurityUtil.id = id;
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

}