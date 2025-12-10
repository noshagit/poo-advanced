package projet;

/* IMPORTS */

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import projet.potions.*;
import projet.armor.*;
import projet.enemies.*;
import projet.weapon.*;

/**
 * The main application class for the game.
 */
public class App {
    public static void main(String[] args) {
        Leaderboard leaderboard = new Leaderboard();
        Map<String, Integer> scores = leaderboard.getScores();
        leaderboard.load();
        System.out.println("Current Leaderboard:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        gameLoop();
    }

    /**
     * The main game loop handling player actions and enemy encounters.
     */
    public static void gameLoop() {
        boolean revival = true;

        Enemy[] enemies = {
                new Slime(),
                new Goblin(),
                new Skeleton(),
                new Orc(),
                new Reaper(),
                new Troll(),
                new StoneGolem(),
                new Minotaur()
        };

        Scanner scanner = new Scanner(System.in);
        Player player = createPlayer(scanner);

        System.out.println("Welcome " + player.getName() + "! Let the battles begin!"
                + "\n============================\n");

        while (true) {
            while (player.getHealth() > 0) {
                int fightCount = 0;
                Enemy enemy;

                enemy = Fight.randEnemy(player, enemies);

                enemy.resetHealth();

                System.out.println("\n--- Fight number " + ++fightCount + ": " + enemy.getName() + " ---\n");

                while (player.getHealth() > 0 && enemy.getHealth() > 0) {

                    boolean isFight = Fight.chooseAction(player, enemy, scanner);
                    if (!isFight) { // skip post-fight updates if no fight occurred
                        continue;
                    }
                    System.out.println("============================\n"
                            + "After the exchange: " + player.getName() + "'s Health = " + player.getHealth()
                            + " | " + enemy.getName() + " Health = " + enemy.getHealth()
                            + "\n\n============================\n\n");

                    if (player.getHealth() <= 0) {
                        if (revival == true) {
                            System.out.println("You have been given a second chance !\n");
                            player.setHealth(player.getBaseHealth() / 2);
                            revival = false;
                        } else {
                            System.out.println("\nYou are dead. Game over.\n");
                            scanner.close();

                            Leaderboard leaderboard = new Leaderboard();
                            leaderboard.load();
                            leaderboard.setScore(player.getName(), player.getXp());
                            System.out.println("Your score has been recorded in the leaderboard."
                                    + "\nTotal XP gained: " + player.getTotalXp());
                            return;
                        }
                    }

                    if (enemy.getHealth() <= 0) {
                        defeatEnemy(enemy, player, scanner);
                        System.out.println();
                    }
                    player.setExtraArmor(0);
                    player.setExtraDamage(0);
                }

                if (player.getHealth() > 0) {
                    System.out.println("\nEnd of battles. " + player.getName() + " survives with " + player.getHealth()
                            + " health points.\n");
                }

                System.out.println();
                AfterFight(player, scanner);
                System.out.println();
                fightCount++;
                player.setWeapon(player.getOldWeapon());
                player.setLifeSteal(false);
                player.chancetostun(false);
                player.extraDamageMightySoup(0);
                player.setExtraArmorGolem(0);
            }
        }
    }

    /**
     * AfterFight handles the actions a player can take after a fight.
     * 
     * @param player  The player who has just fought.
     * @param scanner The scanner to read user input.
     */
    public static void AfterFight(Player player, Scanner scanner) {
        System.out.println("1. continue fighting\n2. check my inventory\n3. Leave the game\nChoose an action: ");
        System.out.print("> ");
        String actionChoice = scanner.nextLine();

        while (!"1".equals(actionChoice)) {
            switch (actionChoice) {
                case "1" -> {
                    System.out.println("Continuing the fight...");
                }
                case "2" -> {
                    System.out.println();
                    player.checkInventory(scanner);
                }
                case "3" -> {
                    while (true) {
                        System.out.print("\nAre you sure? Your score will be saved in the leaderboard. (y/n): ");
                        String confirmation = scanner.nextLine().trim();
                        if ("y".equalsIgnoreCase(confirmation)) {
                            System.out.println("Exiting game...");
                            scanner.close();

                            Leaderboard leaderboard = new Leaderboard();
                            leaderboard.load();
                            leaderboard.setScore(player.getName(), player.getXp());
                            System.out.println("Your score has been recorded in the leaderboard."
                                    + "\nTotal XP gained: " + player.getTotalXp());

                            System.exit(0);
                        } else if ("n".equalsIgnoreCase(confirmation)) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        }
                    }
                }
                default -> System.out.println("Invalid choice. Please choose again.");
            }
            System.out.println("1. continue fighting\n2. check my inventory\n3. Leave the game\nChoose an action: ");
            System.out.print("> ");
            actionChoice = scanner.nextLine();
            System.out.println();
        }
    }

