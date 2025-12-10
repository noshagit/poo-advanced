package projet;

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
        int isStunned = rand.nextInt(100);
        if (isStunned >= 25) {
            if (player.getMoveSpeed() < enemy.getMoveSpeed()) {
                player.takeDamage(enemyWeapon);
                if ((rand.nextInt(100) + enemy.getExtraCrit()) > 75) {
                    System.out.println(enemy.getName() + " lands a critical strike!");
                    player.takeDamage(enemyWeapon);
                }

                enemy.takeDamage(playerWeapon);
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
                if ((rand.nextInt(100) + player.getExtraCrit()) > 75) {
                    System.out.println(player.getName() + " lands a critical strike!");
                    enemy.takeDamage(playerWeapon);
                    if (player.hasLifeSteal()) {
                        player.addHealth(playerWeapon.getDamages() / 4);
                    }
                }

            } else {
                enemy.takeDamage(playerWeapon);
                if (player.hasLifeSteal()) {
                    player.addHealth(playerWeapon.getDamages() / 4);
                }
                if ((rand.nextInt(100) + player.getExtraCrit()) > 75) {
                    System.out.println(player.getName() + " lands a critical strike!");
                    enemy.takeDamage(playerWeapon);
                    if (player.hasLifeSteal()) {
                        player.addHealth(playerWeapon.getDamages() / 4);
                    }
                }

                player.takeDamage(enemyWeapon);
                if ((rand.nextInt(100) + enemy.getExtraCrit()) > 75) {
                    System.out.println(enemy.getName() + " lands a critical strike!");
                    player.takeDamage(enemyWeapon);
                }
            }
        } else {
            enemy.takeDamage(playerWeapon);
            if ((rand.nextInt(100) + player.getExtraCrit()) > 75) {
                System.out.println(player.getName() + " lands a critical strike!");
                enemy.takeDamage(playerWeapon);
            }

            player.takeDamage(enemyWeapon);
            if ((rand.nextInt(100) + enemy.getExtraCrit()) > 75) {
                System.out.println(enemy.getName() + " lands a critical strike!");
                player.takeDamage(enemyWeapon);
            }
        }

        playerWeapon.gainXp();
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
        int healthCount = 0;
        int gamblingCount = 0;
        if (potions == null || potions.isEmpty()) {

            System.out.println("No potions available"
                    + "\n============================\n");
            return;
        } else {
            System.out.println("\n============================\n\n");
            int criticalCount = 0;
            int labyrinthMithySoupCount = 0;
            int slimePuddingCount = 0;
            int soulElixirCount = 0;
            int boneSkinCount = 0;
            int berserkCount = 0;
            int stoneGolemCount = 0;

            for (int i = 0; i < potions.size(); i++) {
                Potion p = potions.get(i);
                if (p == null || p.getName() == null)
                    continue;
                String name = p.getName().trim();

                switch (name.toLowerCase()) {
                    case "health":
                        healthCount++;
                        break;
                    case "gambling":
                        gamblingCount++;
                        break;
                    case "critical":
                        criticalCount++;
                        break;
                    case "labyrinth mithy soup":
                    case "labyrinthe mighty soup":
                    case "labyrinthemightysoup":
                        labyrinthMithySoupCount++;
                        break;
                    case "slime pudding":
                        slimePuddingCount++;
                        break;
                    case "soul elixir":
                        soulElixirCount++;
                        break;
                    case "bone skin":
                    case "bone skin potion":
                    case "boneskinpotion":
                        boneSkinCount++;
                        break;
                    case "berserk":
                    case "berserk potion":
                        berserkCount++;
                        break;
                    case "stone golem":
                    case "stone golem potion":
                    case "stonegolempotion":
                        stoneGolemCount++;
                        break;
                }
            }

            int totalPotions = healthCount + gamblingCount + criticalCount + labyrinthMithySoupCount
                    + slimePuddingCount + soulElixirCount + boneSkinCount + berserkCount + stoneGolemCount;

            if (totalPotions == 0) {
                System.out.println("  No potions in the inventory");
            } else {
                System.out.println("Potions:");
                String[] potionNames = {"Health", "Gambling", "Critical Strike", "Labyrinth Mithy Soup", 
                                       "Slime Pudding", "Soul Elixir", "Bone Skin", "Berserk", "Stone Golem"};
                int[] potionCounts = {healthCount, gamblingCount, criticalCount, labyrinthMithySoupCount,
                                     slimePuddingCount, soulElixirCount, boneSkinCount, berserkCount, stoneGolemCount};
                
                for (int i = 0; i < potionNames.length; i++) {
                    if (potionCounts[i] > 0) {
                        System.out.println("  " + potionNames[i] + ": " + potionCounts[i]);
                    }
                }
            }

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
