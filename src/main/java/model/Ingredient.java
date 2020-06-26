package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class Ingredient {
    String ingredientName;
    int currentQuantity;
    int maxQuantity;
}
