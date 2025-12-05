package projet;

import projet.armor.Armor;
import projet.potions.Potion;
import projet.weapon.Weapon;
import java.util.Scanner;

public class Player extends Champion {
    private int xp;
    private int level;
    private final Inventory inventory;
    private int maxHealth;

    public Player(String name, int health, Weapon weapon, Armor armor) {
        super(name, health, weapon, armor);
        this.maxHealth = health;
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public void gainXp(int xpGained) {
        float actualXp = xpGained / Math.min(((float) level / 10), 1);
        this.xp += (int) actualXp;
        if (this.xp >= 100) {
            this.level += 1;
            this.xp = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.println("You leveled up!");
            System.out.println("1. Increase your max health by 10 \n2. Heal 40 health points.\nWould you like to : ");
            int choice = scanner.nextInt();

            switch (choice){
                case 1 :
                    this.maxHealth += 10;
                    this.setHealth(this.getHealth() + 10);
                    System.out.println("Your max health increased to " + this.maxHealth);
                    break;
                case 2 :
                    this.setHealth(Math.min(this.getHealth() + 40, this.maxHealth));
                    System.out.println("You healed 40 health points. Current health: " + this.getHealth());
            }
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
