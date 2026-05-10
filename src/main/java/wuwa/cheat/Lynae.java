package wuwa.cheat;

public class Lynae extends Person {
    
    public Lynae() {
        super("Lynae");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Lynae: 60% Chance, sich mit doppelter Augenzahl zu bewegen
        //        20% Chance, sich nicht zu bewegen
        //        20% Chance, normal zu bewegen
        double chance = random.nextDouble();
        if (chance < 0.60) {
            return baseMovement * 2;  // 60% doppelte Bewegung
        } else if (chance < 0.80) {
            return 0;  // 20% keine Bewegung
        }
        return baseMovement;  // 20% normale Bewegung
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
