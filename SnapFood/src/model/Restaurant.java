package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Restaurant{

    private String name;

    private TypeOfRestaurant type;

    private RestaurantManager manager;

    private HashMap<Customer, Integer> rates = new HashMap<>();

    private float rate;

    private HashMap<Customer, ArrayList<String>> comments = new HashMap<>();

    private ArrayList<Food> menu = new ArrayList<>();

    public Restaurant(String name, TypeOfRestaurant type, RestaurantManager manager) {
        this.name = name;
        this.type = type;
        this.manager = manager;
    }

    public void setType(TypeOfRestaurant type) {
        this.type = type;
    }

    public TypeOfRestaurant getType() {
        return type;
    }

    public void addFood(Food food) {
        menu.add(food);
    }

    public void removeFood(Food food) {
        menu.remove(food);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Food> getMenu() {
        return copyWithoutReference(menu);
    }

    public float getRate() {
        return rate;
    }

    public void setRate(Customer customer, int rate) {
        rates.put(customer, rate);
        int sum = 0;
        for(Customer customer1 : rates.keySet()) {
            sum += rates.get(customer1);
        }
        this.rate = sum / rates.size();
    }

    public HashMap<Customer, ArrayList<String>> getComments() {
        return comments;
    }

    public RestaurantManager getManager() {
        return manager;
    }

    public void setManager(RestaurantManager manager) {
        this.manager = manager;
    }

    private <T>ArrayList<T> copyWithoutReference(ArrayList<T> oldList) {
        ArrayList<T> newList = new ArrayList<>();
        for(T t : oldList) {
            newList.add(t);
        }
        return newList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return Objects.equals(name, that.name) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
