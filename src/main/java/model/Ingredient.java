package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
@Builder
public class Ingredient {
    String ingredientName;
    int currentQuantity;
    int maxQuantity;
    ReentrantLock lock;

    public boolean getLockStatus() {
        return lock.isLocked();
    }

}
