package wuwa.cheat.characters.original;

import java.util.Random;

import wuwa.cheat.game.WuerfelDerby;

/**
 * Phoebe: 50% Chance, ein zusätzliches Feld vorzurücken
 */
public class Phoebe extends Person {
    
    public Phoebe() {
        super("Phoebe");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        // 50% Chance für +1 Feld
        if (random.nextDouble() < 0.5) {
            return baseMovement + 1;
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
