package wuwa.cheat.characters.original;

import wuwa.cheat.characters.abilities.ActOrderModifier;
import wuwa.cheat.game.WuerfelDerby;

/**
 * Augusta: Befindet sich der Würfel zu Beginn der Runde ganz oben im Stapel,
 * setzt er in dieser Runde aus und handelt in der nächsten Runde zuletzt.
 * Implementiert ActOrderModifier
 */
public class Augusta extends Person implements ActOrderModifier {
    private boolean skippedThisRound = false;
    private boolean shouldActLast = false;
    
    public Augusta() {
        super("Augusta");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Überprüfe, ob Augusta zu Beginn der Runde ganz oben im Stapel liegt
        // (= keine Person über ihr)
        if (this.personAbove == null && this.shouldActLast) {
            // Sitzt aus diese Runde
            this.skippedThisRound = true;
            return 0;  // Keine Bewegung
        }
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Am Ende der Runde: vorbereiten für nächste Runde
        if (this.personAbove == null) {
            // War oben im Stapel -> handelt nächste Runde zuletzt
            this.shouldActLast = true;
        } else {
            this.shouldActLast = false;
        }
        
        this.skippedThisRound = false;
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte beim Raumzeitriss
    }
    
    public boolean shouldActLast() {
        return this.shouldActLast;
    }
    
    @Override
    public boolean shouldSkipTurn() {
        return this.skippedThisRound;
    }
}
