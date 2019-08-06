package ru.javawebinar.graduateprojectjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class DishTo {
    private final String description;
    private final BigDecimal price;

    @JsonCreator
    public DishTo(@JsonProperty("description") String description,@JsonProperty("price") BigDecimal price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return Objects.equals(description, dishTo.description) &&
                Objects.equals(price, dishTo.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, price);
    }
}
