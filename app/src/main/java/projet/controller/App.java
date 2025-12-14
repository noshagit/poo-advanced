package projet.controller;

/* IMPORTS */

import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import projet.model.enemies.IEnemyProvider;
import projet.model.enemies.DefaultEnemyProvider;
import java.util.List;
import projet.model.weapons.IWeaponProvider;
import projet.model.weapons.DefaultWeaponProvider;
import projet.model.armors.IArmorProvider;
import projet.model.armors.DefaultArmorProvider;
import projet.model.potions.IPotionProvider;
import projet.model.potions.DefaultPotionProvider;

import projet.model.potions.*;
import projet.model.weapons.*;
import projet.model.armors.*;
import projet.model.enemies.*;
import projet.model.Player;
import projet.view.View;
import projet.view.ConsoleView;

/**
 * The main application class for the game.
 */
public class App {
    public static void main(String[] args) {
        View view = new ConsoleView();

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.load();
        view.println("Current Leaderboard:");
        for (Map.Entry<String, Integer> entry : leaderboard.getScores().entrySet()) {
            view.println(entry.getKey() + ": " + entry.getValue());
        }
        gameLoop(view);
    }

    /**
     * The main game loop handling player actions and enemy encounters.
     */
    public static void gameLoop(View view) {
        boolean revival = true;

        IEnemyProvider enemyProvider = new DefaultEnemyProvider();
        List<Enemy> enemies = enemyProvider.getEnemies();
        IWeaponProvider weaponProvider = new DefaultWeaponProvider();
        List<Weapon> weapons = weaponProvider.getWeapons();
        IArmorProvider armorProvider = new DefaultArmorProvider();
        List<Armor> armors = armorProvider.getArmors();
        IPotionProvider potionProvider = new DefaultPotionProvider();
        List<Potion> potions = potionProvider.getPotions();

        Scanner scanner = new Scanner(System.in);
        Player player = createPlayer(scanner, view, weapons, armors);

        view.println("Welcome " + player.getName() + "! Let the battles begin!"
                + "\n============================\n");

        while (true) {
            int fightCount = 1;
            while (player.getHealth() > 0) {
                Enemy enemy;

                enemy = Fight.randEnemy(player, enemies.toArray(new Enemy[0]));

                enemy.resetHealth();

                view.println("\n--- Fight number " + fightCount + ": " + enemy.getName() + " ---\n");

                while (player.getHealth() > 0 && enemy.getHealth() > 0) {

                    boolean isFight = Fight.chooseAction(player, enemy, scanner);
                    if (!isFight) { // skip post-fight updates if no fight occurred
                        continue;
                    }
                    view.println("============================\n"
                            + "After the exchange: " + player.getName() + "'s Health = " + player.getHealth()
                            + " | " + enemy.getName() + " Health = " + enemy.getHealth()
                            + "\n\n============================\n\n");

                    if (player.getHealth() <= 0) {
                        if (revival == true) {
                            view.println("You have been given a second chance !\n");
                            player.setHealth(player.getBaseHealth() / 2);
                            revival = false;
                        } else {
                            view.println("\nYou are dead. Game over.\n");
                            scanner.close();

                            Leaderboard leaderboard1 = new Leaderboard();
                            leaderboard1.load();
                            leaderboard1.setScore(player.getName(), player.getTotalXp());
                            view.println("Your score has been recorded in the leaderboard."
                                    + "\nTotal XP gained: " + player.getTotalXp());
                            return;
                        }
                    }

                    if (enemy.getHealth() <= 0) {
                        defeatEnemy(enemy, player, scanner, view);
                        view.println();
                    }
                    player.setExtraArmor(0);
                    player.setExtraDamage(0);
                }

                if (player.getHealth() > 0) {
                    view.println("\nEnd of battle. " + player.getName() + " survives with "
                            + player.getHealth() + " health points.\n");
                }

                view.println();
                AfterFight(player, scanner, view);
                view.println();
                fightCount++;
                player.setWeapon(player.getOldWeapon());
                player.setLifeSteal(false);
                player.setChanceToStun(false);
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
    public static void AfterFight(Player player, Scanner scanner, View view) {
        view.println("1. continue fighting\n2. check my inventory\n3. Leave the game\nChoose an action: ");
        view.print("> ");
        String actionChoice = scanner.nextLine();

        while (true) {
            switch (actionChoice) {
                case "1" -> {
                    view.println("Continuing the fight...");
                    return;
                }
                case "2" -> {
                    view.println();
                    player.checkInventory(scanner);
                    break;
                }
                case "3" -> {
                    while (true) {
                        view.print("\nAre you sure? Your score will be saved in the leaderboard. (y/n): ");
                        String confirmation = scanner.nextLine().trim();
                        if ("y".equalsIgnoreCase(confirmation)) {
                            view.println("Exiting game...");
                            scanner.close();

                            Leaderboard leaderboard = new Leaderboard();
                            leaderboard.load();
                            leaderboard.setScore(player.getName(), player.getTotalXp());
                            view.println("Your score has been recorded in the leaderboard."
                                    + "\nTotal XP gained: " + player.getTotalXp());

                            System.exit(0);
                        } else if ("n".equalsIgnoreCase(confirmation)) {
                            break;
                        } else {
                            view.println("Invalid input. Please enter 'y' or 'n'.");
                        }
                    }
                    break;
                }
                default -> view.println("Invalid choice. Please choose again.");
            }
            view.println("1. continue fighting\n2. check my inventory\n3. Leave the game\nChoose an action: ");
            view.print("> ");
            actionChoice = scanner.nextLine();
            view.println();
        }
    }

    /**
     * createPlayer handles the creation of a new player.
     * 
     * @param scanner The scanner to read user input.
     * @return The created Player object.
     */
    public static Player createPlayer(Scanner scanner, View view, List<Weapon> weapons, List<Armor> armors) {
        view.println("\n\n============================\n"
                + "Enter username: ");
        view.print("> ");
        String userName = scanner.nextLine();
        view.println();

        while (userName.isEmpty()) {
            view.println("Username cannot be empty. Enter username: ");
            view.print("> ");
            userName = scanner.nextLine();
            view.println();
        }

        view.println("============================\n"
                + "Choose your weapon:\n"
                + "\n1. Fist"
                + "\n2. Sword"
                + "\n3. Axe");
        view.print("> ");

        String weaponChoice = scanner.nextLine();
        view.println();

        while (!"1".equals(weaponChoice) && !"2".equals(weaponChoice) && !"3".equals(weaponChoice)) {
            view.println("Invalid weapon choice. Enter weapon choice (1. Fist, 2. Sword, 3. Axe): ");
            view.print("> ");
            weaponChoice = scanner.nextLine();
            view.println();
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

        view.println("============================\n"
                + "\nChoose your armor:\n"
                + "\n1. Naked"
                + "\n2. WoodenArmor"
                + "\n3. IronArmor");
        view.print("> ");

        String armorChoice = scanner.nextLine();
        view.println();

        while (!"1".equals(armorChoice) && !"2".equals(armorChoice) && !"3".equals(armorChoice)) {
            view.println("Invalid armor choice. Choose your armor:"
                    + "\n 1. Naked"
                    + "\n 2. WoodenArmor"
                    + "\n 3. IronArmor: ");
            view.print("> ");
            armorChoice = scanner.nextLine();
            view.println();
        }

        Armor playerArmor = new Naked();
        switch (armorChoice) {
            case "1":
                playerArmor = new Naked();
                break;
            case "2":
                playerArmor = new WoodenArmor();
                break;
            case "3":
                playerArmor = new IronArmor();
        }

        view.println("\n============================\n");

        return new Player(userName, 100, playerWeapon, playerArmor);
    }

    /**
     * defeatEnemy handles the actions to take when an enemy is defeated.
     * 
     * @param enemy   The enemy that was defeated.
     * @param player  The player who defeated the enemy.
     * @param scanner The scanner to read user input.
     */
    public static void defeatEnemy(Enemy enemy, Player player, Scanner scanner, View view) {
        view.println("\nYou defeated " + enemy.getName() + "!");
        player.gainXp(enemy.getXpReward(), scanner);
        view.println("New XP points: " + player.getXp() + " | Level: " + player.getLevel() + "\n");

        NewPotion(player, enemy, view);

        NewWeapon(player, enemy, scanner, view);

        NewArmor(player, enemy, scanner, view);
    }

    /**
     * NewPotion randomly gives the player a new potion after defeating an enemy.
     * 
     * @param player The player to receive the potion.
     */
    public static void NewPotion(Player player, Enemy enemy, View view) {
        Random randpotion = new Random();
        int newPotion = randpotion.nextInt(100);
        if (newPotion < 30) {
            view.println("You found a health potion!");
            player.getInventory().addPotion(new HealthPotion());
        } else if (newPotion >= 30 && newPotion < 50) {
            view.println("You found a gambling potion");
            player.getInventory().addPotion(new GamblingPotion());
        } else if (newPotion >= 50 && newPotion < 70) {
            view.println("You found a critical potion and a health potion!");
            player.getInventory().addPotion(new CriticalPotion());
            player.getInventory().addPotion(new HealthPotion());
        }

        switch (enemy.getName()) {
            case "Skeleton" -> {
                if (newPotion < 30) {
                    view.println("You found a bone skin potion!");
                    player.getInventory().addPotion(new BoneSkinPotion());
                }
            }
            case "Minotaur" -> {
                if (newPotion < 15) {
                    view.println("You found a Labyrinth Mighty Soup!");
                    player.getInventory().addPotion(new LabyrintheMightySoup());
                }
            }
            case "Slime" -> {
                if (newPotion < 30) {
                    view.println("You found a Slime Pudding!");
                    player.getInventory().addPotion(new SlimePudding());
                }
            }
            // case "Goblin" -> {
            // if (newPotion < 20) {
            // view.println("You found a !");
            // player.getInventory().addPotion(new ());
            // }
            // }
            case "Orc" -> {
                if (newPotion < 20) {
                    view.println("You found a Berserk Potion!");
                    player.getInventory().addPotion(new BerserkPotion());
                }
            }
            // case "Troll" -> {
            // if (newPotion < 15) {
            // view.println("You found a ");
            // player.getInventory().addPotion(new ());
            // }
            // }
            case "Reaper" -> {
                if (newPotion < 25) {
                    view.println("You found a Soul Elixir!");
                    player.getInventory().addPotion(new SoulElixir());
                }
            }
        }
        view.println();
    }

    /**
     * NewWeapon randomly gives the player a new weapon after defeating an enemy.
     * 
     * @param player  The player to receive the weapon.
     * @param enemy   The enemy that was defeated.
     * @param scanner The scanner to read user input.
     */
    public static void NewWeapon(Player player, Enemy enemy, Scanner scanner, View view) {
        Random randweapon = new Random();
        int newWeapon = randweapon.nextInt(100);

        if (newWeapon < 20) {
            Weapon droppedWeapon = enemy.getWeapon();
            if (droppedWeapon instanceof Fist) {
                return;
            }
            view.println("The enemy dropped a " + droppedWeapon.getName() + "!"
                    + "\nDo you want to take it? (y/n): ");
            view.print("> ");
            String choice;
            while (true) {
                choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                    break;
                }
                view.println("Invalid input. Please enter 'y' or 'n'.");
                view.print("> ");
            }
            view.println();

            if (choice.equalsIgnoreCase("y")) {
                player.getInventory().addWeapon(droppedWeapon);
                view.println("You put the " + droppedWeapon.getName() + " in your inventory.");
            }
        }
        view.println();
    }

    /**
     * NewArmor randomly gives the player a new armor after defeating an enemy.
     *
     * @param player  The player to receive the armor.
     * @param enemy   The enemy that was defeated.
     * @param scanner The scanner to read user input.
     */
    public static void NewArmor(Player player, Enemy enemy, Scanner scanner, View view) {
        Random randarmor = new Random();
        int newArmor = randarmor.nextInt(100);

        if (newArmor < 20) {
            Armor droppedArmor = enemy.getArmor();
            if (droppedArmor instanceof Naked) {
                return;
            }
            view.println("The enemy dropped a " + droppedArmor.getName() + "!"
                    + "\nDo you want to take it? (y/n): ");
            view.print("> ");
            String choice;
            while (true) {
                choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                    break;
                }
                view.println("Invalid input. Please enter 'y' or 'n'.");
                view.print("> ");
            }
            view.println();

            if (choice.equalsIgnoreCase("y")) {
                player.getInventory().addArmor(droppedArmor);
                view.println("You put the " + droppedArmor.getName() + " in your inventory.");
            }
        }
        view.println();
    }
}