    /**
     * createPlayer handles the creation of a new player.
     * 
     * @param scanner The scanner to read user input.
     * @return The created Player object.
     */
    public static Player createPlayer(Scanner scanner) {
        System.out.println("\n\n============================\n"
                + "Enter username: ");
        System.out.print("> ");
        String userName = scanner.nextLine();
        System.out.println();

        while (userName.isEmpty()) {
            System.out.println("Username cannot be empty. Enter username: ");
            System.out.print("> ");
            userName = scanner.nextLine();
            System.out.println();
        }

        System.out.println("============================\n"
                + "Choose your weapon:\n"
                + "\n1. Fist"
                + "\n2. Sword"
                + "\n3. Axe");
        System.out.print("> ");

        String weaponChoice = scanner.nextLine();
        System.out.println();

        while (!"1".equals(weaponChoice) && !"2".equals(weaponChoice) && !"3".equals(weaponChoice)) {
            System.out.println("Invalid weapon choice. Enter weapon choice (1. Fist, 2. Sword, 3. Axe): ");
            System.out.print("> ");
            weaponChoice = scanner.nextLine();
            System.out.println();
        }

        Weapon playerWeapon = new Fist();
        switch (weaponChoice) {
            case "1":
                playerWeapon = new Fist();
                break;
            case "2":
                playerWeapon = new Sword();
                break;
            case "3":
                playerWeapon = new Axe();
        }

        System.out.println("============================\n"
                + "\nChoose your armor:\n"
                + "\n1. Naked"
                + "\n2. WoodenArmour"
                + "\n3. IronArmour");
        System.out.print("> ");

        String armorChoice = scanner.nextLine();
        System.out.println();

        while (!"1".equals(armorChoice) && !"2".equals(armorChoice) && !"3".equals(armorChoice)) {
            System.out.println("Invalid armor choice. Choose your armor:"
                    + "\n 1. Naked"
                    + "\n 2. WoodenArmour"
                    + "\n 3. IronArmour: ");
            System.out.print("> ");
            armorChoice = scanner.nextLine();
            System.out.println();
        }

        Armor playerArmor = new Naked();
        switch (armorChoice) {
            case "1":
                playerArmor = new Naked();
                break;
            case "2":
                playerArmor = new WoodenArmour();
                break;
            case "3":
                playerArmor = new IronArmour();
        }

        System.out.println("\n============================\n");

        return new Player(userName, 100, playerWeapon, playerArmor);
    }

    /**
     * defeatEnemy handles the actions to take when an enemy is defeated.
     * 
     * @param enemy   The enemy that was defeated.
     * @param player  The player who defeated the enemy.
     * @param scanner The scanner to read user input.
     */
    public static void defeatEnemy(Enemy enemy, Player player, Scanner scanner) {
        System.out.println("\nYou defeated " + enemy.getName() + "!");
        player.gainXp(enemy.getXpReward(), scanner);
        System.out.println("New XP points: " + player.getXp() + " | Level: " + player.getLevel() + "\n");

        NewPotion(player, enemy);

        NewWeapon(player, enemy, scanner);

        NewArmor(player, enemy, scanner);
    }

