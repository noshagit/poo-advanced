package projet;

import java.util.Random;

import projet.armor.Armor;
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
            if ("Health Potion".equals(potion.getName())) {
                this.setHealth(this.getHealth() + 10);
            } else if ("Gambling Potion".equals(potion.getName())) {
                Random rand = new Random();
                int chance = rand.nextInt(100);
                if (chance < 25) {
                    this.setHealth(0);
                    System.out.println("Oh no! The gambling potion backfired and caused instant death!");
                } else if (chance > 25 && chance < 40) {
                    this.setWeapon(new Weapon("Gambling Blade", 50, 1));
                    System.out.println("Lucky you! The gambling potion transformed your weapon into the Gambling Blade!");
                } else {
                    this.setHealth(this.getHealth() + 15);
                    System.out.println("You gained 15 health from the gambling potion!");
                }
                this.inventory.removePotion(potion);
            }
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
