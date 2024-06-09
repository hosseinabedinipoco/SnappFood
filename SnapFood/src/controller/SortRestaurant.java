package controller;

import model.Restaurant;

import java.util.Comparator;

public class SortRestaurant implements Comparator<Restaurant> {


    @Override
    public int compare(Restaurant restaurant1, Restaurant restaurant2) {
        if(restaurant1.getRate() > restaurant2.getRate()) {
            return -1;
        } else if (restaurant1.getRate() == restaurant2.getRate()) {
            return 0;
        } else {
            return 1;
        }
    }
}
