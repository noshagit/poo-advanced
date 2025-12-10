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

                enemy = Fight.randEnemy(player);

                for (Enemy e : enemies) {
                    e.resetHealth();
                }

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
     * randEnemy selects a random enemy based on the player's level.
     * 
     * @param player  The player whose level is considered.
     * @param enemies The array of possible enemies.
     * @param rand    The random number generator.
     * @return A randomly selected enemy.
     */
    public static Enemy randEnemy(Player player, Random rand) {
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

    /**
     * chooseAction allows the player to choose an action during a fight.
     * 
     * @param player  The player who is fighting.
     * @param enemy   The enemy being fought.
     * @param fight   The fight instance.
     * @param scanner The scanner for user input.
     * @return true if the action was successful, false otherwise.
     */
    public static boolean chooseAction(Player player, Enemy enemy, Scanner scanner) {
        System.out.println("1. Attack \n2. Use Potion \n3. Display statistics \nChoose an action: ");
        System.out.print("> ");

        String action = scanner.nextLine().trim();
        System.out.println();

        switch (action) {
            case "1":
                Fight.attack(player, enemy);
                break;
            case "2":
                System.out.println("\n============================\n"
                        + "Your pockets: ");
                java.util.List<Potion> potions = player.getInventory().getPotions();
                int healthCount = 0;
                int gamblingCount = 0;
                if (potions == null || potions.isEmpty()) {

                    System.out.println("No potions available"
                            + "\n============================\n");
                    return true;
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

                        if ("Health".equalsIgnoreCase(name)) {
                            healthCount++;
                        } else if ("Gambling".equalsIgnoreCase(name)) {
                            gamblingCount++;
                        } else if ("Critical".equalsIgnoreCase(name)) {
                            criticalCount++;
                        } else if ("Labyrinth Mithy Soup".equalsIgnoreCase(name)
                                || "Labyrinthe Mighty Soup".equalsIgnoreCase(name)
                                || "LabyrintheMightySoup".equalsIgnoreCase(name)) {
                            labyrinthMithySoupCount++;
                        } else if ("Slime Pudding".equalsIgnoreCase(name)) {
                            slimePuddingCount++;
                        } else if ("Soul Elixir".equalsIgnoreCase(name)) {
                            soulElixirCount++;
                        } else if ("Bone Skin".equalsIgnoreCase(name)
                                || "Bone Skin Potion".equalsIgnoreCase(name)
                                || "BoneSkinPotion".equalsIgnoreCase(name)) {
                            boneSkinCount++;
                        } else if ("Berserk".equalsIgnoreCase(name) || "Berserk Potion".equalsIgnoreCase(name)) {
                            berserkCount++;
                        } else if ("Stone Golem".equalsIgnoreCase(name)
                                || "Stone Golem Potion".equalsIgnoreCase(name)
                                || "StoneGolemPotion".equalsIgnoreCase(name)) {
                            stoneGolemCount++;
                        }
                    }

                    int totalPotions = healthCount + gamblingCount + criticalCount + labyrinthMithySoupCount
                            + slimePuddingCount + soulElixirCount + boneSkinCount + berserkCount + stoneGolemCount;

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
                            System.out.println("  Critical Strike : " + criticalCount);
                        }
                        if (labyrinthMithySoupCount > 0) {
                            System.out.println("  Labyrinth Mithy Soup : " + labyrinthMithySoupCount);
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
                        if (stoneGolemCount > 0) {
                            System.out.println("  Stone Golem: " + stoneGolemCount);
                        }
                    }

                    System.out.println("Choose a potion by name or index, or enter 'b' to go back:");
                    System.out.print("> ");
                    System.out.println();

                    while (true) {
                        String potionChoice = scanner.nextLine().trim();
                        if (potionChoice.equalsIgnoreCase("b") || potionChoice.equalsIgnoreCase("back")) {
                            return true;
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
                break;
            case "3":
                System.out.println("============================\n"
                        + player.getName() + " - Max Health: " + player.getMaxHealth() + " - Health: "
                        + player.getHealth() + " | Weapon: "
                        + player.getWeapon().getName() + " | Armor: " + player.getArmor().getName() + " | XP: "
                        + player.getXp() + " | Level: " + player.getLevel()

                        + "\n" + enemy.getName() + " - Health: " + enemy.getHealth() + " | Weapon: "
                        + enemy.getWeapon().getName() + " | Armor: " + enemy.getArmor().getName()
                        + "\n\n============================\n");
                return true;
            default:
                System.out.println("Invalid action, try again.");
                return true;
        }
        return false;
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
