package view;

import controller.AppManager;
import controller.EventHandler;
import model.*;

public class SnapFoodMangerMenu {

    public static void run() {
        System.out.println("1.show restaurant");
        System.out.println("2.set discount");
        System.out.println("3.delete customer");
        System.out.println("4.add restaurant manager");
        System.out.println("5.delete restaurant manager");
        System.out.println("6.profile");
        System.out.println("7.logout");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), SnapFoodMangerMenu::run, SnapFoodMangerMenu::showRestaurant,
                    SnapFoodMangerMenu::setDiscount, SnapFoodMangerMenu::deleteCustomer,
                    SnapFoodMangerMenu::addRestaurantManager, SnapFoodMangerMenu::deleteRestaurantManager,
                    SnapFoodMangerMenu::profile, SnapFoodMangerMenu::logOut);
        }catch (Exception e) {
            System.out.println("invalid command");
            run();
        }
    }

    public static void showRestaurant() {
        System.out.println("<<FAST FOOD>>");
        Database.getInstance().getRestaurants().stream().filter(restaurant -> restaurant.getType().equals(TypeOfRestaurant.FASTFOOD)).
                forEach(restaurant -> System.out.println(restaurant.getName()+" manger: "+restaurant.getManager().getName()));
        System.out.println("<<CAFE SHOP>>");
        Database.getInstance().getRestaurants().stream().filter(restaurant -> restaurant.getType().equals(TypeOfRestaurant.CAFESHOP)).
                forEach(restaurant -> System.out.println(restaurant.getName()+" manger: "+restaurant.getManager().getName()));
        System.out.println("<<BUFFET>>");
        Database.getInstance().getRestaurants().stream().filter(restaurant -> restaurant.getType().equals(TypeOfRestaurant.BUFFET)).
                forEach(restaurant -> System.out.println(restaurant.getName()+" manger: "+restaurant.getManager().getName()));
        System.out.println("<<KEBAB>>");
        Database.getInstance().getRestaurants().stream().filter(restaurant -> restaurant.getType().equals(TypeOfRestaurant.KEBAB)).
                forEach(restaurant -> System.out.println(restaurant.getName()+" manger: "+restaurant.getManager().getName()));
        System.out.println("<<PIZZA>>");
        Database.getInstance().getRestaurants().stream().filter(restaurant -> restaurant.getType().equals(TypeOfRestaurant.PIZZA)).
                forEach(restaurant -> System.out.println(restaurant.getName()+" manger: "+restaurant.getManager().getName()));
        AppManager.expected();
        run();
    }

    public static void setDiscount() {
        String name = FirstMenu.getName();
        System.out.print("discount code: ");
        String discountCode = ScannerWrapper.nextLine();
        int discount = RestaurantManagerMenu.getInt("discount");
        Customer customer = (Customer) AppManager.getPersonWithName(Database.getInstance().getCustomers(), name);
        if(customer != null) {
            customer.addDiscountCode(discountCode, discount);
            AppManager.expected();
            run();
        }else {
            System.out.println("the customer not found");
            run();
        }
    }

    public static void deleteCustomer() {
        String name = FirstMenu.getName();
        Customer customer = (Customer) AppManager.getPersonWithName(Database.getInstance().getCustomers(), name);
        if(customer != null) {
            Database.getInstance().removeCustomer(customer);
            System.out.println("the customer removed");
            AppManager.expected();
            run();
        }else {
            System.out.println("there is no customer");
            run();
        }
    }

    public static void addRestaurantManager() {
        String name = FirstMenu.getName();
        String password = FirstMenu.getPass();
        RestaurantManager restaurantManager = new RestaurantManager(name, password);
        if(AppManager.isAvaialable(restaurantManager, Database.getInstance().getRestaurantManagers())) {
            System.out.println("this manager is avaialable");
            run();
        } else {
            Restaurant restaurant = getRestaurant(restaurantManager);
            restaurantManager.setRestaurant(restaurant);
            Database.getInstance().addRestaurantManger(restaurantManager);
            Database.getInstance().addRestaurant(restaurant);
            System.out.println("the manager added");
            AppManager.expected();
            run();
        }
    }

    private static Restaurant getRestaurant(RestaurantManager restaurantManager) {
        String name = FirstMenu.getName();
        TypeOfRestaurant type = getType();
        return new Restaurant(name, type, restaurantManager);
    }

    private static TypeOfRestaurant getType() {
        System.out.println("1.FAST FOOD");
        System.out.println("2.CAFE SHOP");
        System.out.println("3.BUFFET");
        System.out.println("4.KEBAB");
        System.out.println("5.PIZZA");
        switch (ScannerWrapper.nextLine()) {
            case "1":
                return TypeOfRestaurant.FASTFOOD;
            case "2":
                return TypeOfRestaurant.CAFESHOP;
            case "3":
                return TypeOfRestaurant.BUFFET;
            case "4":
                return TypeOfRestaurant.KEBAB;
            case "5":
                return TypeOfRestaurant.PIZZA;
            default:
                return getType();
        }
    }

    public static void deleteRestaurantManager() {
        String name = FirstMenu.getName();
        RestaurantManager restaurantManager = (RestaurantManager) AppManager.getPersonWithName(Database.getInstance().getRestaurantManagers(), name);
        if(restaurantManager != null) {
            Database.getInstance().removeRestaurantManager(restaurantManager);
            Database.getInstance().removeRestaurant(restaurantManager.getRestaurant());
            System.out.println("the manager removed");
            AppManager.expected();
            run();
        }else {
            System.out.println("there is no restaurant manager");
            run();
        }
    }

    public static void profile() {
        System.out.println("1.change pass");
        System.out.println("2.show info");
        System.out.println("3.back");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), SnapFoodMangerMenu::profile, SnapFoodMangerMenu::changePass,
                    SnapFoodMangerMenu::showInfo, SnapFoodMangerMenu::run);
        }catch (Exception e) {
            System.out.println("invalid command");
            profile();
        }
    }

    public static void changePass() {
        SnapFoodManager snapFoodManager = (SnapFoodManager) FirstMenu.loggedPerson;
        AppManager.changePass(snapFoodManager);
        AppManager.expected();
        profile();
    }

    public static void showInfo() {
        SnapFoodManager snapFoodManager = (SnapFoodManager) FirstMenu.loggedPerson;
        snapFoodManager.showInfo();
        AppManager.expected();
        profile();
    }

    public static void logOut() {
        System.out.println("you logged out");
        FirstMenu.run();
    }
}
