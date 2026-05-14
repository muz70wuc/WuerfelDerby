package wuwa.cheat.game;

import java.util.*;
import wuwa.cheat.characters.*;
import wuwa.cheat.characters.original.Abbowser;
import wuwa.cheat.characters.original.Carthethyia;
import wuwa.cheat.characters.original.LuukHerssen;
import wuwa.cheat.characters.original.Person;
import wuwa.cheat.characters.original.Sigrika;
import wuwa.cheat.board.Position;
import wuwa.cheat.board.ProbabilityDice;

/**
 * Würfelderby: Ein Rennspiel mit 32 Feldern und 7 verschiedenen Charakteren mit speziellen Fähigkeiten.
 * 
 * Feldtypen:
 * - Feld 3: Vorschubmechanismus (+1)
 * - Feld 8: Hemmmechanismus (-1)
 * - Feld 15: Raumzeitriss (neu gestapelt)
 * - Feld 23: Vorschubmechanismus (+1)
 * - Feld 30: Raumzeitriss (neu gestapelt)
 * - etc.
 */
public class WuerfelDerby {
    private List<Person> allCharacters;
    private List<Person> turnOrder;
    private Map<Integer, Position> board;  // Feld-Nummern zu Positionen
    private Random random;
    private ProbabilityDice dice;
    private int round;
    private Abbowser abbowser;
    private Person winner;
    private static final int BOARD_SIZE = 32;
    private static final int GOAL = 32;
    
    public WuerfelDerby(long seed) {
        this.allCharacters = new ArrayList<>();
        this.turnOrder = new ArrayList<>();
        this.board = new HashMap<>();
        this.random = new Random(seed);
        this.dice = new ProbabilityDice(random);
        this.round = 0;
        this.winner = null;
        initializeBoard();
    }
    
    private void initializeBoard() {
        // Definiere die Spezialfelder
        // Vorschubmechanismus: 3, 11, 16, 23
        // Hemmmechanismus: 10, 28
        // Raumzeitriss: 6, 20
        int[] specialFields = {3, 11, 16, 23, 10, 28, 6, 20};
        Position.FieldProperty[] properties = {
            Position.FieldProperty.VORSCHUBMECHANISMUS,
            Position.FieldProperty.VORSCHUBMECHANISMUS,
            Position.FieldProperty.VORSCHUBMECHANISMUS,
            Position.FieldProperty.VORSCHUBMECHANISMUS,
            Position.FieldProperty.HEMMMECHANISMUS,
            Position.FieldProperty.HEMMMECHANISMUS,
            Position.FieldProperty.RAUMZEITRISS,
            Position.FieldProperty.RAUMZEITRISS
        };
        
        for (int i = 1; i <= BOARD_SIZE; i++) {
            Position.FieldProperty prop = Position.FieldProperty.NORMAL;
            for (int j = 0; j < specialFields.length; j++) {
                if (specialFields[j] == i) {
                    prop = properties[j];
                    break;
                }
            }
            board.put(i, new Position(i, prop));
        }
    }
    
    public void addCharacter(Person person) {
        allCharacters.add(person);
        person.setRandom(random);
        if (person instanceof Abbowser) {
            this.abbowser = (Abbowser) person;
        }
    }
    
    /**
     * Fügt einen Charakter am Startfeld (Feld 1) mit zufälligem verticalLevel hinzu
     * @param person Der hinzuzufügende Charakter
     * @param randomizeVerticalLevel true = zufälliger Stack-Platz, false = auf dem Boden (Level 0)
     */
    public void addCharacterAtStart(Person person, boolean randomizeVerticalLevel) {
        allCharacters.add(person);
        person.setRandom(random);
        
        if (randomizeVerticalLevel) {
            // Zufälliger verticalLevel (0 bis Anzahl der Charaktere - 1)
            // Bei 7 Charakteren: Level 0-6 (der unterste trägt alle oben)
            int randomLevel = random.nextInt(allCharacters.size());
            person.setStartPosition(1, randomLevel);
        } else {
            person.setStartPosition(1, 0);
        }
        
        if (person instanceof Abbowser) {
            this.abbowser = (Abbowser) person;
        }
    }
    
