package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Builder
public class Beverage {
    private String beverageName;
    private Map<Ingredient,Integer> requirements;

}
