package projet;

/* IMPORTS */
import java.util.Random;
import java.util.Scanner;

import projet.enemies.*;
import projet.potions.Potion;
import projet.weapons.Weapon;

/**
 * The class responsible for handling fights between champions.
 */
public class Fight {
    /**
     * Executes an attack sequence between two champions.
     * 
     * @param player The champion who is attacking. ( The player )
     * @param enemy  The champion who is defending. ( The monster )
     */
    public static void attack(Player player, Enemy enemy) {
        Weapon playerWeapon = player.getWeapon();
        Weapon enemyWeapon = enemy.getWeapon();

        playerWeapon.addDamages(player.getExtraDamage());
        playerWeapon.addDamages(player.extraDamageMightySoup());
        enemyWeapon.subDamages(player.getExtraArmorGolem());

        Random rand = new Random();
        boolean isStunned = rand.nextInt(100) < 25;

        if (player.getMoveSpeed() < enemy.getMoveSpeed()) {
            player.takeDamage(enemyWeapon);
            critDmg(enemy, player, rand);

            enemy.takeDamage(playerWeapon);
            if (player.hasLifeSteal() && !isStunned) {
                player.addHealth(playerWeapon.getDamages() / 4);
            }
            if (critDmg(player, enemy, rand) && !isStunned) {
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
            }

        } else { // player strikes first
            enemy.takeDamage(playerWeapon);
            if (player.hasLifeSteal() && !isStunned) {
                player.addHealth(playerWeapon.getDamages() / 4);
            }
            if (critDmg(player, enemy, rand) && !isStunned) {
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
            }

            player.takeDamage(enemyWeapon);
            critDmg(enemy, player, rand);
        }
        playerWeapon.gainXp();
    }

    /**
     * Determines if an attack is a critical hit and applies damage accordingly.
     * 
     * @param attacker The champion who is attacking.
     * @param defender The champion who is defending.
     * @param rand     The random number generator.
     * @return true if the attack was a critical hit, false otherwise.
     */
    private static boolean critDmg(Champion attacker, Champion defender, Random rand) {
        if ((rand.nextInt(100) + attacker.getExtraCrit()) > 75) {
            System.out.println(attacker.getName() + " lands a critical strike!");
            defender.takeDamage(attacker.getWeapon());
            return true;
        }
        return false;
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
                return true;
            case "2":
                usePotion(player, scanner);
                return false;
            case "3":
                System.out.println("============================\n"
                        + player.getName() + " - Max Health: " + player.getMaxHealth() + " - Health: "
                        + player.getHealth() + " | Weapon: "
                        + player.getWeapon().getName() + " | Armor: " + player.getArmor().getName() + " | XP: "
                        + player.getXp() + " | Level: " + player.getLevel()

                        + "\n" + enemy.getName() + " - Health: " + enemy.getHealth() + " | Weapon: "
                        + enemy.getWeapon().getName() + " | Armor: " + enemy.getArmor().getName()
                        + "\n\n============================\n");
                return false;
            default:
                System.out.println("Invalid action, try again.");
                return false;
        }
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

        if (potions == null || potions.isEmpty()) {
            System.out.println("No potions available"
                    + "\n============================\n");
            return;
        }

        java.util.Map<String, Integer> potionCounts = player.countPotions();

        System.out.println("\nPotions:");
        potionCounts.forEach((name, count) -> System.out.println("  " + name + ": " + count));

        System.out.println("Choose a potion by name or index, or enter 'b' to go back:");
        System.out.print("> ");
        System.out.println();

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

    /**
     * randEnemy selects a random enemy based on the player's level.
     * 
     * @param player  The player whose level is considered.
     * @param enemies The array of possible enemies.
     * @return A randomly selected enemy.
     */
    public static Enemy randEnemy(Player player, Enemy[] enemies) {
        Random rand = new Random();
        int lvl = player.getLevel();

        // filter enemies that match the player's level range
        java.util.List<Enemy> validEnemies = new java.util.ArrayList<>();
        for (Enemy enemy : enemies) {
            int[] range = enemy.getLevelRange();
            if (lvl >= range[0] && lvl < range[1]) {
                validEnemies.add(enemy);
            }
        }

        if (validEnemies.isEmpty()) {
            return new Minotaur();
        }

        int randomIndex = rand.nextInt(validEnemies.size());
        return validEnemies.get(randomIndex);
    }
}