    /**
     * Fügt einen Charakter mit spezifischer Anfangsposition hinzu
     * @param person Der hinzuzufügende Charakter
     * @param fieldNumber Feldnummer (1-32) - Startfeld
     * @param verticalLevel Vertikale Position im Stack (0 = auf dem Feld, 1+ = auf anderen Charakteren)
     *                      Kann bis zu (Anzahl der Charaktere - 1) sein
     */
    public void addCharacter(Person person, int fieldNumber, int verticalLevel) {
        allCharacters.add(person);
        person.setRandom(random);
        
        // Exakte Position setzen
        person.setStartPosition(fieldNumber, verticalLevel);
        
        if (person instanceof Abbowser) {
            this.abbowser = (Abbowser) person;
        }
    }
    
    /**
     * Organisiert den Stack auf einem Feld
     * Alle Charaktere auf dem gleichen Feld werden gestapelt nach ihrer vertikalen Position
     */
    public void organizeStack() {
        // Gruppiere Charaktere nach Feldnummer
        Map<Integer, List<Person>> fieldMap = new HashMap<>();
        for (Person p : allCharacters) {
            fieldMap.computeIfAbsent(p.getPosition(), k -> new ArrayList<>()).add(p);
        }
        
        // Für jedes Feld: sortiere nach vertikaler Position und stacke
        for (List<Person> charactersOnField : fieldMap.values()) {
            if (charactersOnField.size() <= 1) continue;
            
            // Sortiere nach vertikaler Position
            charactersOnField.sort((a, b) -> Integer.compare(a.getVerticalPosition(), b.getVerticalPosition()));
            
            // Clearer erst alle Verbindungen
            for (Person p : charactersOnField) {
                p.setPersonAbove(null);
                p.getPersonsBelow().clear();
            }
            
            // Baue den Stack auf
            for (int i = 0; i < charactersOnField.size() - 1; i++) {
                charactersOnField.get(i).addPersonBelow(charactersOnField.get(i + 1));
            }
        }
    }
    
    public void startRound() {
        round++;
        System.out.println("\n========== RUNDE " + round + " ==========");
        
        if (round == 1) {
            // Erste Runde: nur Spielreihenfolge bestimmen
            randomizePlayerOrder();
            System.out.println("Spielreihenfolge wird bestimmt...");
            for (Person p : turnOrder) {
                int roll = dice.roll();
                p.setLastDiceRoll(roll);
                System.out.println(p.getName() + " würfelt: " + roll);
            }
        } else {
            // Normale Runde: Würfeln und Bewegung
            randomizePlayerOrder();
            for (Person player : turnOrder) {
                playTurn(player);
            }
            
            // Am Ende der Runde: Sigrika markiert, Carthethyia Reset
            for (Person p : allCharacters) {
                p.onTurnEnd(this);
                if (p instanceof Carthethyia) {
                    ((Carthethyia) p).resetAbility();
                }
            }
        }
    }
    
    /**
     * Führt das Spiel bis zum Gewinn aus
     * @return Der Gewinner
     */
    public Person playToCompletion() {
        // Erste Runde: Spielreihenfolge bestimmen
        randomizePlayerOrder();
        for (Person p : turnOrder) {
            int roll = dice.roll();
            p.setLastDiceRoll(roll);
        }
        round = 1;
        
        // Weitere Runden bis jemand gewinnt
        round = 2;
        while (winner == null) {
            playRound();
        }
        
        return winner;
    }
    
    private void playRound() {
        randomizePlayerOrder();
        
        for (Person player : turnOrder) {
            if (winner != null) break;  // Spiel beendet
            
            if (player instanceof Abbowser && round < 3) {
                continue;  // Abbowser spielt vor Runde 3 nicht
            }
            
            playTurn(player);
        }
        
        // Am Ende der Runde: Sigrika markiert, Carthethyia Reset
        for (Person p : allCharacters) {
            p.onTurnEnd(this);
            if (p instanceof Carthethyia) {
                ((Carthethyia) p).resetAbility();
            }
        }
        
        round++;
    }
    
    private void playTurn(Person player) {
        if (player instanceof Abbowser && round < 3) {
            return;  // Abbowser spielt vor Runde 3 nicht
        }
        
        int roll = dice.roll();
        player.setLastDiceRoll(roll);
        
        // Überprüfe Sigrika-Markierung
        int finalMovement = roll;
        for (Person p : allCharacters) {
            if (p instanceof Sigrika && ((Sigrika) p).isMarked(player)) {
                finalMovement = Math.max(1, finalMovement - 1);
            }
        }
        
        // Speziale Fähigkeiten anwenden
        finalMovement = player.applySpecialAbility(finalMovement, random, this);
        
        // Bewegung durchführen
        moveCharacter(player, finalMovement);
    }
    
