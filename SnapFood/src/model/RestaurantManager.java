package model;

import java.util.ArrayList;

public class RestaurantManager extends Person{

    private Restaurant restaurant;

    public RestaurantManager(String name, String password) {
        super(name, password);
    }

    @Override
    public void showInfo() {
        System.out.println("name: "+getName());
        System.out.println("password: "+getPassword());
        System.out.println("balance: "+getBalance());
        System.out.println("restaurant name: "+restaurant.getName());;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
