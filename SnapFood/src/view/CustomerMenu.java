package view;

import controller.AppManager;
import controller.EventHandler;
import controller.SortRestaurant;
import model.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu {

    public static void run() {
        System.out.println("1.show restaurant");
        System.out.println("2.show discounts");
        System.out.println("3.profile");
        System.out.println("4.logout");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), CustomerMenu::run, CustomerMenu::showSelectRestaurant,
                    CustomerMenu::showDiscounts, CustomerMenu::profile, CustomerMenu::logout);
        } catch (Exception e) {
            System.out.println("invalid command");
            run();
        }
    }

    public static void showSelectRestaurant() {
        System.out.println("1.fast food");
        System.out.println("2.buffet");
        System.out.println("3.cafe shop");
        System.out.println("4.kebab");
        System.out.println("5.pizza");
        System.out.println("6.all");
        System.out.println("7.back");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), CustomerMenu::showSelectRestaurant,
                    () -> filterRestaurant(TypeOfRestaurant.FASTFOOD), () -> filterRestaurant(TypeOfRestaurant.BUFFET),
                    () -> filterRestaurant(TypeOfRestaurant.CAFESHOP), () -> filterRestaurant(TypeOfRestaurant.KEBAB),
                    () -> filterRestaurant(TypeOfRestaurant.PIZZA), () -> showRestaurant(Database.getInstance().getRestaurants()),
                    CustomerMenu::run);
        } catch (Exception e) {
            System.out.println("invalid command");
            showSelectRestaurant();
        }
    }

    public static void filterRestaurant(TypeOfRestaurant type) {
        showRestaurant(Database.getInstance().getRestaurants().stream().filter(restaurant -> restaurant.getType().equals(type)).
                sorted(new SortRestaurant()).collect(Collectors.toList()));
    }

    public static void showRestaurant(List<Restaurant> restaurants) {
        System.out.println("if you want back press else list number");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getName() + " type: " + restaurants.get(i).getType() + " rate: " +
                    restaurants.get(i).getRate());
        }
        Restaurant selectedRestaurant = selectRestaurant(restaurants);
        if (selectedRestaurant != null) {
            selectMenu(selectedRestaurant);
        } else {
            showSelectRestaurant();
        }
    }

    public static void selectMenu(Restaurant selectedRestaurant) {
        System.out.println("1.STARTER");
        System.out.println("2.ENTREE");
        System.out.println("3.DESSERT");
        System.out.println("4.back");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), () -> selectMenu(selectedRestaurant),
                    () -> filterMenu(Category.STARTER, selectedRestaurant), () -> filterMenu(Category.ENTREE, selectedRestaurant),
                    () -> filterMenu(Category.DESERT, selectedRestaurant), CustomerMenu::showSelectRestaurant);
        } catch (Exception e) {
            System.out.println("invalid command");
            selectMenu(selectedRestaurant);
        }
    }

    public static void filterMenu(Category category, Restaurant restaurant) {
        List<Food> foods = restaurant.getMenu().stream().filter(food -> food.getCategory().equals(category))
                .collect(Collectors.toList());
        showFood(foods, category.toString(), restaurant);
    }

    private static void showFood(List<Food> foods, String category, Restaurant restaurant) {
        System.out.println("if you want back press else list number");
        System.out.println("if you want to buy food press it");
        System.out.println("<<" + category + ">>");
        for (int i = 0; i < foods.size(); i++) {
            System.out.println((i + 1) + ". " + foods.get(i).getName() + " price: " + foods.get(i).getPrice());
        }
        Food selectedfood = selectFood(foods, restaurant);
        if (selectedfood != null) {
            buyFood(selectedfood, restaurant);
        } else {
            selectMenu(restaurant);
        }
    }

    private static void buyFood(Food selectedfood, Restaurant restaurant) {
        System.out.println("if you have discount code press 1");
        int discount = 0;
        if (ScannerWrapper.nextLine().equals("1")) {
            discount = getDiscount();
        }
        if (AppManager.butFood((Customer) FirstMenu.loggedPerson, selectedfood, restaurant, discount)) {
            System.out.println("you bought");
            rate(restaurant);
        } else {
            System.out.println("no enough money");
        }
        AppManager.expected();
        selectMenu(restaurant);
    }

    private static void rate(Restaurant restaurant) {
        System.out.println("if you want rate press 0");
        int rate;
        Customer customer = (Customer) FirstMenu.loggedPerson;
        if(ScannerWrapper.nextLine().equals("0")) {
            rate = getRate();
            restaurant.setRate(customer, rate);
        }
    }

    private static int getRate() {
        System.out.print("rate: ");
        int rate = Integer.parseInt(ScannerWrapper.nextLine());
        if(rate <= 5 && rate >= 1) {
            return rate;
        }
        System.out.println("invalid rate");
        return getRate();
    }

    private static int getDiscount() {
        System.out.print("discount code: ");
        return AppManager.getDiscount(ScannerWrapper.nextLine(), (Customer) FirstMenu.loggedPerson);
    }

    private static Food selectFood(List<Food> foods, Restaurant restaurant) {
        try {
            return foods.get(Integer.parseInt(ScannerWrapper.nextLine()) - 1);
        } catch (Exception e) {
            return null;
        }
    }

    private static Restaurant selectRestaurant(List<Restaurant> restaurants) {
        String input = ScannerWrapper.nextLine();
        try {
            return restaurants.get(Integer.parseInt(input) - 1);
        } catch (Exception e) {
            return null;
        }
    }

    public static void showDiscounts() {
        Customer customer = (Customer) FirstMenu.loggedPerson;
        for (String s : customer.getDiscountCodes().keySet()) {
            System.out.println("discount code: " + s + " discount: " + customer.getDiscountCodes().get(s));
        }
        AppManager.expected();
        run();
    }

    public static void profile() {
        System.out.println("1.change pass");
        System.out.println("2.charging wallet");
        System.out.println("3.show info");
        System.out.println("4.delete acount");
        System.out.println("5.back");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), CustomerMenu::profile, CustomerMenu::changePass,
                    CustomerMenu::chargeWallet, CustomerMenu::showInfo, CustomerMenu::deleteAcount, CustomerMenu::run);
        } catch (Exception e) {
            System.out.println("invalid command");
            profile();
        }
    }

    public static void changePass() {
        if (AppManager.changePass(FirstMenu.loggedPerson)) {
            System.out.println("password changed");
            AppManager.expected();
            profile();
        } else {
            profile();
        }
    }

    public static void chargeWallet() {
        System.out.print("how much do you want charge: ");
        try {
            Customer customer = (Customer) FirstMenu.loggedPerson;
            customer.addBalance(Integer.parseInt(ScannerWrapper.nextLine()));
            AppManager.expected();
            profile();
        } catch (Exception e) {
            System.out.println("invalid command");
            chargeWallet();
        }
    }

    public static void showInfo() {
        FirstMenu.loggedPerson.showInfo();
        AppManager.expected();
        profile();
    }

    public static void deleteAcount() {
        System.out.println("the acount was deleted");
        Database.getInstance().removeCustomer((Customer) FirstMenu.loggedPerson);
        FirstMenu.run();
    }

    public static void logout() {
        System.out.println("you logged out");
        FirstMenu.run();
    }
}
