package wuwa.cheat.characters.abilities;

/**
 * Interface für Charaktere, die die Zugreihenfolge beeinflussen können.
 * Beispiele: Augusta (sitzt aus, handelt zuletzt), Changli (handelt zuletzt)
 */
public interface ActOrderModifier {
    /**
     * Überprüft, ob der Charakter in dieser Runde zuletzt agieren soll
     * @return true wenn der Charakter zuletzt agieren soll
     */
    boolean shouldActLast();
    
    /**
     * Überprüft, ob der Charakter in dieser Runde aussetzen soll
     * @return true wenn der Charakter aussetzen soll
     */
    boolean shouldSkipTurn();
}
