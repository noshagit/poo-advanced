package projet.model.enemies;

/* IMPORTS */

import java.util.ArrayList;
import java.util.List;

public class DefaultEnemyProvider implements IEnemyProvider {
    @Override
    public List<Enemy> getEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Slime());
        enemies.add(new Goblin());
        enemies.add(new Skeleton());
        enemies.add(new Orc());
        enemies.add(new Reaper());
        enemies.add(new Troll());
        enemies.add(new StoneGolem());
        enemies.add(new Minotaur());
        return enemies;
    }
}
