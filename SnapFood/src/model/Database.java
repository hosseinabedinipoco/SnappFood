package model;

import java.util.ArrayList;

public class Database {

    private static Database database = new Database();

    private ArrayList<Person> customers = new ArrayList<>();

    private ArrayList<Person> restaurantManagers = new ArrayList<>();

    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    private ArrayList<Person> snapFoodManagers = new ArrayList<>();
    {
        snapFoodManagers.add(new SnapFoodManager("kazem", "Abcd1234"));
    }

    private Database() {

    }

    public static Database getInstance() {
        return database;
    }

    public ArrayList<Person> getSnapFoodManagers() {
        return copyWithoutReference(snapFoodManagers);
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) {
        restaurants.remove(restaurant);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public void addRestaurantManger(RestaurantManager restaurantManager) {
        restaurantManagers.add(restaurantManager);
    }

    public void removeRestaurantManager(RestaurantManager restaurantManager) {
        restaurantManagers.remove(restaurantManager);
    }

    public ArrayList<Restaurant> getRestaurants() {
        return copyWithoutReference(restaurants);
    }

    public ArrayList<Person> getCustomers() {
        return copyWithoutReference(customers);
    }

    public ArrayList<Person> getRestaurantManagers() {
        return copyWithoutReference(restaurantManagers);
    }

    private <T>ArrayList<T> copyWithoutReference(ArrayList<T> oldList) {
        ArrayList<T> newList = new ArrayList<>();
        for(T t : oldList) {
            newList.add(t);
        }
        return newList;
    }
}
