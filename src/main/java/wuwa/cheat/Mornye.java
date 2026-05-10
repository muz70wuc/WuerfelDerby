package wuwa.cheat;

public class Mornye extends Person {
    private int sequenceIndex = 0;  // Track die Position in der Würfelsequenz
    private static final int[] SEQUENCE = {3, 2, 1};  // Feste Abfolge: 3, 2, 1, 3, 2, 1, ...
    
    public Mornye() {
        super("Mornye");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        // Mornye: Feste Abfolge von 3, 2, 1, 3, 2, 1, ...
        int result = SEQUENCE[sequenceIndex % SEQUENCE.length];
        sequenceIndex++;
        return result;
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
