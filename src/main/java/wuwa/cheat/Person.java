package wuwa.cheat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Person {
    protected String name;
    protected int position;  // 1-32 auf dem Spielfeld
    protected int verticalPosition;  // 0 = auf dem Feld, 1+ = auf anderen Personen
    protected Person personAbove;  // Person, die auf mir steht
    protected List<Person> personsBelow;  // Personen, auf denen ich stehe
    protected int lastDiceRoll;  // Letzter Würfelwurf
    protected boolean isAbbowser;  // Spezialfall Abbowser
    
    public Person(String name) {
        this.name = name;
        this.position = 1;  // Start bei Feld 1
        this.verticalPosition = 0;  // Standard: auf dem Feld
        this.personAbove = null;
        this.personsBelow = new ArrayList<>();
        this.lastDiceRoll = 0;
        this.isAbbowser = false;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int newPosition) {
        // Position zwischen 1 und 32 halten
        this.position = Math.max(1, Math.min(newPosition, 32));
    }
    
    public int getVerticalPosition() {
        return verticalPosition;
    }
    
    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = Math.max(0, verticalPosition);
    }
    
    /**
     * Setzt die Anfangsposition (Feld und vertikale Position)
     * @param fieldNumber Feldnummer (1-32)
     * @param verticalLevel Vertikale Position (0 = auf dem Feld, 1+ = auf anderen Personen)
     */
    public void setStartPosition(int fieldNumber, int verticalLevel) {
        this.position = Math.max(1, Math.min(fieldNumber, 32));
        this.verticalPosition = Math.max(0, verticalLevel);
    }
    
    public Person getPersonAbove() {
        return personAbove;
    }
    
    public void setPersonAbove(Person person) {
        this.personAbove = person;
    }
    
    public List<Person> getPersonsBelow() {
        return personsBelow;
    }
    
    public void addPersonBelow(Person person) {
        this.personsBelow.add(person);
        person.setPersonAbove(this);
    }
    
    public void removePersonBelow(Person person) {
        this.personsBelow.remove(person);
        person.setPersonAbove(null);
    }
    
    public int getLastDiceRoll() {
        return lastDiceRoll;
    }
    
    public void setLastDiceRoll(int roll) {
        this.lastDiceRoll = roll;
    }
    
    // Spezialfähigkeit: wird von jeder Unterklasse überschrieben
    public abstract int applySpecialAbility(int baseMovement, Random random, WuerfelDerby game);
    
    // Hook für Sigrika und andere: wird nach der Bewegung aufgerufen
    public abstract void onTurnEnd(WuerfelDerby game);
    
    // Hook wenn Raumzeitriss wird gelöst
    public abstract void onTimeRift(WuerfelDerby game);
    
    @Override
    public String toString() {
        String verticalInfo = verticalPosition > 0 ? " (Ebene " + verticalPosition + ")" : "";
        return "[" + name + " @ Feld " + position + verticalInfo + (personAbove != null ? " (unter " + personAbove.name + ")" : "") + "]";
    }
}
