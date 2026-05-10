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

## 🎮 Spielkonzept

Das Würfelderby ist ein Rennspiel über ein 32-Feld-Spielbrett, bei dem:

- **Beliebig viele Charaktere** gleichzeitig laufen (Anzahl pro Runde konfigurierbar)
- Jeder Charakter eine **einzigartige Spezialfähigkeit** hat
- Die Charaktere sich gegenseitig **stapeln** können (Stack-Mechanik)
- Zufallselemente (Würfelwürfe) das Spiel beeinflussen
- Das erste Nicht-Abbowser-Charakter-Ziel am Feld 32 gewinnt
- Das Spiel **mehrfach simuliert** wird, um Statistiken zu sammeln

---

## 🦸 Charaktere & Fähigkeiten

### Neue Charaktere hinzufügen

Um neue Charaktere zum Spiel hinzuzufügen, erstelle eine neue Klasse, die von `Person` erbt:

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

Danach kann der Charakter wie jeder andere zum Spiel hinzugefügt werden:

```java
game.addCharacterAtStart(new MeinCharakter(), true);
```

### Vorhandene Charaktere

#### Originalcharaktere

### 🌙 Phoebe
- **Fähigkeit**: 50% Chance für +1 zusätzliches Feld pro Zug
- **Strategie**: Konstant und verlässlich mit guter Zusatzchance

### 🔮 Sigrika
- **Fähigkeit**: Nach jeder Runde (ab Runde 2) markiert sie bis zu 2 Charaktere auf ihrem Feld
- **Effekt**: Markierte Charaktere bewegen sich -1 Feld (min. 1 Feld)
- **Strategie**: Kontrolle durch Markierung der Konkurrenten

### ❄️ Hiyuki
- **Fähigkeit**: Wenn sie Abbowser trifft, erhält sie permanent +1 Feld pro Zug
- **Strategie**: Abhängig von der Begegnung mit Abbowser, wird dann sehr stark

### 💪 Carthethyia
- **Fähigkeit**: 60% Chance für +2 Felder, wenn auf Position 32
- **Effekt**: Nur 1x pro Runde auslösbar
- **Strategie**: Explosive Power in entscheidender Nähe zum Ziel

### 🧬 Denia
- **Fähigkeit**: Wenn die gleiche Augenzahl wie beim letzten Wurf erzielt wird: +2 Felder
- **Strategie**: Glücksspiel basiert auf Wiederholung

### ⚙️ Luuk Herssen
- **Fähigkeit**: Profitiert von Feldeffekten
  - Vorschubmechanismus: +2 Felder (statt +1)
  - Hemmmechanismus: -1 Feld (statt normal)
- **Strategie**: Spezialist für Feldinteraktionen

### 🎭 Abbowser (der Antagonist)
- **Besonderheit**: Bewegt sich rückwärts (ab Runde 3!)
- **Start**: Feld 32 (das Ziel)
- **Spezial**: Kann sich teleportieren, wenn allein
- **Rolle**: Verhindert, dass andere einfach vorbeigehen

---

#### Zusätzliche Charaktere

### 📖 Aemeath
- **Fähigkeit**: Einmal pro Spiel - Teleportation auf den Stapel des nächsten Charakters
- **Bedingung**: Ab Feldmitte (Feld 16+) und wenn ein anderer Charakter vor ihm ist
- **Strategie**: Taktischer Sprung nach vorne in der Spielmitte

### 🎪 Carlotta
- **Fähigkeit**: 28% Chance, die Bewegung zu verdoppeln
- **Strategie**: Moderate Boosts mit guter Erfolgsrate

### 🎯 Chisa
- **Fähigkeit**: +2 Felder, wenn die kleinste Würfelzahl der Runde gewürfelt wird
- **Strategie**: Profit aus der Schwäche wird zur Stärke

### 🎲 Lynae
- **Fähigkeit**: Unterschiedliche Chancen pro Zug:
  - 60% Chance für doppelte Bewegung
  - 20% Chance für keine Bewegung
  - 20% Chance für normale Bewegung
- **Strategie**: Hochriskant mit großem Upside-Potenzial

### 🔢 Mornye
- **Fähigkeit**: Feste Bewegungsabfolge (3 → 2 → 1 → 3 → 2 → 1 ...)
- **Strategie**: Vorhersehbar und konsistent, nicht vom Glück abhängig

### 🌊 Shorekeeper
- **Fähigkeit**: Würfelt immer 2 oder 3 (50/50 Chance)
- **Strategie**: Zuverlässig, mittelmäßig, stabil

---

## ⚙️ Spielmechaniken

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
  - `0` = auf dem Boden
  - `1` = auf einem anderen Charakter
  - `2+` = auf mehreren Charakteren
- Wichtig: **Der unten stehende trägt die oben stehenden mit!**

### 3. **Siegbedingung**
- Erster Charakter (außer Abbowser) auf Feld 32 = **Gewinner**
- Abbowser kann nicht gewinnen (bewegt sich rückwärts)

