package projet.model;

/* IMPORTS */

import java.util.List;
import java.util.Scanner;

import projet.controller.Inventory;
import projet.model.armors.Armor;
import projet.model.potions.Potion;
import projet.model.weapons.Weapon;
import projet.view.Views;

/**
 * The player class representing the user in the game.
 */
public class Player extends Champion {
    private int xp;
    private int totalXp;
    private int level;
    private final Inventory inventory;
    private int maxHealth;
    private Weapon oldWeapon;
    private int ExtraArmor = 0;
    private int extraDamage = 0;
    private int extraDamageMightySoup = 0;
    private boolean chancetostun = false;
    private boolean lifeSteal = false;
    private int golemExtraArmor = 0;

    public Player(String name, int health, Weapon weapon, Armor armor) {
        super(name, health, weapon, armor);
        this.oldWeapon = weapon;
        this.maxHealth = health;
        this.xp = 0;
        this.level = 1;
        this.inventory = new Inventory();
    }

    public void addHealth(int health) {
        this.setHealth(Math.min(this.getHealth() + health, this.maxHealth));
    }

    public boolean hasLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(boolean lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public boolean hasChanceToStun() {
        return chancetostun;
    }

    public void setChanceToStun(boolean chancetostun) {
        this.chancetostun = chancetostun;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getExtraArmor() {
        return this.ExtraArmor;
    }

    public void setExtraArmor(int extraArmor) {
        this.ExtraArmor = extraArmor;
    }

    public int getExtraDamage() {
        return this.extraDamage;
    }

    public void setExtraDamage(int extraDamage) {
        this.extraDamage = extraDamage;
    }

    public int extraDamageMightySoup() {
        return this.extraDamageMightySoup;
    }

    public void setExtraArmorGolem(int extraArmorGolem) {
        this.golemExtraArmor = extraArmorGolem;
    }

    public int getExtraArmorGolem() {
        return this.golemExtraArmor;
    }

    public void extraDamageMightySoup(int extraDamage) {
        this.extraDamageMightySoup = extraDamage;
    }

    /**
     * Increases the player's experience points and handles leveling up.
     * 
     * @param xpGained The amount of experience points gained.
     * @param scanner  The scanner to read user input.
     */
    public void gainXp(int xpGained, Scanner scanner) {
        float actualXp = xpGained / Math.min(((float) level / 10), 1);
        this.xp += (int) actualXp;
        this.totalXp += (int) actualXp;

        if (this.xp >= 100) {
            levelUp(scanner);
        }
    }

    /**
     * Levels up the player and asks them to choose an upgrade.
     * 
     * @param scanner The scanner to read user input.
     */
    public void levelUp(Scanner scanner) {
        this.level += 1;
        this.xp = 0;
        Views.println("\nYou leveled up!\n"
                + "\nWould you like to :"
                + "\n 1. Increase your max health by 10"
                + "\n 2. Heal 40 health points\n");
        Views.print("> ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                this.maxHealth += 10;
                this.setHealth(this.getHealth() + 10);
                Views.println("Your max health increased to " + this.maxHealth);
                break;
            case 2:
                this.setHealth(Math.min(this.getHealth() + 40, this.maxHealth));
                Views.println("You healed 40 health points. Current health: " + this.getHealth());
        }
        Views.println();
    }

    public Weapon getOldWeapon() {
        return oldWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Checks and displays the contents of the player's inventory with input
     * options.
     */
    public void checkInventory(Scanner scanner) {
        while (true) {
            Views.println("What would you like to see?"
                    + "\n 1. Potions"
                    + "\n 2. Weapons"
                    + "\n 3. Armors"
                    + "\n 4. Back");
            Views.print("> ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                Views.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: {
                    checkPotions(scanner);
                    break;
                }
                case 2: {
                    checkWeapons(scanner);
                    break;
                }
                case 3: {
                    checkArmors(scanner);
                    break;
                }
                case 4:
                    Views.println();
                    return;
                default:
                    Views.println("Invalid choice.\n");
            }
        }
    }

    /**
     * Checks and displays the armors in the player's inventory.
     * 
     * @param scanner The scanner to read user input.
     */
    public void checkArmors(Scanner scanner) {
        Views.println();
        List<Armor> armors = inventory.getArmors();

        if (armors.isEmpty()) {
            Views.println("You don't have any armors in your backpack"
                    + "\nPress Enter to return to the menu...");
            scanner.nextLine();
            return;
        }

        while (true) {
            Views.println("Armors in backpack:");
            for (int i = 0; i < armors.size(); i++) {
                Armor a = armors.get(i);
                Views.println("  " + i + ": " + a.getName() + " (resistance " + a.getResistance() + ", weight "
                        + a.getWeight() + ")");
            }
            Views.println(
                    "Current armor: "
                            + (this.getArmor() != null ? this.getArmor().getName() : "none")
                            + "\nEnter the index of the armor to equip, or type 'back' to cancel:");
            String input = scanner.nextLine().trim();

            if ("back".equalsIgnoreCase(input)) {
                break;
            }

            try {
                int idx = Integer.parseInt(input);
                if (idx < 0 || idx >= inventory.getArmors().size()) {
                    Views.println("Invalid index.");
                    continue;
                }

                Armor selected = inventory.getArmors().remove(idx);
                Armor current = this.getArmor();

                if (current != null) {
                    inventory.getArmors().add(current);
                }
                this.setArmor(selected);

                Views.println("Equipped " + selected.getName());
                break;
            } catch (NumberFormatException e) {
                Views.println("Please enter a valid number or 'back'.");
            }
        }
        Views.println();
    }

    /**
     * Checks and displays the weapons in the player's inventory.
     * 
     * @param scanner The scanner to read user input.
     */
    public void checkWeapons(Scanner scanner) {
        Views.println();
        List<Weapon> weapons = inventory.getWeapons();

        if (weapons.isEmpty()) {
            Views.println("You don't have any weapons in your backpack"
                    + "\nPress Enter to return to the menu...");
            scanner.nextLine();
            return;
        }

        while (true) {
            Views.println("Weapons in backpack:");
            for (int i = 0; i < weapons.size(); i++) {
                Weapon w = weapons.get(i);
                Views.println("  " + i + ": " + w.getName() + " (lvl " + w.getLevel() + ", xp "
                        + w.getXp() + ")");
            }
            Views.println(
                    "Current weapon: "
                            + (this.getOldWeapon() != null ? this.getOldWeapon().getName() : "none")
                            + "\nEnter the index of the weapon to swap with the current weapon, or type 'back' to cancel:");
            String input = scanner.nextLine().trim();

            if ("back".equalsIgnoreCase(input)) {
                break;
            }

            try {
                int idx = Integer.parseInt(input);
                if (idx < 0 || idx >= inventory.getWeapons().size()) {
                    Views.println("Invalid index.");
                    continue;
                }

                Weapon selected = inventory.getWeapons().get(idx);
                Weapon currentOld = this.getOldWeapon();

                if (currentOld != null) {
                    inventory.getWeapons().set(idx, currentOld);
                } else {
                    inventory.getWeapons().remove(idx);
                }

                this.oldWeapon = selected;

                Views.println("Swapped " + selected.getName() + " into oldWeapon.");
                break;
            } catch (NumberFormatException e) {
                Views.println("Please enter a valid number or 'back'.");
            }
        }
        Views.println();
    }

    /**
     * Counts potions in the inventory by name.
     *
     * @return A map of potion names to their counts.
     */
    public java.util.Map<String, Integer> countPotions() {
        java.util.Map<String, Integer> potionCounts = new java.util.HashMap<>();
        for (Potion p : inventory.getPotions()) {
            int count = potionCounts.getOrDefault(p.getName(), 0) + 1;
            potionCounts.put(p.getName(), count);
        }
        return potionCounts;
    }

    /**
     * Checks and displays the potions in the player's inventory.
     *
     * @param scanner The scanner to read user input.
     */
    public void checkPotions(Scanner scanner) {
        if (inventory.getPotions().isEmpty()) {
            Views.println("\nYou don't have any potions in your backpack\n");
        } else {
            java.util.Map<String, Integer> potionCounts = countPotions();
            Views.println("\nPotions:");
            potionCounts.forEach((name, count) -> Views.println("  " + name + ": " + count));
        }
        Views.println("Press Enter to return to the menu...");
        scanner.nextLine();
    }

    /**
     * Uses a potion from the inventory.
     *
     * @param potion The potion to use.
     */
    public void usePotion(Potion potion) {
        if (this.inventory.getPotions().contains(potion)) {
            potion.use(this);
        }
    }

    /**
     * Checks if the player has a specific potion in their inventory.
     *
     * @param potionName The name of the potion to check for.
     * @return True if the potion is found, false otherwise.
     */
    public boolean hasPotion(String potionName) {
        for (Potion p : this.inventory.getPotions()) {
            if (p.getName().equals(potionName)) {
                return true;
            }
        }
        return false;
    }
}
