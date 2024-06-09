package model;

public class SnapFoodManager extends Person{

    public SnapFoodManager(String name, String password) {
        super(name, password);
    }

    @Override
    public void showInfo() {
        System.out.println("name: "+getName());
        System.out.println("password: "+getPassword());
        System.out.println("balance: "+getBalance());
    }
}
