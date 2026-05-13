package wuwa.cheat.characters.abilities;

/**
 * Interface für Charaktere, die ihre Bewegung unter bestimmten Bedingungen modifizieren.
 * Beispiele: Phrolova (+3 wenn unten), Calcharo (+3 wenn auf Position 32)
 */
public interface MovementModifier {
    /**
     * Berechnet eine Bewegungs-Modifikation basierend auf Bedingungen
     * @return die zusätzliche Bewegungsdistanz (0 wenn keine Modifikation)
     */
    int getMovementBonus();
}
