package projet;

import projet.weapon.Weapon;

public class Fight {
    public void attack(Champion attacker, Champion defender) {
        Weapon attackerWeapon = attacker.getWeapon();
        Weapon defenderWeapon = defender.getWeapon();
        int diffRatio;
        
        if (attacker.getMoveSpeed() < defender.getMoveSpeed()) {
            diffRatio = (int)(defender.getMoveSpeed() + attacker.getMoveSpeed()) / 2;
            System.out.println("DiffRatio: " + diffRatio);
            for (int i = 0; i < diffRatio; i++) {
                attacker.takeDamage(defenderWeapon);
                System.out.println("Attacker Health after hit " + (i+1) + ": " + attacker.getHealth());
            }
            defender.takeDamage(attackerWeapon);
        } else {
            diffRatio = (int)(attacker.getMoveSpeed() + defender.getMoveSpeed())/2;
            System.out.println("DiffRatio: " + diffRatio);
            for (int i = 0; i < diffRatio; i++) {
                defender.takeDamage(attackerWeapon);
                System.out.println("Attacker Health after hit " + (i+1) + ": " + attacker.getHealth());
            }
            attacker.takeDamage(defenderWeapon);
        }
    }

    public boolean aliveVerification(Champion champion) {
        if (champion.getHealth() <= 0) {
            return false;
        }
        return true;
    }
}