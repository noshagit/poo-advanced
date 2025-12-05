package projet;

import java.util.Random;

import projet.armor.Armor;
import projet.weapon.Weapon;

public class Champion {
    private String name;
    private int health;
    private Weapon weapon;
    private Armor armor;
    private int xp;
    private int level;
    private final Inventory inventory;
    private final Weapon oldWeapon;
    private final int baseHealth;

    public Champion(String name, int health, Weapon weapon, Armor armor) {
        this.name = name;
        this.health = health;
        this.baseHealth = health;
        this.weapon = weapon;
        this.oldWeapon = weapon;
        this.armor = armor;
        this.xp = 0;
        this.level = 1;
        this.inventory = new Inventory();
    }

    public Weapon getOldWeapon() {
        return oldWeapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getBaseHealth() {
        return baseHealth;
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

    public void takeDamage(Weapon weapon) {
        if (weapon.getName().equals("Gambling Blade")) {
            Random rand = new Random();
            int chance = rand.nextInt(150) - 25;
            this.health -= (chance - this.armor.getResistance());
        } else {
            if ((weapon.getDamages() - this.armor.getResistance()) > 2) {
                this.health -= (weapon.getDamages() - this.armor.getResistance());
            } else {
                this.health -= 2;
            }
        }
    }

    public int getMoveSpeed() {
        return this.weapon.getAttackSpeed() / (this.armor.getWeight() / 10);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void usePotion(Potion potion) {
        if (this.inventory.getPotions().contains(potion)) {
            if ("Health Potion".equals(potion.getName())) {
                this.health += 20;
            } else if ("Gambling Potion".equals(potion.getName())) {
                Random rand = new Random();
                int chance = rand.nextInt(100);
                if (chance < 25) {
                    this.health = 0;
                } else if (chance > 25 && chance < 40) {
                    this.weapon = new Weapon("Gambling Blade", 50, 1);
                } else {
                    this.health += 15;
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

    public void resetHealth() {
        this.health = this.baseHealth;
    }
}