package projet.model.armors;

/* IMPORTS */

import java.util.ArrayList;
import java.util.List;

/**Class providing a default list of armor equipment.*/
public class DefaultArmorProvider implements IArmorProvider {
    @Override
    public List<Armor> getArmors() {
        List<Armor> armors = new ArrayList<>();
        armors.add(new Naked());
        armors.add(new WoodenArmor());
        armors.add(new IronArmor());
        armors.add(new KevlarArmor());
        armors.add(new LeatherArmor());
        armors.add(new ChainmailArmor());
        armors.add(new BoneArmor());
        armors.add(new StoneArmor());
        armors.add(new MythrilArmor());
        armors.add(new ReaperArmor());
        return armors;
    }
}
