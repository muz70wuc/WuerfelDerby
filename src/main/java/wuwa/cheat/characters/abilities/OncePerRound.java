package wuwa.cheat.characters.abilities;

/**
 * Interface für Charaktere mit Fähigkeiten, die pro Runde zurückgesetzt werden.
 * Beispiel: Carthethyia (Fähigkeit kann einmal pro Runde genutzt werden)
 */
public interface OncePerRound {
    /**
     * Setzt die Fähigkeit zurück (für die nächste Runde)
     */
    void resetRoundAbility();
}
