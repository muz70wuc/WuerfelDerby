package wuwa.cheat.characters.original;

import java.util.Random;

import wuwa.cheat.game.WuerfelDerby;

/**
 * Denia: Wenn dieselbe Augenzahl wie beim letzten Wurf erzielt wird, +2 Felder.
 */
public class Denia extends Person {
    
    public Denia() {
        super("Denia");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        if (lastDiceRoll > 0 && baseMovement == lastDiceRoll) {
            System.out.println(name + " würfelt die gleiche Zahl: +2 Felder!");
            return baseMovement + 2;
        }
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Keine speziellen Effekte
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Lastroll wird zurückgesetzt bei Raumzeitriss
        lastDiceRoll = 0;
    }
}
