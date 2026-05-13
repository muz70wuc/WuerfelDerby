package wuwa.cheat.characters.original;

import wuwa.cheat.game.WuerfelDerby;

public class Shorekeeper extends Person {
    
    public Shorekeeper() {
        super("Shorekeeper");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Shorekeeper: Die Augenzahl ergibt immer 2 oder 3
        return random.nextBoolean() ? 2 : 3;
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
