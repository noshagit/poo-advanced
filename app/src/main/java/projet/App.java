package projet;

import java.util.Random;
import java.util.Scanner;

import projet.armor.*;
import projet.weapon.*;
import projet.enemies.*;
import projet.potions.*;

public class App {

    public static void main(String[] args) {
        boolean revival = true;

        Enemy goblin = new Goblin();
        Enemy orc = new Orc();
        Enemy troll = new Troll();
        Enemy kawaleck = new Kawaleck();
        Enemy[] enemies = new Enemy[] { goblin, orc, troll, kawaleck };

        Scanner scanner = new Scanner(System.in);
        Player player = createPlayer(scanner);
        Fight fight = new Fight();

        while (true) {
            java.util.Random rand = new java.util.Random();

            while (player.getHealth() > 0) {
                Enemy enemy;

                enemy = randEnemy(player, enemies, rand);

                for (Enemy e : enemies) {
                    e.resetHealth();
                }

                System.out.println("\n--- New fight: " + enemy.getName() + " ---");

                while (player.getHealth() > 0 && enemy.getHealth() > 0) {

                    boolean skip = chooseAction(player, enemy, fight, scanner);
                    if (skip) {
                        continue;
                    }

                    System.out.println("After the exchange: " + player.getName() + " Health = " + player.getHealth()
                            + " | " + enemy.getName() + " Health = " + enemy.getHealth());

                    if (player.getHealth() <= 0) {
                        if (revival == true) {
                            System.out.println("You have been given a second chance !");
                            player.setHealth(player.getBaseHealth() / 2);
                            revival = false;
                        } else {
                            System.out.println("\nYou are dead. Game over.");
                            scanner.close();
                            return;
                        }
                    }

                    if (enemy.getHealth() <= 0) {
                        defeatEnemy(enemy, player, scanner);
                    }
                }

                if (player.getHealth() > 0) {
                    System.out.println("\nEnd of battles. " + player.getName() + " survives with " + player.getHealth()
                            + " health points.");
                }
            }
        }
    }

    public static Player createPlayer(Scanner scanner) {
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        while (userName.isEmpty()) {
            System.out.print("Username cannot be empty. Enter username: ");
            userName = scanner.nextLine();
        }

        System.out.print("1. Fist \n2. Sword \n3. Axe \nEnter weapon choice: ");
        String weaponChoice = scanner.nextLine();

        int weaponChoiceInt = Integer.parseInt(weaponChoice);

        while ((weaponChoiceInt < 1 || weaponChoiceInt > 3)) {
            System.out.print("Invalid weapon choice. Enter weapon choice (1. Fist, 2. Sword, 3. Axe): ");
            weaponChoice = scanner.nextLine();
            weaponChoiceInt = Integer.parseInt(weaponChoice);
        }

        Weapon playerWeapon = new Fist();
        switch (weaponChoiceInt) {
            case 1:
                playerWeapon = new Fist();
                break;
            case 2:
                playerWeapon = new Sword();
                break;
            case 3:
                playerWeapon = new Axe();
        }

        System.out.print("1. Naked \n2. WoodenArmour \n3. IronArmour: \nChoose your armor:");

        String armorChoice = scanner.nextLine();
        int armorChoiceInt = Integer.parseInt(armorChoice);

        while (armorChoiceInt < 1 || armorChoiceInt > 3) {
            System.out.print(
                    "Invalid armor choice. Choose your armor: \n 1. Naked \n 2. WoodenArmour \n 3. IronArmour: ");
            armorChoice = scanner.nextLine();
            armorChoiceInt = Integer.parseInt(armorChoice);
        }

        Armor playerArmor = new Naked();
        switch (armorChoiceInt) {
            case 1:
                playerArmor = new Naked();
                break;
            case 2:
                playerArmor = new WoodenArmour();
                break;
            case 3:
                playerArmor = new IronArmour();
        }
        return new Player(userName, 100, playerWeapon, playerArmor);
    }

    public static Enemy randEnemy(Player player, Enemy[] enemies, Random rand) {
        int lvl = player.getLevel();
        double r = rand.nextDouble();
        if (lvl < 5) {
            return enemies[0];
        }
        else if (lvl < 10) {
            return rand.nextBoolean() ? enemies[0] : enemies[1];
        }
        else if (lvl < 20) {
            if (r < 0.75) {
                return rand.nextBoolean() ? enemies[0] : enemies[1];
            } else {
                return enemies[2];
            }
        }
        else {
            if (r < 0.70) {
                return rand.nextBoolean() ? enemies[0] : enemies[1];
            } else if (r < 0.95) {
                return enemies[2];
            } else {
                return enemies[3];
            }
        }
    }

    public static boolean chooseAction(Player player, Enemy enemy, Fight fight, Scanner scanner) {
        System.out.print("1. Attack \n2. Use Potion \n3. Display statistics \nChoose an action: ");

        String action = scanner.nextLine().trim();
        switch (action) {
            case "1":
                fight.attack(player, enemy);
                break;
            case "2":
                System.out.println("Your pockets: ");
                java.util.List<Potion> potions = player.getInventory().getPotions();
                int healthCount = 0;
                int gamblingCount = 0;

                if (potions == null || potions.isEmpty()) {
                    System.out.println("No potions available");
                } else {
                    for (int i = 0; i < potions.size(); i++) {
                        Potion p = potions.get(i);
                        if ("Health Potion".equals(p.getName())) {
                            healthCount++;
                        } else if ("Gambling Potion".equals(p.getName())) {
                            gamblingCount++;
                        }
                    }
                    System.out.println("Total Health Potion: " + healthCount + " | Total Gambling Potion: "
                            + gamblingCount);
                    System.out.print("Choose a potion (name or index): ");
                    String potionChoice = scanner.nextLine().trim();

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
                                System.out.println("Invalid index.");
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid potion name.");
                            return true;
                        }
                    }
                    player.usePotion(selectedPotion);
                    player.getInventory().removePotion(selectedPotion);
                }
                break;
            case "3":
                System.out.println(player.getName() + " - Max Health: " + player.getMaxHealth() + " - Health: "
                        + player.getHealth() + " | Weapon: "
                        + player.getWeapon().getName() + " | Armor: " + player.getArmor().getName() + " | XP: "
                        + player.getXp() + " | Level: " + player.getLevel());
                System.out.println(enemy.getName() + " - Health: " + enemy.getHealth() + " | Weapon: "
                        + enemy.getWeapon().getName() + " | Armor: " + enemy.getArmor().getName());
                return true;
            default:
                System.out.println("Invalid action, try again.");
                return true;
        }
        return false;
    }

    public static void defeatEnemy(Enemy enemy, Player player, Scanner scanner) {
        System.out.println("\nYou defeated " + enemy.getName() + "!");
        player.gainXp(enemy.getXpReward(), scanner);
        System.out.println("New XP points: " + player.getXp() + " | Level: " + player.getLevel());
        Random randpotion = new Random();
        int newPotion = randpotion.nextInt(100);

        if (newPotion < 40) {
            System.out.println("You found a health potion!");
            player.getInventory().addPotion(new HealthPotion());
        } else if (newPotion >= 40 && newPotion < 60) {
            System.out.println("You found a gambling potion");
            player.getInventory().addPotion(new GamblingPotion());
        } else {
            System.out.println("You found Nothing.");
        }

        player.setWeapon(player.getOldWeapon());
    }
}
