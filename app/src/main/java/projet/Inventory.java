package projet;

import java.util.List;
import java.util.ArrayList;

public class Inventory {
    private final List<Potion> potions;

    public Inventory() {
        this.potions = new ArrayList<Potion>();
    }

    public void addPotion(Potion potion) {
        this.potions.add(potion);
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public void removePotion(Potion potion) {
        this.potions.remove(potion);
    }
}