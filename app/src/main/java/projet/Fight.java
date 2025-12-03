package projet;

public class Fight {
    public void attack(Champion attacker, Champion defender) {
        Weapon attackerWeapon = attacker.getWeapon();
        Weapon defenderWeapon = defender.getWeapon();
        
        if (attackerWeapon.getAttackSpeed() < defenderWeapon.getAttackSpeed()) {
            attacker.takeDamage(defenderWeapon);
        } else {
            defender.takeDamage(attackerWeapon);
        }
    }

    public boolean aliveVerification(Champion champion) {
        if (champion.getHealth() <= 0) {
            return false;
        }
        return true;
    }
}