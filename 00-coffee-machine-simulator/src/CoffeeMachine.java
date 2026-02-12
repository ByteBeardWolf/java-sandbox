package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static Scanner scanner = new java.util.Scanner(System.in);

    private static int availableWater = 400;
    private static int availableMilk = 540;
    private static int availableCoffeeBeans = 120;
    private static int availableCups = 9;
    private static int collectedMoney = 550;
    private static int availableCoffeeGroundsSpace = 10;

    public record coffeeRecipe(int water, int milk, int coffeeBeans, int cost) {}
    private static final coffeeRecipe espresso = new coffeeRecipe(250, 0, 16, 4);
    private static final coffeeRecipe latte = new coffeeRecipe(350, 75, 20, 7);
    private static final coffeeRecipe cappuccino = new coffeeRecipe(200, 100, 12, 6);

    public static void printMachineState() {
        System.out.println("The coffee machine has:");
        System.out.println(availableWater + " ml of water");
        System.out.println(availableMilk + " ml of milk");
        System.out.println(availableCoffeeBeans + " g of coffee beans");
        System.out.println(availableCups + " disposable cups");
        System.out.println("$" + collectedMoney + " of money");
    }

    public static boolean hasResources(coffeeRecipe recipe) {
        if (availableWater < recipe.water) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (availableMilk < recipe.milk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (availableCoffeeBeans < recipe.coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (availableCups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }
        System.out.println("I have enough resources, making you a coffee!");
        return true;
    }

    public static void makeCoffee(coffeeRecipe recipe) {
        availableWater -= recipe.water;
        availableMilk -= recipe.milk;
        availableCoffeeBeans -= recipe.coffeeBeans;
        collectedMoney += recipe.cost;
        availableCups -= 1;
        availableCoffeeGroundsSpace -= 1;
    }

    public static void fillMachine() {
        System.out.println("Write how many ml of water you want to add:");
        availableWater += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        availableMilk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        availableCoffeeBeans += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        availableCups += scanner.nextInt();
    }

    public static void takeMoney() {
        System.out.println("I gave you $" + collectedMoney);
        collectedMoney = 0;
    }

    public static void cleanCoffeeGroundsSpace() {
        availableCoffeeGroundsSpace = 10;
        System.out.println("I have been cleaned!");
    }

    public static void main(String[] args) {
        while(true) {
            System.out.println("\nWrite action (buy, fill, take, clean, remaining, exit):");
            String action = scanner.next();
            switch (action) {
                case "buy" -> {
                    if (availableCoffeeGroundsSpace < 1) {
                        System.out.println("I need cleaning!");
                        continue;
                    }
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    switch (scanner.next()) {
                        case "1" -> {
                            if(hasResources(espresso)) makeCoffee(espresso);
                        }
                        case "2" -> {
                            if(hasResources(latte)) makeCoffee(latte);
                        }
                        case "3" -> {
                            if(hasResources(cappuccino)) makeCoffee(cappuccino);
                        }
                        case "back" -> {}
                        default -> System.out.println("Unknown coffee choice.");
                    }
                }
                case "fill" -> fillMachine();
                case "take" -> takeMoney();
                case "clean" -> cleanCoffeeGroundsSpace();
                case "remaining" -> printMachineState();
                case "exit" -> {return;}
                default -> System.out.println("Unknown action.");
            }
        }
    }
}