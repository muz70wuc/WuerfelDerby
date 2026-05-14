# 🎲 Würfelderby - Ein Rennspiel der besonderen Art

Ein Java-basiertes Simulationsspiel, bei dem verschiedene Charaktere mit speziellen Fähigkeiten um die Wette laufen. Das Spiel simuliert Tausende von Runden, um die Gewinnwahrscheinlichkeiten zu berechnen. Die Anzahl der Spieler kann frei konfiguriert werden, und neue Charaktere lassen sich leicht durch neue Klassen hinzufügen.

---

## 📋 Inhaltsverzeichnis

- [Spielkonzept](#spielkonzept)
- [Charaktere & Fähigkeiten](#charaktere--fähigkeiten)
- [Spielmechaniken](#spielmechaniken)
- [Feldtypen](#feldtypen)
- [Installation & Ausführung](#installation--ausführung)
- [Verwendung](#verwendung)
- [Anfangspositionen](#anfangspositionen)
- [Ausgabe & Statistiken](#ausgabe--statistiken)

---

## Spielkonzept

Das Würfelderby ist ein Rennspiel über ein 32-Feld-Spielbrett, bei dem:

- **Beliebig viele Charaktere** gleichzeitig laufen (Anzahl pro Runde konfigurierbar)
- Jeder Charakter eine **einzigartige Spezialfähigkeit** hat
- Die Charaktere sich gegenseitig **stapeln** können (Stack-Mechanik)
- Zufallselemente (Würfelwürfe) das Spiel beeinflussen
- Das erste Nicht-Abbowser-Charakter-Ziel am Feld 32 gewinnt
- Das Spiel **mehrfach simuliert** wird, um Statistiken zu sammeln

---

## Charaktere & Fähigkeiten

### Neue Charaktere hinzufügen

Um neue Charaktere zum Spiel hinzuzufügen, erstelle eine neue Klasse in `src/main/java/wuwa/cheat/characters/original/`, die von `Person` erbt:

```java
public class MeinCharakter extends Person {
    public MeinCharakter() {
        super("Mein Charakter", "Beschreibung");
    }
    
    @Override
    public void doSpecialAbility(WuerfelDerby game) {
        // Spezialfähigkeit implementieren
    }
}
```

Danach kann der Charakter in `Main.java` zur `characterNames`-Liste hinzugefügt werden.

### Vorhandene Charaktere (7 Charaktere)

#### 🎯 Abbowser (der Antagonist)
- **Besonderheit**: Bewegt sich rückwärts (ab Runde 3!)
- **Start**: Feld 32 (das Ziel)
- **Spezial**: Kann sich teleportieren, wenn allein
- **Rolle**: Verhindert, dass andere einfach vorbeigehen
- **Stack-Verhalten**: Trägt wie alle anderen beim Stapeln

#### 🌙 Chisa
- **Fähigkeit**: +2 Felder, wenn die kleinste Würfelzahl der Runde gewürfelt wird
- **Strategie**: Profit aus der Schwäche wird zur Stärke

#### 🎲 Lynae
- **Fähigkeit**: Unterschiedliche Chancen pro Zug:
  - 60% Chance für doppelte Bewegung
  - 20% Chance für keine Bewegung
  - 20% Chance für normale Bewegung
- **Strategie**: Hochriskant mit großem Upside-Potenzial

#### 🌊 Shorekeeper
- **Fähigkeit**: Würfelt immer 2 oder 3 (50/50 Chance)
- **Strategie**: Zuverlässig, mittelmäßig, stabil

#### 📖 Aemeath
- **Fähigkeit**: Einmal pro Spiel - Teleportation auf den Stapel des nächsten Charakters
- **Bedingung**: Ab Feldmitte (Feld 16+) und wenn ein anderer Charakter vor ihm ist
- **Strategie**: Taktischer Sprung nach vorne in der Spielmitte

#### 🎪 Carlotta
- **Fähigkeit**: 28% Chance, die Bewegung zu verdoppeln
- **Strategie**: Moderate Boosts mit guter Erfolgsrate

#### 🔢 Mornye
- **Fähigkeit**: Feste Bewegungsabfolge (3 → 2 → 1 → 3 → 2 → 1 ...)
- **Strategie**: Vorhersehbar und konsistent, nicht vom Glück abhängig

---

## Spielmechaniken

### 1. **Rundenablauf**
```
Runde 1: Spielreihenfolge bestimmen (Jeder würfelt einmal)
Runde 2+: Normale Runden
  - Zufällige Spielreihenfolge pro Runde
  - Jeder würfelt (1-6)
  - Sigrika markiert (ab Runde 2)
  - Spezialfähigkeiten anwenden
  - Bewegung durchführen
  - Feldeffekte anwenden
  - Carthethyia-Fähigkeit reset
```

### 2. **Stack-Mechanik (Stapeln)**
- Mehrere Charaktere können auf dem gleichen Feld sein
- Sie werden vertikal übereinander gestapelt
- **verticalLevel**:
  - `0` = auf dem Boden (trägt alle anderen mit!)
  - `1` = auf einem anderen Charakter
  - `2+` = auf mehreren Charakteren
  - Bei 7 Charakteren: Level kann 0 bis 6 sein
- Wichtig: **Der unten stehende (Level 0) trägt alle oben stehenden mit!**
- Wenn Level 0 vorwärts geht, gehen Level 1, 2, 3... automatisch mit

### 3. **Siegbedingung**
- Erster Charakter (außer Abbowser) auf Feld 32 = **Gewinner**
- Abbowser kann nicht gewinnen (bewegt sich rückwärts)

---

## Feldtypen

Das 32-Feld-Spielbrett hat 8 Spezialfelder:

| Feld | Typ | Effekt |
|------|-----|--------|
| 3 | Vorschubmechanismus | +1 Feld (Luuk Herssen: +2) |
| 6 | Raumzeitriss | Charakter wird neu gestapelt |
| 10 | Hemmmechanismus | -1 Feld |
| 11 | Vorschubmechanismus | +1 Feld (Luuk Herssen: +2) |
| 16 | Vorschubmechanismus | +1 Feld (Luuk Herssen: +2) |
| 20 | Raumzeitriss | Charakter wird neu gestapelt |
| 23 | Vorschubmechanismus | +1 Feld (Luuk Herssen: +2) |
| 28 | Hemmmechanismus | -1 Feld |

### Raumzeitriss (Feld 6, 20)
- Auslöser für Neustapelung
- Charakter wird aus dem aktuellen Stack genommen
- Neu gestapelt je nach `onTimeRift()`-Implementierung

---

## Installation & Ausführung

### Voraussetzungen
- Java 21 oder höher
- Gradle (oder `gradlew`)

### Kompilieren
```bash
# Windows
gradlew build

# Linux/Mac
./gradlew build
```

### Ausführen
```bash
# Windows
gradlew run

# Linux/Mac
./gradlew run
```

### Direktes Ausführen der Main-Klasse
```bash
java -cp build/classes/java/main wuwa.cheat.Main
```

---

## Verwendung

### Basis-Simulation (OPTION 1: Zufällige Anfangspositionen) - Standard

Dies ist die Standardkonfiguration in `Main.java`:

```java
String[] characterNames = {"Abbowser", "Chisa", "Lynae", "Shorekeeper", "Aemeath", "Carlotta","Mornye"};
WuerfelDerby game = new WuerfelDerby(seed);

// Charaktere am Startfeld (Feld 1) mit zufälligem Stack-Platz (Level 0 bis 6)
// Der unterste Charakter (Level 0) trägt alle anderen!
for (String name : characterNames) {
    Class<?> clazz = Class.forName("wuwa.cheat.characters.original." + name);
    Person character = (Person) clazz.getDeclaredConstructor().newInstance();
    game.addCharacterAtStart(character, true);
}

game.organizeStack();
Person winner = game.playToCompletion();
```

### OPTION 2: Spezifische Positionen mit Reflection

Um Option 2 zu nutzen, kommentieren Sie die Schleife in Option 1 aus und aktivieren Sie:

```java
WuerfelDerby game = new WuerfelDerby(seed);

try {
    // Beispiel: Alle 7 Charaktere auf Feld 1, gestapelt (Level 0-6)
    for (int i = 0; i < characterNames.length; i++) {
        game.addCharacter(
            (Person) Class.forName(basePackage + characterNames[i])
            .getDeclaredConstructor()
            .newInstance(), 1, i);  // Feld 1, Level 0-6
    }
} catch (Exception e) {
    e.printStackTrace();
}

game.organizeStack();
Person winner = game.playToCompletion();
```

### OPTION 3: Direkte Objektinstanziierung

Um Option 3 zu nutzen, kommentieren Sie die Schleife in Option 1 aus und aktivieren Sie:

```java
WuerfelDerby game = new WuerfelDerby(seed);

// Beispiel: Alle 7 Charaktere auf Feld 1, Level 0-6 gestapelt
game.addCharacter(new Abbowser(), 1, 0);       // Feld 1, Level 0 (auf dem Boden - trägt alle anderen!)
game.addCharacter(new Chisa(), 1, 1);         // Feld 1, Level 1 (auf Abbowser)
game.addCharacter(new Lynae(), 1, 2);         // Feld 1, Level 2
game.addCharacter(new Shorekeeper(), 1, 3);   // Feld 1, Level 3
game.addCharacter(new Aemeath(), 1, 4);       // Feld 1, Level 4
game.addCharacter(new Carlotta(), 1, 5);      // Feld 1, Level 5
game.addCharacter(new Mornye(), 1, 6);        // Feld 1, Level 6 (ganz oben)

game.organizeStack();
Person winner = game.playToCompletion();
```

### Spezifische Anfangspositionen

```java
WuerfelDerby game = new WuerfelDerby(seed);

// Charaktere auf verschiedenen Feldern und Levels
game.addCharacter(new Abbowser(), 1, 0);      // Feld 1, auf dem Boden
game.addCharacter(new Chisa(), 1, 1);         // Feld 1, auf Abbowser (Stapel!)
game.addCharacter(new Lynae(), 2, 0);         // Feld 2, auf dem Boden
game.addCharacter(new Shorekeeper(), 5, 0);   // Feld 5, auf dem Boden

game.organizeStack();
Person winner = game.playToCompletion();
```

### Individuelle Positionen ändern

```java
Person chisa = new Chisa();
game.addCharacter(chisa);

// Position ändern (z.B. nach der Initialisierung)
chisa.setPosition(5);           // Auf Feld 5 setzen
chisa.setVerticalPosition(2);   // Ebene 2 im Stack
```

---

## Anfangspositionen

### addCharacterAtStart(Person, boolean randomizeVerticalLevel)
- `randomizeVerticalLevel = true`: Zufälliger Stack-Platz (Ebene 0 bis Anzahl Charaktere - 1)
  - Bei 7 Charakteren: Level 0-6
- `randomizeVerticalLevel = false`: Immer auf dem Boden (Ebene 0)

```java
// Alle auf Feld 1, zufälliger Stapel
game.addCharacterAtStart(new Abbowser(), true);
game.addCharacterAtStart(new Chisa(), true);

// Alle auf Feld 1, auf dem Boden
game.addCharacterAtStart(new Lynae(), false);
```

### addCharacter(Person, int fieldNumber, int verticalLevel)
- **fieldNumber**: 1-32
- **verticalLevel**: 0 = Boden (trägt alle oben), 1+ = gestapelt auf anderen
  - Bei 7 Charakteren: Level kann 0 bis 6 sein

```java
game.addCharacter(new Abbowser(), 1, 0);   // Feld 1, Level 0 (trägt alle!)
game.addCharacter(new Chisa(), 1, 1);      // Feld 1, Level 1 (auf Abbowser)
game.addCharacter(new Lynae(), 10, 0);     // Feld 10, Level 0
```

---

## Ausgabe & Statistiken

Nach der Simulation erhältst du drei Statistik-Tabellen:

### 1. **Gewinnchancen (Wahrscheinlichkeits-Ranking)**
```
Phoebe: 18.5% (185 Siege)
Sigrika: 16.2% (162 Siege)
Hiyuki: 15.8% (158 Siege)
...
```

### 2. **Durchschnittliche Positionen**
```
Hiyuki: 18.45 Felder
Phoebe: 17.23 Felder
...
```

### 3. **Stack-Information**
```
Charakter der am weitesten vorankommt trägt andere 'gratis' mit:
Geführt von: Hiyuki (auf Feld ~18.45 im Schnitt)
```

---

## 🔧 Konfiguration

### Simulationen erhöhen
In `Main.java`:
```java
private static final int SIMULATION_COUNT = 1000;  // Change this value
```

### Feldtypen anpassen
In `WuerfelDerby.java` - `initializeBoard()` Methode:
```java
int[] specialFields = {3, 8, 15, 23, 30, 5, 18, 27};
Position.FieldProperty[] properties = {
    Position.FieldProperty.VORSCHUBMECHANISMUS,
    // ... weitere Feldtypen
};
```

---

## 🐛 Debugging

### Spielzustand ausdrucken
```java
game.printGameState();  // Zeigt aktuelle Positionen aller Charaktere
```

### Spiel Schritt für Schritt spielen
```java
game.startRound();  // Eine Runde spielen
game.printGameState();  // Ergebnis anschauen
```

---

## 📝 Projektstruktur

```
src/main/java/wuwa/cheat/
├── Main.java              # Simulationsprogramm (Haupteinstiegspunkt)
├── STRUCTURE.md           # Dokumentation der Klassenstruktur
├── board/
│   ├── Position.java      # Felddefinitionen und Feldtypen
│   └── ProbabilityDice.java   # Würfel-Simulator
├── characters/
│   ├── original/          # Alle 7 Charaktere
│   │   ├── Person.java              # Basis-Klasse für alle Charaktere
│   │   ├── Abbowser.java            # Antagonist (rückwärts)
│   │   ├── Chisa.java               # +2 bei kleinster Würfelzahl
│   │   ├── Lynae.java               # Hochriskant (60%/20%/20%)
│   │   ├── Shorekeeper.java         # Würfelt 2 oder 3
│   │   ├── Aemeath.java             # Teleportation einmal pro Spiel
│   │   ├── Carlotta.java            # 28% Verdopplung
│   │   └── Mornye.java              # Feste Abfolge 3→2→1
│   └── abilities/         # Modifier-Interfaces
│       ├── ActOrderModifier.java
│       ├── MovementModifier.java
│       ├── OncePerGame.java
│       ├── OncePerRound.java
│       └── StackInteractor.java
└── game/
    └── WuerfelDerby.java  # Hauptspiel-Engine
```

---

**Viel Spaß beim Spielen! 🎲🏁**
