package projet;

import projet.armor.Armor;
import projet.potions.Potion;
import projet.weapon.Weapon;

public class Player extends Champion {
    private int xp;
    private int level;
    private final Inventory inventory;

    public Player(String name, int health, Weapon weapon, Armor armor) {
        super(name, health, weapon, armor);
        this.xp = 0;
        this.level = 1;
        this.inventory = new Inventory();
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public void gainXp(int xpGained) {
        float actualXp = xpGained / Math.min(((float) level / 10), 1);
        this.xp += (int) actualXp;
        if (this.xp >= 100) {
            this.level += 1;
            this.xp = 0;
            System.out.println("You leveled up!");
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void usePotion(Potion potion) {
        if (this.inventory.getPotions().contains(potion)) {
            potion.use(this);
        }
    }

    public boolean hasPotion(String potionName) {
        for (Potion p : this.inventory.getPotions()) {
            if (p.getName().equals(potionName)) {
                return true;
            }
        }
        return false;
    }
}
