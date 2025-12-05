package projet;

import java.util.Scanner;

import projet.armor.*;
import projet.weapon.*;
import java.util.Random;
import projet.enemies.*;

public class App {

    public static void main(String[] args) {
        boolean revival = true;

        Enemy goblin = new Goblin();
        Enemy orc = new Orc();
        Enemy troll = new Troll();
        Enemy kawaleck = new Kawaleck();

        Scanner scanner = new Scanner(System.in);

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
        int choiceInt = Integer.parseInt(armorChoice);

        while (choiceInt != 1 && choiceInt != 2 && choiceInt != 3) {
            System.out.print("Invalid armor choice. Choose your armor: \n 1. Naked \n 2. WoodenArmour \n 3. IronArmour: ");
            armorChoice = scanner.nextLine();
            choiceInt = Integer.parseInt(armorChoice);
        }

        Armor playerArmor = new Naked();
        switch (choiceInt) {
            case 1:
                playerArmor = new Naked();
                break;
            case 2:
                playerArmor = new WoodenArmour();
                break;
            case 3:
                playerArmor = new IronArmour();
        }

        Player player = new Player(userName, 100, playerWeapon, playerArmor);

        while (true) {

            Fight fight = new Fight();
            Enemy[] enemies = new Enemy[]{goblin, orc, troll, kawaleck};
            
            java.util.Random rand = new java.util.Random();

            while (fight.aliveVerification(player)) {
                Enemy enemy;
                int lvl = player.getLevel();
                double r = rand.nextDouble();

                if (lvl < 5) {
                    enemy = enemies[0];
                } else if (lvl >= 5 && lvl < 10) {
                    enemy = rand.nextBoolean() ? enemies[0] : enemies[1];
                } else if (lvl >= 10 && lvl < 20) {
                    if (r < 0.75) {
                        enemy = rand.nextBoolean() ? enemies[0] : enemies[1];
                    } else {
                        enemy = enemies[2];
                    }
                } else {
                    if (r < 0.70) {
                        enemy = rand.nextBoolean() ? enemies[0] : enemies[1];
                    } else if (r < 0.95) {
                        enemy = enemies[2];
                    } else {
                        enemy = enemies[3];
                    }
                }

                //player.resetHealth();
                goblin.resetHealth();
                orc.resetHealth();
                troll.resetHealth();
                kawaleck.resetHealth();
                
                System.out.println("\n--- New fight: " + enemy.getName() + " ---");

                while (fight.aliveVerification(player) && fight.aliveVerification(enemy)) {
                    System.out.print("1. Attack \n2. Use Potion \n3. Display statistics \nChoose an action: ");

                    String action = scanner.nextLine().trim();
                    if (action.equals("1")) {
                        fight.attack(player, enemy);
                    } else if (action.equals("2")) {
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
                            System.out.println("Total Health Potion: " + healthCount + " | Total Gambling Potion: " + gamblingCount);
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
                                        continue;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid potion name.");
                                    continue;
                                }
                            }
                            player.usePotion(selectedPotion);
                            player.getInventory().removePotion(selectedPotion);
                        }
                    } else if (action.equals("3")) {
                        System.out.println(player.getName() + " - Health: " + player.getHealth() + " | Weapon: "
                                + player.getWeapon().getName() + " | Armor: " + player.getArmor().getName() + " | XP: "
                                + player.getXp() + " | Level: " + player.getLevel());
                        System.out.println(enemy.getName() + " - Health: " + enemy.getHealth() + " | Weapon: "
                                + enemy.getWeapon().getName() + " | Armor: " + enemy.getArmor().getName());
                        continue;
                    } else {
                        System.out.println("Invalid action, try again.");
                        continue;
                    }

                    System.out.println("After the exchange: " + player.getName() + " Health = " + player.getHealth()
                            + " | " + enemy.getName() + " Health = " + enemy.getHealth());

                    if (!fight.aliveVerification(player)) {
                        if (revival == true) {
                            System.out.println("You have been given a second chance !");
                            player.setHealth(player.getBaseHealth() / 2);
                            revival = false;
                        } else {
                            System.out.println("\nYou are dead. Game over.");
                            scanner.close();
                            break;
                        }
                    }

                    if (!fight.aliveVerification(enemy)) {
                        System.out.println("\nYou defeated " + enemy.getName() + "!");
                        player.gainXp(enemy.getXpReward());
                        System.out.println("New XP points: " + player.getXp() + " | Level: " + player.getLevel());
                        Random randpotion = new Random();
                        int newPotion = randpotion.nextInt(100);

                        if (newPotion < 40) {
                            System.out.println("You found a health potion!");
                            player.getInventory().addPotion(new Potion("Health Potion"));
                        } else if (newPotion >= 40 && newPotion < 60) {
                            System.out.println("You found a gambling potion");
                            player.getInventory().addPotion(new Potion("Gambling Potion"));
                        } else {
                            System.out.println("You found Nothing.");
                        }

                        player.setWeapon(player.getOldWeapon());
                    }
                }

                if (fight.aliveVerification(player)) {
                    System.out.println("\nEnd of battles. " + player.getName() + " survives with " + player.getHealth()
                            + " health points.");
                }
            }
        }
    }
}