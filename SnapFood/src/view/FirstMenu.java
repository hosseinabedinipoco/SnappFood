package view;

import controller.AppManager;
import controller.EventHandler;
import controller.EventHandlerInterface;
import model.*;
import java.util.ArrayList;

public class FirstMenu {

    public static Person loggedPerson;

    public static void main(String[] args) {
        Database.getInstance().addCustomer(new Customer("mmd", "Abcd1234"));
        Database.getInstance().addCustomer(new Customer("alireza", "Abcd1234"));
        RestaurantManager manager = new RestaurantManager("mohammad", "Abcd1234");
        Restaurant restaurant = new Restaurant("karoon", TypeOfRestaurant.FASTFOOD, manager);
        manager.setRestaurant(restaurant);
        Database.getInstance().addRestaurantManger(manager);
        Database.getInstance().addRestaurant(restaurant);
        run();
    }

    public static void run() {
        System.out.println("1.snap food manager");
        System.out.println("2.restaurant manager");
        System.out.println("3.customer");
        System.out.println("4.exit");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input),FirstMenu::run,
                    () -> loggIn(Database.getInstance().getSnapFoodManagers(), SnapFoodMangerMenu::run),
                    () -> loggIn(Database.getInstance().getRestaurantManagers(), RestaurantManagerMenu::run)
                    , FirstMenu::registerOrLoggIn, FirstMenu::exit);
        }catch (Exception e) {
            System.out.println("invalid command");
            run();
        }
    }

    public static void loggIn(ArrayList<Person> people, EventHandlerInterface methodReference) {
        String name = getName();
        Person person = AppManager.getPersonWithName(people, name);
        if(person != null) {
            String password = getPass();
            if(AppManager.verifyPass(password, person)) {
                loggedPerson = person;
                System.out.println("you logged in");
                methodReference.run();
            } else {
                System.out.println("incorrect password");
                expected(FirstMenu::run, () -> loggIn(people, methodReference));
            }
        } else {
            System.out.println("there is no person");
            expected(FirstMenu::run, () -> loggIn(people, methodReference));
        }
    }

    public static void expected(EventHandlerInterface backMethodReference, EventHandlerInterface thisMethodReference) {
        if(wantToBack()) {
            backMethodReference.run();
        } else {
            thisMethodReference.run();
        }
    }

    private static boolean wantToBack() {
        System.out.println("if want to back press 1");
        return ScannerWrapper.nextLine().equals("1");
    }

    public static String getPass() {
        System.out.print("password: ");
        String password = ScannerWrapper.nextLine();
        if(AppManager.isValidPass(password)) {
            return password;
        } else {
            System.out.println("password is weak");
            return getPass();
        }
    }

    public static String getName() {
        System.out.print("name: ");
        String name = ScannerWrapper.nextLine();
        if(AppManager.isValidName(name)) {
            return name;
        } else {
            System.out.println("name is invalid");
            return getName();
        }
    }

    public static void registerOrLoggIn() {
        System.out.println("1.register");
        System.out.println("2.logg in");
        System.out.println("3.back");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), FirstMenu::registerOrLoggIn, FirstMenu::register,
                    () -> loggIn(Database.getInstance().getCustomers(), CustomerMenu::run), FirstMenu::run);
        }catch (Exception e) {
            System.out.println("invalid command");
            registerOrLoggIn();
        }
    }

    public static void register() {
        String name = getName();
        Person person = AppManager.getPersonWithName(Database.getInstance().getCustomers(), name);
        if(person == null) {
            String password = getPass();
            Customer customer = new Customer(name, password);
            Database.getInstance().addCustomer(customer);
            registerOrLoggIn();
        } else {
            System.out.println("there is a customer with this name");
        }
    }

    public static void exit() {
        System.out.println("good bye");
    }
}
