package projet;

public class Fight {
    public void attack(Champion attacker, Champion defender) {
        Weapon attackerWeapon = attacker.getWeapon();
        Weapon defenderWeapon = defender.getWeapon();
        
        if (attackerWeapon.getAttackSpeed() < defenderWeapon.getAttackSpeed()) {
            attacker.takeDamage(defenderWeapon);
            defender.takeDamage(attackerWeapon);
        } else {
            defender.takeDamage(attackerWeapon);
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