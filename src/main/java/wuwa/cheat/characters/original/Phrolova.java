package wuwa.cheat.characters.original;

import wuwa.cheat.characters.abilities.MovementModifier;
import wuwa.cheat.characters.abilities.StackInteractor;
import wuwa.cheat.game.WuerfelDerby;

/**
 * Phrolova: Befindet sich der Würfel zu Beginn der Runde ganz unten im Stapel,
 * bewegt er sich zusätzlich 3 Felder vorwärts.
 * Implementiert MovementModifier und StackInteractor
 */
public class Phrolova extends Person implements MovementModifier, StackInteractor {
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
    
    @Override
    public int getMovementBonus() {
        // Überprüfe, ob Phrolova zu Beginn der Runde ganz unten im Stapel liegt
        if (this.personAbove != null && this.personsBelow.isEmpty()) {
            return 3;  // +3 Felder
        }
        return 0;
    }
    
    @Override
    public void performStackInteraction(WuerfelDerby game) {
        // Phrolova hat keine spezielle Stack-Interaktion wie Jinhsi
    }
}
