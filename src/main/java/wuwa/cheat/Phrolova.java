package wuwa.cheat;

/**
 * Phrolova: Befindet sich der Würfel zu Beginn der Runde ganz unten im Stapel,
 * bewegt er sich zusätzlich 3 Felder vorwärts.
 */
public class Phrolova extends Person {
    private boolean wasAtBottomStart = false;
    
    public Phrolova() {
        super("Phrolova");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Überprüfe, ob Phrolova zu Beginn der Runde ganz unten im Stapel liegt
        // (= es gibt Personen über mir UND keine Personen unter mir)
        if (this.personAbove != null && this.personsBelow.isEmpty()) {
            // Ganz unten -> +3 Felder zusätzlich
            this.wasAtBottomStart = true;
            return baseMovement + 3;
        }
        this.wasAtBottomStart = false;
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