    /**
     * NewPotion randomly gives the player a new potion after defeating an enemy.
     * 
     * @param player The player to receive the potion.
     */
    public static void NewPotion(Player player, Enemy enemy) {
        Random randpotion = new Random();
        int newPotion = randpotion.nextInt(100);
        if (newPotion < 30) {
            System.out.println("You found a health potion!");
            player.getInventory().addPotion(new HealthPotion());
        } else if (newPotion >= 30 && newPotion < 50) {
            System.out.println("You found a gambling potion");
            player.getInventory().addPotion(new GamblingPotion());
        } else if (newPotion >= 50 && newPotion < 70) {
            System.out
                    .println("You found a critical potion and a health potion!");
            player.getInventory().addPotion(new CriticalPotion());
            player.getInventory().addPotion(new HealthPotion());
        }

        switch (enemy.getName()) {
            case "Skeleton" -> {
                if (newPotion < 30) {
                    System.out.println("You found a bone skin potion!");
                    player.getInventory().addPotion(new BoneSkinPotion());
                }
            }
            case "Minotaur" -> {
                if (newPotion < 15) {
                    System.out.println("You found a Labyrinth Mighty Soup!");
                    player.getInventory().addPotion(new LabyrintheMightySoup());
                }
            }
            case "Slime" -> {
                if (newPotion < 30) {
                    System.out.println("You found a Slime Pudding!");
                    player.getInventory().addPotion(new SlimePudding());
                }
            }
            // case "Goblin" -> {
            // if (newPotion < 20) {
            // System.out.println("You found a !");
            // player.getInventory().addPotion(new ());
            // }
            // }
            case "Orc" -> {
                if (newPotion < 20) {
                    System.out.println("You found a Berserk Potion!");
                    player.getInventory().addPotion(new BerserkPotion());
                }
            }
            // case "Troll" -> {
            // if (newPotion < 15) {
            // System.out.println("You found a ");
            // player.getInventory().addPotion(new ());
            // }
            // }
            case "Reaper" -> {
                if (newPotion < 25) {
                    System.out.println("You found a Soul Elixir!");
                    player.getInventory().addPotion(new SoulElixir());
                }
            }
        }
        System.out.println();
    }

    /**
     * NewWeapon randomly gives the player a new weapon after defeating an enemy.
     * 
     * @param player  The player to receive the weapon.
     * @param enemy   The enemy that was defeated.
     * @param scanner The scanner to read user input.
     */
    public static void NewWeapon(Player player, Enemy enemy, Scanner scanner) {
        Random randweapon = new Random();
        int newWeapon = randweapon.nextInt(100);

        if (newWeapon < 20) {
            Weapon droppedWeapon = enemy.getWeapon();
            System.out.println("The enemy dropped a " + droppedWeapon.getName() + "!"
                    + "\nDo you want to take it? (y/n): ");
            System.out.print("> ");
            String choice;
            while (true) {
                choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                    break;
                }
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                System.out.print("> ");
            }
            System.out.println();

            if (choice.equalsIgnoreCase("y")) {
                player.getInventory().addWeapon(droppedWeapon);
                System.out.println("You put the " + droppedWeapon.getName() + " in your inventory.");
            }
        }
        System.out.println();
    }

    /**
     * NewArmor randomly gives the player a new armor after defeating an enemy.
     *
     * @param player  The player to receive the armor.
     * @param enemy   The enemy that was defeated.
     * @param scanner The scanner to read user input.
     */
    public static void NewArmor(Player player, Enemy enemy, Scanner scanner) {
        Random randarmor = new Random();
        int newArmor = randarmor.nextInt(100);

        if (newArmor < 20) {
            Armor droppedArmor = enemy.getArmor();
            System.out.println("The enemy dropped a " + droppedArmor.getName() + "!"
                    + "\nDo you want to take it? (y/n): ");
            String choice;
            while (true) {
                choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                    break;
                }
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                System.out.print("> ");
            }
            System.out.println();

            if (choice.equalsIgnoreCase("y")) {
                player.getInventory().addArmor(droppedArmor);
                System.out.println("You put the " + droppedArmor.getName() + " in your inventory.");
            }
        }
        System.out.println();
    }
}
