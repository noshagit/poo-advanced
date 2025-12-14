package projet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import projet.controller.Inventory;
import projet.model.Player;
import projet.model.weapons.Fist;
import projet.model.armors.Naked;
import projet.model.enemies.Enemy;
import projet.model.enemies.DefaultEnemyProvider;
import projet.model.weapons.DefaultWeaponProvider;
import projet.model.armors.DefaultArmorProvider;
import projet.model.potions.DefaultPotionProvider;

import java.util.List;

public class PlayerTest {
    @Test
    public void testPlayerInitialization() {
        Player player = new Player("TestUser", 100, new Fist(), new Naked());
        assertEquals("TestUser", player.getName());
        assertEquals(100, player.getHealth());
        assertNotNull(player.getWeapon());
        assertNotNull(player.getArmor());
    }

    @Test
    public void testInventoryAddWeapon() {
        Player player = new Player("TestUser", 100, new Fist(), new Naked());
        Inventory inv = player.getInventory();
        var weaponProvider = new DefaultWeaponProvider();
        var weapons = weaponProvider.getWeapons();
        inv.addWeapon(weapons.get(0));
        assertTrue(inv.getWeapons().contains(weapons.get(0)));
    }

    @Test
    public void testInventoryAddArmor() {
        Player player = new Player("TestUser", 100, new Fist(), new Naked());
        Inventory inv = player.getInventory();
        var armorProvider = new DefaultArmorProvider();
        var armors = armorProvider.getArmors();
        inv.addArmor(armors.get(0));
        assertTrue(inv.getArmors().contains(armors.get(0)));
    }

    @Test
    public void testInventoryAddPotion() {
        Player player = new Player("TestUser", 100, new Fist(), new Naked());
        Inventory inv = player.getInventory();
        var potionProvider = new DefaultPotionProvider();
        var potions = potionProvider.getPotions();
        inv.addPotion(potions.get(0));
        assertTrue(inv.getPotions().contains(potions.get(0)));
    }

    @Test
    public void testEnemyProvider() {
        var enemyProvider = new DefaultEnemyProvider();
        List<Enemy> enemies = enemyProvider.getEnemies();
        assertFalse(enemies.isEmpty());
        assertNotNull(enemies.get(0).getName());
    }

    @Test
    public void testWeaponLevelAndXp() {
        var weapon = new Fist();
        int initialLevel = weapon.getLevel();
        weapon.gainXp();
        assertTrue(weapon.getXp() > 0);
        // Simule plusieurs gains d'XP pour vérifier la montée de niveau
        for (int i = 0; i < 20; i++) weapon.gainXp();
        assertTrue(weapon.getLevel() >= initialLevel);
    }

    @Test
    public void testPotionUse() {
        Player player = new Player("PotionUser", 50, new Fist(), new Naked());
        var potionProvider = new DefaultPotionProvider();
        var potions = potionProvider.getPotions();
        // On applique toutes les potions pour vérifier qu'aucune ne plante
        for (var potion : potions) {
            potion.use(player);
        }
        // Le test passe si aucune exception n'est levée
        assertTrue(true);
    }

    @Test
    public void testFightAttack() {
        Player player = new Player("Fighter", 100, new Fist(), new Naked());
        var enemyProvider = new DefaultEnemyProvider();
        Enemy enemy = enemyProvider.getEnemies().get(0);
        int enemyHealthBefore = enemy.getHealth();
        projet.controller.Fight.attack(player, enemy);
        assertTrue(enemy.getHealth() <= enemyHealthBefore);
    }

    @Test
    public void testInventoryRemoveWeapon() {
        Player player = new Player("TestUser", 100, new Fist(), new Naked());
        Inventory inv = player.getInventory();
        var weaponProvider = new DefaultWeaponProvider();
        var weapons = weaponProvider.getWeapons();
        inv.addWeapon(weapons.get(0));
        inv.removeWeapon(weapons.get(0));
        assertFalse(inv.getWeapons().contains(weapons.get(0)));
    }

    @Test
    public void testLeaderboardSetAndGetScore() {
        var leaderboard = new projet.controller.Leaderboard();
        leaderboard.setScore("Le Kawleck", 10000);
        assertEquals(10000, leaderboard.getScores().get("Le Kawleck").intValue());
    }
}
