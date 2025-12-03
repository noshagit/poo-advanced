package projet;

import java.util.Random;

public class Champion {
    private String name;
    private int health;
    private Weapon weapon;
    private Armor armor;
    private int xp;
    private int level;
    private Inventory inventory;
    private Weapon oldWeapon;

    public Champion(String name, int health, Weapon weapon, Armor armor) {
        this.name = name;
        this.health = 100;
        this.weapon = weapon;
        this.oldWeapon = weapon;
        this.armor = armor;
        this.xp = 0;
        this.level = 0;
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

    public void gainXp(int xpGained) {
        this.xp += xpGained;
        if (this.xp >= 100) {
            this.level += 1;
            this.xp = this.xp - 100;
        }
    }

    public void takeDamage(Weapon weapon) {
        if (weapon.getName().equals("Gambling Blade")) {
            Random rand = new Random();
            int chance = rand.nextInt(150) - 25;
            this.health -= (chance - this.armor.getResistance());
        } else {
            this.health -= (weapon.getDamages() - this.armor.getResistance());
        }
    }

    public float getMoveSpeed() {
        return (float) this.weapon.getAttackSpeed() / (this.armor.getWeight() / 10);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void usePotion(Potion potion) {
        if (this.inventory.getPotions().contains(potion)) {
            if (potion.getName() == "Health Potion") {
                this.health += 20;
            } else if (potion.getName() == "Gambling Potion") {
                Random rand = new Random();
                int chance = rand.nextInt(100);
                if (chance < 25) {
                    this.health = 0;
                } else if (chance > 25 && chance < 40) {
                    this.weapon = new Weapon("Gambling Blade", 50, 0.1);
                } else {
                    this.health += 15;
                }
            }
            this.inventory.removePotion(potion);
        }
    }
}