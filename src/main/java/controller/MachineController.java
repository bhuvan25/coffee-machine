package controller;

import model.Beverage;
import model.Ingredient;

public class MachineController {
    private BeverageController beverageController;
    private IngredientController ingredientController;

    public MachineController() {
        ingredientController = new IngredientController();
        beverageController = new BeverageController(ingredientController);

    }

    public void serveBeverage(String beverageName) throws InterruptedException {
        Beverage beverage = beverageController.getBeverageByName(beverageName);

        if (beverageController.serveBeverage(beverage)) {
            System.out.println(beverage.getBeverageName() + " served! Enjoy :)");
        } else {
            beverageController.giveReasonForNotServing(beverage);
        }

    }

    public void fillIngredient(String ingredientName, int quantity) {
        Ingredient ingredient = ingredientController.getIngredientByName(ingredientName);
        ingredientController.fillIngredient(ingredient, quantity);
        System.out.println(ingredient.getIngredientName() + " filled.");
    }
}
