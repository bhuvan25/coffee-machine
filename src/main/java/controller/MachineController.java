package controller;

import model.Beverage;
import model.Ingredient;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MachineController {
    private BeverageController beverageController;
    private IngredientController ingredientController;
    int outletCount;
    ReentrantLock lock = new ReentrantLock();
    public MachineController() {
        ingredientController = new IngredientController();
        beverageController = new BeverageController(ingredientController);
        outletCount = 3;

    }

    public void serveBeverage(String beverageName) {
        Beverage beverage = beverageController.getBeverageByName(beverageName);
        synchronized (this) {
            if (beverageController.canBeServed(beverage)) {
                beverageController.serveBeverage(beverage);
                System.out.println(beverage.getBeverageName() + " served! Enjoy :)");
            } else {
                beverageController.giveReasonForNotServing(beverage);
            }
        }

    }

    public void fillIngredient(String ingredientName, int quantity) {
        Ingredient ingredient = ingredientController.getIngredientByName(ingredientName);
        ingredientController.fillIngredient(ingredient, quantity);
        System.out.println(ingredient.getIngredientName() + " filled.");
    }
}
