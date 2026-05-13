package wuwa.cheat.characters.original;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wuwa.cheat.game.WuerfelDerby;

/**
 * Sigrika: Nach jeder Runde werden bis zu zwei direkt vor ihr platzierte Würfel markiert
 * (außer erste Runde). Markierte Würfel bewegen sich 1 Feld weniger, aber min. 1 Feld.
 */
public class Sigrika extends Person {
    private List<Person> markedCharacters;
    private boolean hasMarkedThisRound;
    
    public Sigrika() {
        super("Sigrika");
        this.markedCharacters = new ArrayList<>();
        this.hasMarkedThisRound = false;
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        return baseMovement;  // Keine direkte Bewegungsmechanik
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        if (game.getRound() > 1 && !hasMarkedThisRound) {
            markDirectlyBelowCharacters(game);
            hasMarkedThisRound = true;
        }
    }
    
    private void markDirectlyBelowCharacters(WuerfelDerby game) {
        List<Person> directlyBelow = new ArrayList<>();
        
        // Sammle alle Charaktere auf diesem Feld
        List<Person> allCharacters = game.getAllCharacters();
        for (Person p : allCharacters) {
            if (p.getPosition() == this.position) {
                // Überprüfe, ob sie direkt unter/über mir sind im Stapel
                if (p.getPosition() == this.position && p != this) {
                    directlyBelow.add(p);
                }
            }
        }
        
        // Markiere bis zu 2 Charaktere
        int markCount = Math.min(2, directlyBelow.size());
        for (int i = 0; i < markCount; i++) {
            markedCharacters.add(directlyBelow.get(i));
            System.out.println(name + " markiert " + directlyBelow.get(i).getName());
        }
    }
    
    public boolean isMarked(Person character) {
        return markedCharacters.contains(character);
    }
    
    public void clearMarked() {
        markedCharacters.clear();
        hasMarkedThisRound = false;
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte
    }
}
