package ru.javawebinar.graduateprojectjava.model;

public class AbstractBaseEntity {
    public static final int START_SEQ = 100000;

    protected Integer id;

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
