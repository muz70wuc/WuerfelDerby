package wuwa.cheat.characters.original;

import wuwa.cheat.characters.abilities.OncePerGame;
import wuwa.cheat.game.WuerfelDerby;

/**
 * Aemeath: Einmal pro Spiel - wenn die Mitte erreicht und ein anderer Würfel vor ihm ist.
 * Implementiert OncePerGame
 */
public class Aemeath extends Person implements OncePerGame {
    private boolean teleportationUsed = false;  // Track, ob die Teleportation bereits verwendet wurde
    
    public Aemeath() {
        super("Aemeath");
    }
    
    @Override
    public int applySpecialAbility(int baseMovement, java.util.Random random, WuerfelDerby game) {
        return baseMovement;
    }
    
    @Override
    public void onTurnEnd(WuerfelDerby game) {
        // Aemeath: Einmal pro Spiel - wenn die Mitte erreicht und ein anderer Würfel vor ihm ist
        if (!teleportationUsed && this.position >= 16) {
            Person nearestWdyInFront = game.getNearestPersonInFront(this);
            if (nearestWdyInFront != null && !nearestWdyInFront.getName().equals("Abbowser")) {
                // Teleportiere auf die Spitze des nächstgelegenen Würfels
                this.position = nearestWdyInFront.getPosition();
                this.verticalPosition = nearestWdyInFront.getVerticalPosition() + 1;
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
