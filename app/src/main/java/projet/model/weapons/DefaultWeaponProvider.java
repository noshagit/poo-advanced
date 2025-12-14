package projet.model.weapons;

import java.util.ArrayList;
import java.util.List;

public class DefaultWeaponProvider implements IWeaponProvider {
    @Override
    public List<Weapon> getWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(new Fist());
        weapons.add(new Sword());
        weapons.add(new Axe());
        weapons.add(new Hammer());
        weapons.add(new Flail());
        weapons.add(new Mace());
        weapons.add(new Scythe());
        weapons.add(new Spear());
        weapons.add(new StoneFist());
        weapons.add(new Log());
        weapons.add(new Cancer());
        return weapons;
    }
}
