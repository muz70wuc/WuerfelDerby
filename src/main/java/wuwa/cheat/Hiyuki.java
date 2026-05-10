package wuwa.cheat;

import java.util.Random;

/**
 * Hiyuki: Trifft dieser Würfel auf Abbowser, bewegt er sich bei jeder weiteren
 * Bewegung zusätzlich 1 Feld vorwärts.
 */
public class Hiyuki extends Person {
    private boolean hasMetAbbowser;
    
    public Hiyuki() {
        super("Hiyuki");
        this.hasMetAbbowser = false;
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game) {
        if (hasMetAbbowser) {
            System.out.println(name + " erhält +1 Feld durch Abbowser-Effekt!");
            return baseMovement + 1;
        }
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Überprüfe, ob ich auf dem gleichen Feld wie Abbowser bin
        Person abbowser = game.getAbbowser();
        if (abbowser != null && this.position == abbowser.getPosition() && !hasMetAbbowser) {
            hasMetAbbowser = true;
            System.out.println(name + " trifft Abbowser - erhält permanenten Bonus!");
        }
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte
    }
}
