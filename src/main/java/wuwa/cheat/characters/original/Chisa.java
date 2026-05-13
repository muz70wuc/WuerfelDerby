package wuwa.cheat.characters.original;

import wuwa.cheat.game.WuerfelDerby;

public class Chisa extends Person {
    
    public Chisa() {
        super("Chisa");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Chisa: Wenn das Würfelergebnis das kleinste dieser Runde ist, +2 Felder
        int chisaRoll = this.lastDiceRoll;
        boolean isSmallest = game.isSmallestRollThisRound(chisaRoll, this);
        if (isSmallest) {
            this.position = Math.min(this.position + 2, 32);
        }
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte beim Raumzeitriss
    }
    
}
