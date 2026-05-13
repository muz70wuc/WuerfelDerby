package wuwa.cheat.characters.abilities;

import wuwa.cheat.game.WuerfelDerby;

/**
 * Interface für Charaktere, die mit anderen Charakteren im Stack interagieren können.
 * Beispiele: Jinhsi (Position wechseln), Phrolova (Stack-abhängige Bewegung)
 */
public interface StackInteractor {
    /**
     * Führt eine Stack-basierte Manipulation durch
     * @param game das Spiel
     */
    void performStackInteraction(WuerfelDerby game);
}
