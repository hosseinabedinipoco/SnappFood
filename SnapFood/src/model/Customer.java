package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Person{

    private HashMap<String, Integer> discountCodes = new HashMap<>();
    public Customer(String name, String password) {
        super(name, password);
    }

    @Override
    public void showInfo() {
        System.out.println("name: "+getName());
        System.out.println("password: "+getPassword());
        System.out.println("balance: "+getBalance());
    }

    public void addDiscountCode(String discountCode, Integer discount) {
        discountCodes.put(discountCode, discount);
    }

    public void removeDiscountCode(String discountCode) {
        discountCodes.remove(discountCode);
    }

    public HashMap<String, Integer> getDiscountCodes() {
        return discountCodes;
    }
}
