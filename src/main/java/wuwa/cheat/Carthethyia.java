package wuwa.cheat;

import java.util.Random;

/**
 * Carthethyia: Wenn er sich auf Position 32 befindet, 60% Chance +2 Felder weiter.
 * Einmal pro Runde auslösbar.
 */
public class Carthethyia extends Person {
    private boolean abilityUsedThisRound;
    
    public Carthethyia() {
        super("Carthethyia");
        this.abilityUsedThisRound = false;
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        // Nur wenn auf Position 32 und Fähigkeit noch nicht in dieser Runde genutzt
        if (this.position == 32 && !abilityUsedThisRound && random.nextDouble() < 0.6) {
            System.out.println(name + " nutzt seine Fähigkeit: +2 Felder!");
            abilityUsedThisRound = true;
            return baseMovement + 2;
        }
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Reset der Fähigkeit für nächste Runde
        // Wird von WuerfelDerby aufgerufen
    }
    
    public void resetAbility() {
        abilityUsedThisRound = false;
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte
    }
}
