package wuwa.cheat.characters.original;

import wuwa.cheat.characters.abilities.StackInteractor;
import wuwa.cheat.game.WuerfelDerby;

/**
 * Jinhsi: Wenn ein anderer Würfel darauf liegt (auf Jinhsi), besteht eine 40%ige Chance,
 * die oberste Position zu erreichen.
 * Implementiert StackInteractor
 */
public class Jinhsi extends Person implements StackInteractor {
    
    public Jinhsi() {
        super("Jinhsi");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Überprüfe, ob ein anderer Würfel auf Jinhsi liegt (personAbove ist nicht null)
        if (this.personAbove != null) {
            // 40% Chance, die oberste Position zu erreichen
            if (this.random.nextDouble() < 0.40) {
                // Tausche Position mit dem Würfel über mir
                // Vertausche verticalPositionen
                int myVertical = this.verticalPosition;
                int aboveVertical = this.personAbove.getVerticalPosition();
                
                this.setVerticalPosition(aboveVertical);
                this.personAbove.setVerticalPosition(myVertical);
                
                // Tausche auch die Referenzen im Stack
                Person tempAbove = this.personAbove;
                Person tempBelow = this.personAbove.getPersonAbove();
                
                this.setPersonAbove(tempBelow);
                tempAbove.setPersonAbove(this);
            }
        }
    }
    
    @Override
    public void onTimeRift(WuerfelDerby game) {
        // Keine speziellen Effekte beim Raumzeitriss
    }
    
    @Override
    public void performStackInteraction(WuerfelDerby game) {
        // Führe die Stack-Interaktion durch
        if (this.personAbove != null) {
            // 40% Chance, die oberste Position zu erreichen
            if (this.random.nextDouble() < 0.40) {
                // Tausche Position mit dem Würfel über mir
                // Vertausche verticalPositionen
                int myVertical = this.verticalPosition;
                int aboveVertical = this.personAbove.getVerticalPosition();
                
                this.setVerticalPosition(aboveVertical);
                this.personAbove.setVerticalPosition(myVertical);
                
                // Tausche auch die Referenzen im Stack
                Person tempAbove = this.personAbove;
                Person tempBelow = this.personAbove.getPersonAbove();
                
                this.setPersonAbove(tempBelow);
                tempAbove.setPersonAbove(this);
            }
        }
    }
}
