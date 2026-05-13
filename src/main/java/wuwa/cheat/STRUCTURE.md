# Würfelderby - Ordnerstruktur

## Übersicht

```
src/main/java/wuwa/cheat/
├── Main.java                           # Einstiegspunkt
├── characters/                         # Alle Character-Klassen
│   ├── Person.java                    # Basis-Klasse für alle Charaktere (abstrakt)
│   ├── abilities/                     # Interfaces für gemeinsame Fähigkeiten
│   │   ├── ActOrderModifier.java     # Beeinflusst Zugreihenfolge
│   │   ├── MovementModifier.java     # Modifiziert Bewegung
│   │   ├── OncePerGame.java          # Fähigkeit einmal pro Spiel
│   │   ├── OncePerRound.java         # Fähigkeit einmal pro Runde
│   │   └── StackInteractor.java      # Interagiert mit anderen Charakteren
│   └── original/                      # Originale Charaktere
│       ├── Abbowser.java
│       ├── Aemeath.java              # implementiert OncePerGame
│       ├── Carlotta.java
│       ├── Carthethyia.java          # implementiert OncePerRound
│       ├── Chisa.java
│       ├── Denia.java
│       ├── Hiyuki.java
│       ├── LuukHerssen.java
│       ├── Lynae.java
│       ├── Mornye.java
│       ├── Phoebe.java
│       ├── Shorekeeper.java
│       ├── Sigrika.java
│       ├── Augusta.java              # implementiert ActOrderModifier
│       ├── Calcharo.java             # implementiert MovementModifier
│       ├── Changli.java              # implementiert ActOrderModifier
│       ├── Iuno.java                 # implementiert OncePerGame
│       ├── Jinhsi.java               # implementiert StackInteractor
│       └── Phrolova.java             # implementiert MovementModifier, StackInteractor
├── board/                             # Brett-Verwaltung
│   ├── Position.java                 # Feldverwaltung (Feldtypen)
│   └── ProbabilityDice.java          # Würfel-Logik
└── game/                              # Spiel-Kern
    └── WuerfelDerby.java             # Hauptspiel-Klasse

```

## Interface-Beschreibungen

### ActOrderModifier
- **Nutzer**: Augusta, Changli
- **Zweck**: Charaktere, die die Zugreihenfolge beeinflussen
- **Methoden**: `shouldActLast()`, `shouldSkipTurn()`

### MovementModifier
- **Nutzer**: Phrolova, Calcharo
- **Zweck**: Bewegungs-Boni unter bestimmten Bedingungen
- **Methoden**: `getMovementBonus()`

### OncePerGame
- **Nutzer**: Aemeath, Iuno
- **Zweck**: Fähigkeiten, die nur einmal pro Spiel aktiviert werden
- **Methoden**: `hasAbilityBeenUsed()`, `markAbilityAsUsed()`

### OncePerRound
- **Nutzer**: Carthethyia
- **Zweck**: Fähigkeiten, die pro Runde zurückgesetzt werden
- **Methoden**: `resetRoundAbility()`

### StackInteractor
- **Nutzer**: Jinhsi, Phrolova
- **Zweck**: Charaktere, die mit dem Stack interagieren
- **Methoden**: `performStackInteraction(WuerfelDerby game)`

## Migration-Anleitung

Die Dateien wurden wie folgt organisiert:
1. **Person.java** bleibt in `characters/`
2. Originale 13 Charaktere → `characters/original/`
3. Neue 6 Charaktere → `characters/new_characters/`
4. **Position.java** und **ProbabilityDice.java** → `board/`
5. **WuerfelDerby.java** → `game/`
6. **Main.java** bleibt im Wurzelverzeichnis

Alle Imports wurden automatisch angepasst.
