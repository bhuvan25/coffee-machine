package controller;

import model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class IngredientController {
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    public IngredientController() {
        ingredients.add(Ingredient.builder().ingredientName("hot_water").currentQuantity(500).maxQuantity(1000).lock(new ReentrantLock()).build());
        ingredients.add(Ingredient.builder().ingredientName("hot_milk").currentQuantity(500).maxQuantity(1000).lock(new ReentrantLock()).build());
        ingredients.add(Ingredient.builder().ingredientName("sugar_syrup").currentQuantity(100).maxQuantity(1000).lock(new ReentrantLock()).build());
        ingredients.add(Ingredient.builder().ingredientName("tea_leaves_syrup").currentQuantity(100).maxQuantity(1000).lock(new ReentrantLock()).build());
        ingredients.add(Ingredient.builder().ingredientName("ginger_syrup").currentQuantity(100).maxQuantity(1000).lock(new ReentrantLock()).build());
        ingredients.add(Ingredient.builder().ingredientName("green_mixture").currentQuantity(0).maxQuantity(1000).lock(new ReentrantLock()).build());
    }

    public Ingredient getIngredientByName(String ingredientName) {

        for (Ingredient ingredient1 : ingredients) {
            if (ingredient1.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return ingredient1;
            }
        }
        return Ingredient.builder().build();
    }

    /***
     * to fill the ingredient in the machine when the option is selected for that
     * @param ingredient which ingredient we want to fill
     * @param quantity the quantuty of that ingredient we want to fill
     */
    public void fillIngredient(Ingredient ingredient, int quantity) {
        if (ingredient.getCurrentQuantity() + quantity > ingredient.getMaxQuantity()) {
            ingredient.setCurrentQuantity(ingredient.getMaxQuantity());
        } else {
            ingredient.setCurrentQuantity(ingredient.getCurrentQuantity() + quantity);
        }
    }

    /***
     * to decrease the quantity of the particular ingredient
     * @param ingredient the ingredient name
     * @param quantity the amount by which it will decrement
     */
    public void decreaseIngredient(Ingredient ingredient, int quantity) {
        if (ingredient.getCurrentQuantity() - quantity >= 0) {
            ingredient.setCurrentQuantity(ingredient.getCurrentQuantity() - quantity);
        } else {
            System.out.println(ingredient.getIngredientName() + " quantity is already less than " + quantity);
        }
    }
}
