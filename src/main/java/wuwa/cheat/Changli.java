package wuwa.cheat;

/**
 * Changli: Wenn ein anderer Würfel darunten liegt (auf Changli), besteht eine 65% Chance,
 * sich im nächsten Zug zuletzt zu bewegen.
 */
public class Changli extends Person {
    private boolean shouldActLast = false;
    
    public Changli() {
        super("Changli");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Überprüfe, ob ein anderer Würfel auf Changli liegt (personsBelow ist nicht leer)
        if (!this.personsBelow.isEmpty()) {
            // 65% Chance, dass Changli zuletzt agiert
            if (this.random.nextDouble() < 0.65) {
                this.shouldActLast = true;
            } else {
                this.shouldActLast = false;
            }
        } else {
            this.shouldActLast = false;
        }
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte beim Raumzeitriss
    }
    
    public boolean shouldActLast() {
        return this.shouldActLast;
    }
}
