package projet.model.potions;

/* IMPORTS */

import java.util.ArrayList;
import java.util.List;

public class DefaultPotionProvider implements IPotionProvider {
    @Override
    public List<Potion> getPotions() {
        List<Potion> potions = new ArrayList<>();
        potions.add(new HealthPotion());
        potions.add(new GamblingPotion());
        potions.add(new CriticalPotion());
        potions.add(new BoneSkinPotion());
        potions.add(new LabyrintheMightySoup());
        potions.add(new SlimePudding());
        potions.add(new BerserkPotion());
        potions.add(new SoulElixir());
        potions.add(new StoneGolemPotion());
        return potions;
    }
}