    private void moveCharacter(Person person, int steps) {
        int newPosition;
        
        if (person instanceof Abbowser && round >= 3) {
            // Abbowser bewegt sich rückwärts
            newPosition = person.getPosition() - steps;
            newPosition = Math.max(1, newPosition);
        } else {
            // Normal vorwärts
            newPosition = person.getPosition() + steps;
        }
        
        person.setPosition(newPosition);
        
        // Feld-Effekte anwenden
        applyFieldEffect(person);
        
        // Überprüfe Gewinn
        if (newPosition >= GOAL && !(person instanceof Abbowser)) {
            this.winner = person;
        }
    }
    
    private void applyFieldEffect(Person person) {
        Position field = board.get(person.getPosition());
        if (field == null) return;
        
        Position.FieldProperty property = field.getFieldProperty();
        
        switch (property) {
            case VORSCHUBMECHANISMUS:
                person.setPosition(person.getPosition() + 1);
                if (person instanceof LuukHerssen) {
                    ((LuukHerssen) person).applyFieldEffect(property);
                }
                break;
                
            case HEMMMECHANISMUS:
                person.setPosition(Math.max(1, person.getPosition() - 1));
                if (person instanceof LuukHerssen) {
                    ((LuukHerssen) person).applyFieldEffect(property);
                }
                break;
                
            case RAUMZEITRISS:
                person.onTimeRift(this);
                break;
                
            default:
                break;
        }
    }
    
    private void randomizePlayerOrder() {
        turnOrder = new ArrayList<>(allCharacters);
        Collections.shuffle(turnOrder);
    }
    
    public int getRound() {
        return round;
    }
    
    public Abbowser getAbbowser() {
        return abbowser;
    }
    
    public List<Person> getAllCharacters() {
        return allCharacters;
    }
    
    public Person getWinner() {
        return winner;
    }
    
    public int getWinnerPosition() {
        return winner != null ? winner.getPosition() : 0;
    }
    
    public void resetGame() {
        for (Person p : allCharacters) {
            p.setPosition(1);
            p.setVerticalPosition(0);
            p.setLastDiceRoll(0);
            p.getPersonsBelow().clear();
            p.setPersonAbove(null);
        }
        // Abbowser reset
        if (abbowser != null) {
            abbowser.setPosition(32);
        }
        round = 0;
        winner = null;
    }
    
    public void printGameState() {
        System.out.println("\n=== Spielzustand ===");
        for (Person person : allCharacters) {
            System.out.println(person);
        }
    }
    
    /**
     * Findet den nächstgelegenen Würfel vor einem gegebenen Würfel
     * @param person Der Würfel, dessen Vordermann gesucht wird
     * @return Der nächstgelegene Würfel vor der Person, oder null wenn keiner vorhanden
     */
    public Person getNearestPersonInFront(Person person) {
        Person nearest = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Person p : allCharacters) {
            if (p.equals(person)) continue;
            if (p.getPosition() <= person.getPosition()) continue;  // Nur die, die weiter vorne sind
            
            int distance = p.getPosition() - person.getPosition();
            if (distance < minDistance) {
                minDistance = distance;
                nearest = p;
            }
        }
        
        return nearest;
    }
    
    /**
     * Überprüft, ob ein Würfelergebnis das kleinste dieser Runde ist
     * @param roll Das zu überprüfende Würfelergebnis
     * @param person Die Person, die das Ergebnis würfelte
     * @return true wenn dieser Roll das kleinste dieser Runde ist
     */
    public boolean isSmallestRollThisRound(int roll, Person person) {
        for (Person p : allCharacters) {
            if (p.equals(person)) continue;
            if (p.getLastDiceRoll() < roll) {
                return false;  // Es gibt einen kleineren Roll
            }
        }
        // Überprüfe, dass nicht alle den gleichen Roll haben (Tie-Breaker)
        for (Person p : allCharacters) {
            if (p.equals(person)) continue;
            if (p.getLastDiceRoll() < roll) {
                return false;
            }
        }
        return true;
    }
}
