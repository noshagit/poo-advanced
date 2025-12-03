package projet;

import java.util.Scanner;

import projet.armor.*;
import projet.weapon.*;

public class App {

    public static void main(String[] args) {
        Champion enemy1 = new Champion("Goblin", 50, new Sword(), new Naked());
        Champion enemy2 = new Champion("Orc", 80, new Axe(), new WoodenArmour());
        Champion enemy3 = new Champion("Troll", 120, new Log(), new Naked());
        Champion enemy4 = new Champion("Kawaleck", 200, new Cancer(), new IronArmour());

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username: ");
        String userName = scanner.nextLine();

        while (userName.isEmpty()) {
            System.out.println("Username cannot be empty. Enter username: ");
            userName = scanner.nextLine();
        }
        
        System.out.println("Enter weapon choice : \n 1. Fist \n 2. Sword \n 3. Axe \n");

        String weaponChoice = scanner.nextLine();
        int weaponChoiceInt = Integer.parseInt(weaponChoice);

        while ((weaponChoiceInt < 1 || weaponChoiceInt > 3)) {
            System.out.println("Invalid weapon choice. Enter weapon choice (1. Fist, 2. Sword, 3. Axe):");
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

        System.out.println("Choose your armor: \n 1. Naked \n 2. WoodenArmour \n 3. IronArmour: \n");

        String armorChoice = scanner.nextLine();
        int choiceInt = Integer.parseInt(armorChoice);

        while (choiceInt != 1 && choiceInt != 2 && choiceInt != 3) {
            System.out.println(
                    "Invalid armor choice. Choose your armor: \n 1. Naked \n 2. WoodenArmour \n 3. IronArmour: \n");
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

        Champion player = new Champion(userName, 100, playerWeapon, playerArmor);


        while(true){

            Fight fight = new Fight();
            Champion[] enemies = new Champion[]{enemy1, enemy2, enemy3, enemy4};

            java.util.Random rand = new java.util.Random();

            while (fight.aliveVerification(player)) {
                Champion enemy;
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
                enemy1.resetHealth();
                enemy2.resetHealth();
                enemy3.resetHealth();
                enemy4.resetHealth();
                System.out.println("\n--- New fight: " + enemy.getName() + " ---");
                while (fight.aliveVerification(player) && fight.aliveVerification(enemy)) {
                    System.out.println("\nChoose an action:");
                    System.out.println("1. Attack");
                    System.out.println("2. Display statistics");

                    String action = scanner.nextLine().trim();
                    if (action.equals("1")) {
                        fight.attack(player, enemy);
                    } else if (action.equals("2")) {
                        System.out.println(player.getName() + " - Health: " + player.getHealth() + " | Weapon: " + player.getWeapon().getName() + " | Armor: " + player.getArmor().getName() + " | XP: " + player.getXp() + " | Level: " + player.getLevel());
                        System.out.println(enemy.getName() + " - Health: " + enemy.getHealth() + " | Weapon: " + enemy.getWeapon().getName() + " | Armor: " + enemy.getArmor().getName());
                        continue;
                    } else {
                        System.out.println("Invalid action, try again.");
                        continue;
                    }

                    System.out.println("After the exchange: " + player.getName() + " Health = " + player.getHealth() + " | " + enemy.getName() + " Health = " + enemy.getHealth());
                }

                if (!fight.aliveVerification(player)) {
                    System.out.println("\nYou are dead. Game over.");
                    scanner.close();
                    break;
                }

                if (!fight.aliveVerification(enemy)) {
                    System.out.println("\nYou defeated " + enemy.getName() + "!");
                    player.gainXp(50);
                    System.out.println("You gain 50 XP. XP: " + player.getXp() + " | Level: " + player.getLevel());
                    player.setWeapon(player.getOldWeapon());
                } else {
                    System.out.println("Moving to the next opponent.");
                }
            }

            if (fight.aliveVerification(player)) {
                System.out.println("\nEnd of battles. " + player.getName() + " survives with " + player.getHealth() + " health points.");
            }
        }
    }
}