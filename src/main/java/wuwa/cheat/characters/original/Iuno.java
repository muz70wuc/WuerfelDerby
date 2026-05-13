package wuwa.cheat.characters.original;

import java.util.ArrayList;
import java.util.List;

import wuwa.cheat.characters.abilities.OncePerGame;
import wuwa.cheat.game.WuerfelDerby;

/**
 * Iuno: Einmal pro Spiel - wenn der Würfel die Streckenmitte passiert und sich andere Würfel
 * (mit Ausnahme des Abbowser-Würfels) sowohl vor als auch hinter ihm befinden,
 * werden diese auf sein Feld teleportiert. Die Stapelreihenfolge bleibt wie die Platzierung vor dem Teleport.
 * Implementiert OncePerGame
 */
public class Iuno extends Person implements OncePerGame {
    private boolean teleportationUsed = false;
    
    public Iuno() {
        super("Iuno");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Iuno: Einmal pro Spiel - wenn die Mitte (Feld 16) erreicht und andere Würfel vor und hinter
        if (!teleportationUsed && this.position >= 16) {
            List<Person> personsBefore = new ArrayList<>();
            List<Person> personsAfter = new ArrayList<>();
            
            // Sammle alle Würfel vor und hinter Iuno
            for (Person p : game.getAllCharacters()) {
                if (p.equals(this) || p.isAbbowser) continue;  // Iuno selbst und Abbowser ausschließen
                
                if (p.getPosition() < this.position) {
                    personsBefore.add(p);
                } else if (p.getPosition() > this.position) {
                    personsAfter.add(p);
                }
            }
            
            // Wenn es Würfel vor UND hinter Iuno gibt: teleportiere alle auf sein Feld
            if (!personsBefore.isEmpty() && !personsAfter.isEmpty()) {
                for (Person p : personsBefore) {
                    p.setPosition(this.position);
                    // Stapelreihenfolge wird durch verticalLevel bewahrt
                }
                for (Person p : personsAfter) {
                    p.setPosition(this.position);
                    // Stapelreihenfolge wird durch verticalLevel bewahrt
                }
                teleportationUsed = true;
            }
        }
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte beim Raumzeitriss
    }
    
    @Override
    public boolean hasAbilityBeenUsed() {
        return teleportationUsed;
    }
    
    @Override
    public void markAbilityAsUsed() {
        this.teleportationUsed = true;
    }
}
