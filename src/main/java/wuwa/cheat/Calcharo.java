package wuwa.cheat;

/**
 * Calcharo: Wenn er sich auf der letzten Position befindet, bewegt er sich zusätzlich 3 Felder weiter.
 */
public class Calcharo extends Person {
    private static final int LAST_POSITION = 32;
    
    public Calcharo() {
        super("Calcharo");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Überprüfe, ob Calcharo sich auf der letzten Position (32) befindet
        if (this.position == LAST_POSITION) {
            // +3 Felder zusätzlich
            return baseMovement + 3;
        }
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Keine speziellen Effekte am Ende des Zuges
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte beim Raumzeitriss
    }
}
