package wuwa.cheat;

import java.util.Random;

/**
 * Abbowser: Ab Runde 3 bewegt sich rückwärts (entgegengesetzt).
 * Startet am Zielfeld (32).
 * Würfelt 1-6, bleibt immer unten im Stapel.
 * Wenn allein am Ende der Runde: teleportiert sich zum Ziel.
 */
public class Abbowser extends Person {
    
    public Abbowser() {
        super("Abbowser");
        this.position = 32;  // Startet am Zielfeld
        this.isAbbowser = true;
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        // Abbowser hat keine speziellen Bewegungs-Boni
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Überprüfe, ob Abbowser allein ist
        if (isAlone(game)) {
            System.out.println(name + " ist allein - teleportiert sich zum Ziel!");
            this.position = 32;
        }
    }
    
    private boolean isAlone(WuerfelDerby game) {
        // Prüfe, ob noch andere Charaktere im Spiel sind (außer Abbowser)
        int nonAbbowserCount = 0;
        for (Person p : game.getAllCharacters()) {
            if (!p.isAbbowser) {
                nonAbbowserCount++;
            }
        }
        // Allein wenn nur noch Abbowser übrig ist oder wenn alle anderen sehr weit weg sind
        return nonAbbowserCount == 0 || Math.abs(this.position - 1) > 5;
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte
    }
}
