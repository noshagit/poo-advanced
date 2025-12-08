package projet;

/* IMPORTS */

import java.util.Scanner;

import projet.armor.Armor;
import projet.potions.Potion;
import projet.weapon.Weapon;

/**
 * The player class representing the user in the game.
 */
public class Player extends Champion {
    private int xp;
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

    public boolean hasNextInt() {
        return this.lifeSteal;
    }

    public boolean hasLifeSteal() {
        return lifeSteal;
    }

    public boolean hasChanceToStun() {
        return chancetostun;
    }

    public void setChanceToStun(boolean chancetostun) {
        this.chancetostun = chancetostun;
    }

    public void setLifeSteal(boolean lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public boolean chancetostun() {
        return chancetostun;
    }

    public void chancetostun(boolean chancetostun) {
        this.chancetostun = chancetostun;
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
        if (this.xp >= 100) {
            this.level += 1;
            this.xp = 0;
            System.out.println();
            System.out.println("You leveled up!");
            System.out.println();
            System.out.println("Would you like to : ");
            System.out.println("1. Increase your max health by 10 \n2. Heal 40 health points");
            System.out.print("> ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    this.maxHealth += 10;
                    this.setHealth(this.getHealth() + 10);
                    System.out.println("Your max health increased to " + this.maxHealth);
                    break;
                case 2:
                    this.setHealth(Math.min(this.getHealth() + 40, this.maxHealth));
                    System.out.println("You healed 40 health points. Current health: " + this.getHealth());
            }
            System.out.println();
        }
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
        boolean back = false;
        while (!back) {
            System.out.println("What would you like to see?");
            System.out.println("1. Potions");
            System.out.println("2. Weapons");
            System.out.println("3. Armors");
            System.out.println("4. Back");
            System.out.print("> ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: {
                    if (inventory.getPotions().isEmpty()) {
                        System.out.println();
                        System.out.println("You don't have any potions in your backpack");
                        System.out.println();
                    } else {
                        int healthCount = 0;
                        int gamblingCount = 0;
                        int criticalCount = 0;
                        int labyrinthMithySoupCount = 0;
                        int slimePuddingCount = 0;
                        int soulElixirCount = 0;
                        int boneSkinCount = 0;
                        int berserkCount = 0;

                        for (Potion p : inventory.getPotions()) {
                            String name = p.getName();

                            switch (name) {
                                case "Health":
                                    healthCount++;
                                    break;
                                case "Gambling":
                                    gamblingCount++;
                                    break;
                                case "Critical":
                                    criticalCount++;
                                    break;
                                case "Labyrinth Mithy Soup":
                                    labyrinthMithySoupCount++;
                                    break;
                                case "Slime Pudding":
                                    slimePuddingCount++;
                                    break;
                                case "Soul Elixir":
                                    soulElixirCount++;
                                    break;
                                case "Bone Skin":
                                    boneSkinCount++;
                                    break;
                                case "Berserk":
                                    berserkCount++;
                                    break;
                                default:
                                    break;
                            }
                        }
                        System.out.println();

                        int totalPotions = healthCount + gamblingCount + criticalCount + labyrinthMithySoupCount
                                + slimePuddingCount + soulElixirCount + boneSkinCount + berserkCount;

                        if (totalPotions == 0) {
                            System.out.println("  No potions in the inventory");
                        } else {
                            System.out.println("Potions:");
                            if (healthCount > 0) {
                                System.out.println("  Health : " + healthCount);
                            }
                            if (gamblingCount > 0) {
                                System.out.println("  Gambling : " + gamblingCount);
                            }
                            if (criticalCount > 0) {
                                System.out.println("  Critical Strike: " + criticalCount);
                            }
                            if (labyrinthMithySoupCount > 0) {
                                System.out.println("  Labyrinth Mithy Soup: " + labyrinthMithySoupCount);
                            }
                            if (slimePuddingCount > 0) {
                                System.out.println("  Slime Pudding: " + slimePuddingCount);
                            }
                            if (soulElixirCount > 0) {
                                System.out.println("  Soul Elixir: " + soulElixirCount);
                            }
                            if (boneSkinCount > 0) {
                                System.out.println("  Bone Skin: " + boneSkinCount);
                            }
                            if (berserkCount > 0) {
                                System.out.println("  Berserk: " + berserkCount);
                            }
                        }
                    }
                    System.out.println("Press Enter to return to the menu...");
                    scanner.nextLine();
                    break;
                }
                case 2: {
                    System.out.println();
                    if (inventory.getWeapons().isEmpty()) {
                        System.out.println("You don't have any weapons in your backpack");
                        System.out.println("Press Enter to return to the menu...");
                        scanner.nextLine();
                        break;
                    }

                    System.out.println("Weapons:");

                    while (true) {
                        System.out.println("Weapons in backpack:");
                        for (int i = 0; i < inventory.getWeapons().size(); i++) {
                            Weapon w = inventory.getWeapons().get(i);
                            System.out.println("  " + i + ": " + w.getName() + " (lvl " + w.getLevel() + ", xp "
                                    + w.getXp() + ")");
                        }
                        System.out.println(
                                "Current weapon: "
                                        + (this.getOldWeapon() != null ? this.getOldWeapon().getName() : "none"));
                        System.out.println(
                                "Enter the index of the weapon to swap with oldWeapon, or type 'back' to cancel:");
                        String input = scanner.nextLine().trim();

                        if ("back".equalsIgnoreCase(input)) {
                            break;
                        }

                        try {
                            int idx = Integer.parseInt(input);
                            if (idx < 0 || idx >= inventory.getWeapons().size()) {
                                System.out.println("Invalid index.");
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

                            System.out.println("Swapped " + selected.getName() + " into oldWeapon.");
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number or 'back'.");
                        }
                    }
                    System.out.println();
                    break;
                }
                case 3: {
                    System.out.println();
                    if (inventory.getArmors().isEmpty()) {
                        System.out.println("You don't have any armors in your backpack");
                        System.out.println("Press Enter to return to the menu...");
                        scanner.nextLine();
                        break;
                    }

                    System.out.println("Armors:");

                    while (true) {
                        System.out.println("Armors in backpack:");
                        for (int i = 0; i < inventory.getArmors().size(); i++) {
                            Armor a = inventory.getArmors().get(i);
                            System.out.println("  " + i + ": " + a.getName());
                        }
                        System.out.println(
                                "Current armor: " + (this.getArmor() != null ? this.getArmor().getName() : "none"));
                        System.out.println("Enter the index of the armor to equip, or type 'back' to cancel:");
                        String input = scanner.nextLine().trim();

                        if ("back".equalsIgnoreCase(input)) {
                            break;
                        }

                        try {
                            int idx = Integer.parseInt(input);
                            if (idx < 0 || idx >= inventory.getArmors().size()) {
                                System.out.println("Invalid index.");
                                continue;
                            }

                            Armor selected = inventory.getArmors().remove(idx);
                            Armor current = this.getArmor();

                            if (current != null) {
                                inventory.getArmors().add(current);
                            }
                            this.setArmor(selected);

                            System.out.println("Equipped " + selected.getName());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number or 'back'.");
                        }
                    }
                    System.out.println();
                    break;
                }
                case 4:
                    back = true;
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    System.out.println();
            }
        }
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
