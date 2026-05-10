package wuwa.cheat;

public class Carlotta extends Person {
    
    public Carlotta() {
        super("Carlotta");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Carlotta: 28% Chance, mit der gewürfelten Zahl zweimal vorzurücken
        if (random.nextDouble() < 0.28) {
            return baseMovement * 2;
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
