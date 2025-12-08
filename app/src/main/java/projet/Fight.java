package projet;

/* TODO : Critical strike chances implementation */

/* IMPORTS */
import java.util.Random;
import java.util.Scanner;

import projet.enemies.*;
import projet.potions.Potion;
import projet.weapon.Weapon;

/**
 * The class responsible for handling fights between champions.
 */
public class Fight {
    /**
     * Executes an attack sequence between two champions.
     * 
     * @param attacker The champion who is attacking. ( The player )
     * @param defender The champion who is defending. ( The monster )
     */
    public static void attack(Player attacker, Enemy defender) {
        Weapon attackerWeapon = attacker.getWeapon();
        Weapon defenderWeapon = defender.getWeapon();

        attackerWeapon.addDamages(attacker.getExtraDamage());

        Random rand = new Random();

        if (attacker.getMoveSpeed() < defender.getMoveSpeed()) {
            attacker.takeDamage(defenderWeapon);
            if ((rand.nextInt(100) + defender.getExtraCrit()) > 75) {
                System.out.println(defender.getName() + " lands a critical strike!");
                attacker.takeDamage(defenderWeapon);
            }

            defender.takeDamage(attackerWeapon);
            if ((rand.nextInt(100) + attacker.getExtraCrit()) > 75) {
                System.out.println(attacker.getName() + " lands a critical strike!");
                defender.takeDamage(attackerWeapon);
            }

        } else {
            defender.takeDamage(attackerWeapon);
            if ((rand.nextInt(100) + attacker.getExtraCrit()) > 75) {
                System.out.println(attacker.getName() + " lands a critical strike!");
                defender.takeDamage(attackerWeapon);
            }

            attacker.takeDamage(defenderWeapon);
            if ((rand.nextInt(100) + defender.getExtraCrit()) > 75) {
                System.out.println(defender.getName() + " lands a critical strike!");
                attacker.takeDamage(defenderWeapon);
            }
        }
        attackerWeapon.gainXp();
    }

    /**
     * chooseAction allows the player to choose an action during a fight.
     * 
     * @param player  The player who is fighting.
     * @param enemy   The enemy being fought.
     * @param scanner The scanner for user input.
     * @return true if the player chose to fight, false otherwise.
     */
    public static boolean chooseAction(Player player, Enemy enemy, Scanner scanner) {
        System.out.println("1. Attack \n2. Use Potion \n3. Display statistics \nChoose an action: ");
        System.out.print("> ");

        String action = scanner.nextLine().trim();
        System.out.println();

        switch (action) {
            case "1":
                attack(player, enemy);
                break;
            case "2":
                usePotion(player, scanner);
                return false;
            case "3":
                System.out.println("============================");
                System.out.println();
                System.out.println(player.getName() + " - Max Health: " + player.getMaxHealth() + " - Health: "
                        + player.getHealth() + " | Weapon: "
                        + player.getWeapon().getName() + " | Armor: " + player.getArmor().getName() + " | XP: "
                        + player.getXp() + " | Level: " + player.getLevel());
                System.out.println(enemy.getName() + " - Health: " + enemy.getHealth() + " | Weapon: "
                        + enemy.getWeapon().getName() + " | Armor: " + enemy.getArmor().getName());
                System.out.println();
                System.out.println("============================");
                System.out.println();
                return false;
            default:
                System.out.println("Invalid action, try again.");
                return false;
        }
        return true;
    }

    /**
     * usePotion allows the player to use a potion from their inventory.
     * 
     * @param player  The player using the potion.
     * @param scanner The scanner to read user input.
     */
    public static void usePotion(Player player, Scanner scanner) {
        System.out.println("\n============================\n"
                + "Your pockets: ");
        java.util.List<Potion> potions = player.getInventory().getPotions();
        int healthCount = 0;
        int gamblingCount = 0;
        if (potions == null || potions.isEmpty()) {

            System.out.println("No potions available"
                    + "\n============================\n");
            return;
        } else {
            System.out.println("\n============================\n");
            for (int i = 0; i < potions.size(); i++) {
                Potion p = potions.get(i);
                if ("Health Potion".equals(p.getName())) {
                    healthCount++;
                } else if ("Gambling Potion".equals(p.getName())) {
                    gamblingCount++;
                }
            }
            System.out.println("Total Health Potion: " + healthCount
                    + " | Total Gambling Potion: " + gamblingCount
                    + "\nChoose a potion by name or index, or enter 'b' to go back:");
            System.out.print("> ");

            while (true) {
                String potionChoice = scanner.nextLine().trim();
                if (potionChoice.equalsIgnoreCase("b") || potionChoice.equalsIgnoreCase("back")) {
                    return;
                }

                Potion selectedPotion = null;
                for (Potion p : potions) {
                    if (p.getName().equalsIgnoreCase(potionChoice)) {
                        selectedPotion = p;
                        break;
                    }
                }

                if (selectedPotion == null) {
                    try {
                        int index = Integer.parseInt(potionChoice);
                        if (index >= 1 && index <= potions.size()) {
                            selectedPotion = potions.get(index - 1);
                        } else {
                            System.out.println("Invalid index. Try again or enter 'b' to go back:");
                            System.out.print("> ");
                            System.out.println();
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid potion name. Try again or enter 'b' to go back:");
                        continue;
                    }
                }

                player.usePotion(selectedPotion);
                player.getInventory().removePotion(selectedPotion);

                System.out.println("\n============================\n");

                break;
            }
        }
    }

    /**
     * randEnemy selects a random enemy based on the player's level.
     * 
     * @param player  The player whose level is considered.
     * @param enemies The array of possible enemies.
     * @param rand    The random number generator.
     * @return A randomly selected enemy.
     */
    public static Enemy randEnemy(Player player) {
        Random rand = new Random();
        int lvl = player.getLevel();
        int randomIndex;

        if (lvl < 2) {
            return new Slime();
        } else if (lvl >= 2 && lvl < 5) {
            Enemy[] possibleEnemies = { new Goblin(), new Slime() };
            randomIndex = rand.nextInt(possibleEnemies.length);
            return possibleEnemies[randomIndex];
        } else if (lvl >= 5 && lvl < 9) {
            Enemy[] possibleEnemies = { new Goblin(), new Skeleton(), new Slime() };
            randomIndex = rand.nextInt(possibleEnemies.length);
            return possibleEnemies[randomIndex];
        } else if (lvl >= 9 && lvl < 15) {
            Enemy[] possibleEnemies = { new Goblin(), new Orc(), new Skeleton() };
            randomIndex = rand.nextInt(possibleEnemies.length);
            return possibleEnemies[randomIndex];
        } else if (lvl >= 15 && lvl < 20) {
            Enemy[] possibleEnemies = { new Reaper(), new Orc(), new Skeleton() };
            randomIndex = rand.nextInt(possibleEnemies.length);
            return possibleEnemies[randomIndex];
        } else if (lvl >= 20 && lvl < 30) {
            Enemy[] possibleEnemies = { new Reaper(), new Orc(), new Troll() };
            randomIndex = rand.nextInt(possibleEnemies.length);
            return possibleEnemies[randomIndex];
        } else if (lvl >= 30 && lvl < 50) {
            Enemy[] possibleEnemies = { new Troll(), new StoneGolem(), new Minotaur() };
            randomIndex = rand.nextInt(possibleEnemies.length);
            return possibleEnemies[randomIndex];
        } else {
            return new Minotaur();
        }
    }
}
