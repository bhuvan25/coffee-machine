package controller;

import model.Beverage;
import model.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class BeverageController {
    ReentrantLock lock = new ReentrantLock();
    private IngredientController ingredientController1;
    private List<Beverage> beverages = new ArrayList<Beverage>();

    public BeverageController(IngredientController ingredientController) {
        ingredientController1 = ingredientController;
        Ingredient sugar = ingredientController1.getIngredientByName("sugar_syrup");
        Ingredient water = ingredientController1.getIngredientByName("hot_water");
        Ingredient milk = ingredientController1.getIngredientByName("hot_milk");
        Ingredient tea_leaves_syrup = ingredientController1.getIngredientByName("tea_leaves_syrup");
        Ingredient ginger_syrup = ingredientController1.getIngredientByName("ginger_syrup");
        Ingredient green_mixture = ingredientController1.getIngredientByName("green_mixture");

        Map<Ingredient, Integer> beverage1Requirement = new HashMap<Ingredient, Integer>();
        beverage1Requirement.put(water, 200);
        beverage1Requirement.put(milk, 100);
        beverage1Requirement.put(tea_leaves_syrup, 30);
        beverage1Requirement.put(ginger_syrup, 10);
        beverage1Requirement.put(sugar, 10);

        Map<Ingredient, Integer> beverage2Requirement = new HashMap<Ingredient, Integer>();
        beverage2Requirement.put(water, 100);
        beverage2Requirement.put(ginger_syrup, 30);
        beverage2Requirement.put(milk, 400);
        beverage2Requirement.put(sugar, 50);
        beverage2Requirement.put(tea_leaves_syrup, 30);

        Map<Ingredient, Integer> beverage3Requirement = new HashMap<Ingredient, Integer>();
        beverage3Requirement.put(water, 300);
        beverage3Requirement.put(ginger_syrup, 30);
        beverage3Requirement.put(sugar, 50);
        beverage3Requirement.put(tea_leaves_syrup, 30);

        Map<Ingredient, Integer> beverage4Requirement = new HashMap<Ingredient, Integer>();
        beverage4Requirement.put(water, 100);
        beverage4Requirement.put(ginger_syrup, 30);
        beverage4Requirement.put(sugar, 50);
        beverage4Requirement.put(green_mixture, 30);

        Beverage beverage1 = Beverage.builder().beverageName("hot_tea").requirements(beverage1Requirement).build();
        Beverage beverage2 = Beverage.builder().beverageName("hot_coffee").requirements(beverage2Requirement).build();
        Beverage beverage3 = Beverage.builder().beverageName("black_tea").requirements(beverage3Requirement).build();
        Beverage beverage4 = Beverage.builder().beverageName("green_tea").requirements(beverage4Requirement).build();

        beverages.add(beverage1);
        beverages.add(beverage2);
        beverages.add(beverage3);
        beverages.add(beverage4);

    }

    public Beverage getBeverageByName(String beverageName) {
        Beverage dummyBeverage = Beverage.builder().build();
        for (Beverage beverage : beverages) {
            if (beverage.getBeverageName().equalsIgnoreCase(beverageName)) {
                return beverage;
            }
        }
        return dummyBeverage;
    }

    private boolean canBeServed(Beverage beverage) {

        for (Map.Entry<Ingredient, Integer> requirement : beverage.getRequirements().entrySet()) {
            if (requirement.getValue() > requirement.getKey().getCurrentQuantity()) {
                return false;
            }
        }
        return true;
    }

    public boolean serveBeverage(Beverage beverage) {

        lock.lock();
        for (Map.Entry<Ingredient, Integer> requirement : beverage.getRequirements().entrySet()) {
            while (requirement.getKey().getLockStatus()) {
                continue;
            }
            requirement.getKey().getLock().lock();
        }
        lock.unlock();

        if (!canBeServed(beverage)) {
            unlockIngredients(beverage);
            return false;
        }
        for (Map.Entry<Ingredient, Integer> requirement : beverage.getRequirements().entrySet()) {

            ingredientController1.decreaseIngredient(requirement.getKey(), requirement.getValue());
        }
        unlockIngredients(beverage);
        return true;
    }

    private void unlockIngredients(Beverage beverage) {
        for (Map.Entry<Ingredient, Integer> requirement : beverage.getRequirements().entrySet()) {
            requirement.getKey().getLock().unlock();
        }
    }

    public void giveReasonForNotServing(Beverage beverage) {
        for (Map.Entry<Ingredient, Integer> requirement : beverage.getRequirements().entrySet()) {
            if (requirement.getValue() > requirement.getKey().getCurrentQuantity()) {
                System.out.println(requirement.getKey().getIngredientName() + " is not sufficient " + beverage.getBeverageName());
            }
        }
    }
}
