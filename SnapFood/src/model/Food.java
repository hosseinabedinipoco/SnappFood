package model;

import java.util.Objects;

public class Food {

    private String name;

    private Category category;

    private int price;

    private int cost;

    public Food(String name, int price, int cost, Category category) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCost() {
        return cost;
    }

    public int getPrice() {
        return price;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
