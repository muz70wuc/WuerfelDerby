package wuwa.cheat;

import java.util.Random;

/**
 * Luuk Herssen: Wird ein Vorschubmechanismus ausgelöst, +2 Felder extra.
 * Wird Hemmmechanismus ausgelöst, -1 Feld (statt normal).
 */
public class LuukHerssen extends Person {
    private int fieldBonus;  // Bonus, der durch Feldeffekte hinzugefügt wird
    
    public LuukHerssen() {
        super("Luuk Herssen");
        this.fieldBonus = 0;
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        int totalMovement = baseMovement + fieldBonus;
        fieldBonus = 0;  // Reset nach Anwendung
        return totalMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Keine speziellen Effekte am Ende des Zuges
    }
    
    public void applyFieldEffect(Position.FieldProperty property) {
        if (property == Position.FieldProperty.VORSCHUBMECHANISMUS) {
            System.out.println(name + " erhält +2 durch Vorschubmechanismus!");
            fieldBonus += 2;
        } else if (property == Position.FieldProperty.HEMMMECHANISMUS) {
            System.out.println(name + " wird durch Hemmmechanismus beeinflusst!");
            fieldBonus -= 1;
        }
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte
    }
}