---

## 🏁 Feldtypen (8 Spezialfelder)

Das 32-Feld-Spielbrett hat 8 Spezialfelder:

| Feld | Typ | Effekt |
|------|-----|--------|
| 3 | Vorschubmechanismus | +1 Feld (Luuk: +2) |
| 5 | Hemmmechanismus | -1 Feld |
| 8 | Hemmmechanismus | -1 Feld |
| 15 | Raumzeitriss | Charakter wird neu gestapelt |
| 18 | Vorschubmechanismus | +1 Feld (Luuk: +2) |
| 23 | Vorschubmechanismus | +1 Feld (Luuk: +2) |
| 27 | Raumzeitriss | Charakter wird neu gestapelt |
| 30 | Raumzeitriss | Charakter wird neu gestapelt |

### Raumzeitriss (Feld 15, 27, 30)
- Auslöser für Neustapelung
- Charakter wird aus dem aktuellen Stack genommen
- Neu gestapelt je nach `onTimeRift()`-Implementierung

---

## 🖥️ Installation & Ausführung

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

## 📖 Verwendung

### Basis-Simulation (Zufällige Anfangspositionen)

```java
WuerfelDerby game = new WuerfelDerby(seed);

// Charaktere am Startfeld (Feld 1) mit zufälligem Stack-Platz
game.addCharacterAtStart(new Phoebe(), true);
game.addCharacterAtStart(new Sigrika(), true);
// ... weitere Charaktere

// Stack organisieren
game.organizeStack();

// Spiel bis zum Gewinn spielen
Person winner = game.playToCompletion();
```

### Spezifische Anfangspositionen

```java
WuerfelDerby game = new WuerfelDerby(seed);

// Direktes Platzieren auf beliebigen Feldern
game.addCharacter(new Phoebe(), 1, 0);      // Feld 1, auf dem Boden
game.addCharacter(new Sigrika(), 1, 1);     // Feld 1, auf Phoebe (Stapel!)
game.addCharacter(new Hiyuki(), 2, 0);      // Feld 2, auf dem Boden
game.addCharacter(new Carthethyia(), 5, 0);

game.organizeStack();
Person winner = game.playToCompletion();
```

### Individuelle Positionen Ändern

```java
Person phoebe = new Phoebe();
game.addCharacter(phoebe);

// Position ändern (z.B. nach der Initialisierung)
phoebe.setPosition(5);           // Auf Feld 5 setzen
phoebe.setVerticalPosition(2);   // Ebene 2 im Stack
```

---

## 📍 Anfangspositionen

### addCharacterAtStart(Person, boolean randomizeVerticalLevel)
- `randomizeVerticalLevel = true`: Zufälliger Stack-Platz (Ebene 0 bis Charakteranzahl-1)
- `randomizeVerticalLevel = false`: Immer auf dem Boden (Ebene 0)

```java
// Alle auf Feld 1, zufälliger Stapel
game.addCharacterAtStart(new Phoebe(), true);
game.addCharacterAtStart(new Sigrika(), true);

// Alle auf Feld 1, auf dem Boden
game.addCharacterAtStart(new Denia(), false);
```

### addCharacter(Person, int fieldNumber, int verticalLevel)
- **fieldNumber**: 1-32
- **verticalLevel**: 0 = Boden, 1+ = gestapelt auf anderen

```java
game.addCharacter(new Phoebe(), 1, 0);   // Feld 1, Level 0
game.addCharacter(new Sigrika(), 1, 1);  // Feld 1, Level 1 (auf Phoebe)
game.addCharacter(new Hiyuki(), 10, 0);  // Feld 10, Level 0
```

---

## 📊 Ausgabe & Statistiken

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
├── Main.java              # Simulationsprogramm
├── WuerfelDerby.java      # Hauptspiel-Engine
├── Person.java            # Basis-Klasse für Charaktere
├── Position.java          # Felddefinitionen
├── ProbabilityDice.java   # Würfel-Simulator
├── Phoebe.java            # Charakter
├── Sigrika.java           # Charakter
├── Hiyuki.java            # Charakter
├── Carthethyia.java       # Charakter
├── Denia.java             # Charakter
├── LuukHerssen.java       # Charakter
└── Abbowser.java          # Charakter (Antagonist)
```

---

## 💡 Tipps & Tricks

1. **Stack-Effekt**: Ein starker Charakter oben auf einem schwachen kann diesem großen Vorteil geben!
2. **Sigrika nutzen**: Ihre Markierungsfähigkeit ist sehr mächtig - positioniere sie strategisch
3. **Abbowser vermeiden**: Versuche, nicht mit Abbowser zusammen gestapelt zu werden
4. **Feldeffekte**: Luuk Herssen profitiert stark von Spezialfeldern
5. **Denia**: Braucht Glück mit Würfelwiederholungen - sehr variabel

---

## 📄 Lizenz

Dieses Projekt ist ein Lernprojekt für die Universität.

---

**Viel Spaß beim Spielen! 🎲🏁**
