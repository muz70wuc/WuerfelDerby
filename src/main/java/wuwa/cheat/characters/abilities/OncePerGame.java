package wuwa.cheat.characters.abilities;

/**
 * Interface für Charaktere, die eine Fähigkeit nur einmal pro Spiel nutzen können.
 * Beispiele: Aemeath (Teleportation), Iuno (Mass-Teleportation)
 */
public interface OncePerGame {
    /**
     * Überprüft, ob die Fähigkeit bereits genutzt wurde
     * @return true wenn die Fähigkeit bereits genutzt wurde
     */
    boolean hasAbilityBeenUsed();
    
    /**
     * Markiert die Fähigkeit als genutzt
     */
    void markAbilityAsUsed();
}
