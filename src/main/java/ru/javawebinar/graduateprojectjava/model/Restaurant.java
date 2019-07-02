package ru.javawebinar.graduateprojectjava.model;


public class Restaurant extends AbstractBaseEntity {
    private String description;
    private Integer user_id;

    public Restaurant() {
    }

    public Restaurant(String description, Integer user_id) {
        this(null,description,user_id);
    }

    public Restaurant(Integer id,String description, Integer user_id) {
        super(id);
        this.description = description;
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
