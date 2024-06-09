package view;

import controller.AppManager;
import controller.EventHandler;
import model.Category;
import model.Database;
import model.Food;
import model.RestaurantManager;

public class RestaurantManagerMenu {

    public static void run() {
        System.out.println("1.show menu");
        System.out.println("2.add food");
        System.out.println("3.profile");
        System.out.println("4.logout");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), RestaurantManagerMenu::run, RestaurantManagerMenu::showMenu,
                    RestaurantManagerMenu::addFood, RestaurantManagerMenu::profile, RestaurantManagerMenu::loggOut);
        }catch (Exception e) {
            System.out.println("invalid command");
            run();
        }
    }

    public static void showMenu() {
        RestaurantManager restaurantManager = (RestaurantManager) FirstMenu.loggedPerson;
        System.out.println("<<STARTER>>");
        restaurantManager.getRestaurant().getMenu().stream().filter(food -> food.getCategory().equals(Category.STARTER)).
                forEach(food -> System.out.println(food.getName()+". price: "+food.getPrice()+" cost: "+food.getCost()));
        System.out.println("<<ENTREE>>");
        restaurantManager.getRestaurant().getMenu().stream().filter(food -> food.getCategory().equals(Category.ENTREE)).
                forEach(food -> System.out.println(food.getName()+". price: "+food.getPrice()+" cost: "+food.getCost()));
        System.out.println("<<DESSERT>>");
        restaurantManager.getRestaurant().getMenu().stream().filter(food -> food.getCategory().equals(Category.DESERT)).
                forEach(food -> System.out.println(food.getName()+". price: "+food.getPrice()+" cost: "+food.getCost()));
        AppManager.expected();
        run();
    }

    public static void addFood() {
        RestaurantManager restaurantManager = (RestaurantManager) FirstMenu.loggedPerson;
        String name = FirstMenu.getName();
        int price = getInt("price");
        int cost = getInt("cost");
        Category category = getCategory();
        Food food = new Food(name, price, cost, category);
        if(AppManager.isAvaialable(food, restaurantManager.getRestaurant().getMenu())){
            System.out.println("this food is aviable");
            run();
        }else {
            restaurantManager.getRestaurant().addFood(food);
            AppManager.expected();
            run();
        }
    }

    private static Category getCategory() {
        System.out.println("1.STARTER");
        System.out.println("2.ENTREE");
        System.out.println("3.DESSERT");
        switch (ScannerWrapper.nextLine()) {
            case "1":
                return Category.STARTER;
            case "2":
                return Category.ENTREE;
            case "3":
                return Category.DESERT;
            default:
                return getCategory();
        }
    }

    public static int getInt(String apply) {
        System.out.print(apply+": ");
        String input = ScannerWrapper.nextLine();
        try {
            return Integer.parseInt(input);
        }catch (Exception e) {
            System.out.println("invalid amount");
            return getInt(apply);
        }
    }

    public static void profile() {
        System.out.println("1.change pass");
        System.out.println("2.show info");
        System.out.println("3.delete acount");
        System.out.println("4.back");
        String input = ScannerWrapper.nextLine();
        try {
            EventHandler.eventHandle(Integer.parseInt(input), RestaurantManagerMenu::profile,
                    RestaurantManagerMenu::changePass, RestaurantManagerMenu::showInfo, RestaurantManagerMenu::deleteAcount,
                    RestaurantManagerMenu::run);
        }catch (Exception e) {
            System.out.println("invalid command");
            profile();
        }
    }

    public static void changePass() {
        AppManager.changePass(FirstMenu.loggedPerson);
        AppManager.expected();
        profile();
    }

    public static void showInfo() {
        RestaurantManager restaurantManager = (RestaurantManager) FirstMenu.loggedPerson;
        restaurantManager.showInfo();
        AppManager.expected();
        profile();
    }

    public static void deleteAcount() {
        RestaurantManager restaurantManager = (RestaurantManager) FirstMenu.loggedPerson;
        System.out.println("you delete your acount");
        Database.getInstance().removeRestaurantManager(restaurantManager);
        AppManager.expected();
        profile();
    }

    public static void loggOut() {
        System.out.println("you logged out");
        FirstMenu.run();
    }
}
