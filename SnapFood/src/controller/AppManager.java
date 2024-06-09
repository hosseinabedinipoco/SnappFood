package controller;

import model.Customer;
import model.Food;
import model.Person;
import model.Restaurant;
import view.FirstMenu;
import view.ScannerWrapper;

import java.util.ArrayList;

public class AppManager {


    public static boolean verifyPass(String password, Person person) {
        return password.equals(person.getPassword());
    }

    public static <T>boolean isAvaialable(T t, ArrayList<T> ts) {
        return ts.contains(t);
    }

    public static Person getPersonWithName(ArrayList<Person> people, String name) {
        for(Person person : people) {
            if(person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

    public static boolean isValidPass(String password) {
        return password.matches("[[a-z]+[0-9]+[A-Z]+]{5,}");
    }

    public static boolean isValidName(String name) {
        return name.matches("[[a-zA-Z]+[0-9_]*]+");
    }

    public static boolean changePass(Person person) {
        System.out.print("old password: ");
        String pass = FirstMenu.getPass();
        if(verifyPass(pass, person)) {
            System.out.print("new password: ");
            pass = FirstMenu.getPass();
            person.setPassword(pass);
            return true;
        } else {
            System.out.println("password is incorrect");
            return false;
        }
    }

    public static void expected() {
        System.out.println("if you want back press 0");
        while (!ScannerWrapper.nextLine().equals("0")){

        }
    }

    public static int getDiscount(String s, Customer customer) {
        if(customer.getDiscountCodes().containsKey(s)) {
            return customer.getDiscountCodes().get(s);
        } else {
            System.out.println("discount no available");
            return 0;
        }
    }

    public static boolean butFood(Customer loggedPerson, Food selectedfood, Restaurant restaurant, int discount) {
        if(loggedPerson.getBalance() < selectedfood.getPrice() - discount) {
            return false;
        } else {
            loggedPerson.lessBalance(selectedfood.getPrice() - discount);
            restaurant.getManager().addBalance(selectedfood.getPrice());
            return true;
        }
    }
}
