package wuwa.cheat;

import java.util.*;

import wuwa.cheat.characters.original.*;
import wuwa.cheat.game.WuerfelDerby;

public class Main {
    private static final int SIMULATION_COUNT = 100000;
    
    public static void main(String[] args) {
        System.out.println("=== Würfelderby Probability Counter ===");
        System.out.println("Simuliere " + SIMULATION_COUNT + " Spiele...\n");
        
        // Statistiken sammeln
        Map<String, Integer> winCounts = new HashMap<>();
        Map<String, List<Integer>> positions = new HashMap<>();
        Map<String, Integer> totalGames = new HashMap<>();
        
        // Charaktere hinzufügen: Einfach richtige Namen der Klassen verwenden, die in "wuwa.cheat.characters.original" definiert sind!!!
        String[] characterNames = {"Abbowser", "Chisa", "Lynae", "Shorekeeper", "Aemeath", "Carlotta","Mornye"};
        for (String name : characterNames) {
            winCounts.put(name, 0);
            positions.put(name, new ArrayList<>());
            totalGames.put(name, 0);
        }
        String basePackage = "wuwa.cheat.characters.original.";
    
        // Führe Simulationen durch
        for (int sim = 0; sim < SIMULATION_COUNT; sim++) {
            WuerfelDerby game = new WuerfelDerby(sim);

            // ===== OPTION 1: Zufällige Positionen auf Feld 1 =====
            // Alle Charaktere auf Feld 1 mit zufälligem Stack-Platz (Level 0 bis zur Anzahl der Charaktere)
            // Der unterste Charakter (Level 0) trägt alle anderen mit!
            for (String name : characterNames) {
                try {
                    Class<?> clazz = Class.forName(basePackage + name);
                    Person character = (Person) clazz.getDeclaredConstructor().newInstance();
                    game.addCharacterAtStart(character, true);
                } catch (Exception e) {
                    System.err.println("Fehler beim Laden von Charakter: " + name);
                    e.printStackTrace();
                }
            }
            
            // ===== OPTION 2: Spezifische Positionen =====
            // AUSKOMMENTIEREN Sie Option 1 oben UND aktivieren Sie diesen Block, wenn Sie spezifische Positionen verwenden möchten!
            /*
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
            */
            
            // ===== OPTION 3: Direkte Objektinstanziierung =====
            // AUSKOMMENTIEREN Sie Option 1 oben UND aktivieren Sie diesen Block, wenn Sie direkte Objekte verwenden möchten!
            /*
            try {
                // Beispiel: Alle 7 Charaktere auf Feld 1, Level 0-6 gestapelt
                game.addCharacter(new Abbowser(), 1, 0);       // Feld 1, Level 0 (auf dem Boden - trägt alle anderen!)
                game.addCharacter(new Chisa(), 1, 1);         // Feld 1, Level 1 (auf Abbowser)
                game.addCharacter(new Lynae(), 1, 2);         // Feld 1, Level 2
                game.addCharacter(new Shorekeeper(), 1, 3);   // Feld 1, Level 3
                game.addCharacter(new Aemeath(), 1, 4);       // Feld 1, Level 4
                game.addCharacter(new Carlotta(), 1, 5);      // Feld 1, Level 5
                game.addCharacter(new Mornye(), 1, 6);        // Feld 1, Level 6 (ganz oben)
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            
            
            // Stapel organisieren (sortiert alle nach verticalLevel)
            game.organizeStack();
            
            // Spiel spielen bis Gewinn
            Person winner = game.playToCompletion();
            
            // Statistiken aktualisieren
            if (winner != null) {
                winCounts.put(winner.getName(), winCounts.get(winner.getName()) + 1);
            }
            
            // Positionen sammeln
            for (Person p : game.getAllCharacters()) {
                List<Integer> positionList = positions.get(p.getName());
                positionList.add(p.getPosition());
            }
        }
        
        // Berechne Durchschnitte
        Map<String, Double> averagePositions = new HashMap<>();
        for (String name : characterNames) {
            List<Integer> positionList = positions.get(name);
            double avg = positionList.stream().mapToInt(Integer::intValue).average().orElse(0);
            averagePositions.put(name, avg);
        }
        
        // Ausgabe Gewinnchancen
        System.out.println("\n========== GEWINNCHANCEN (Wahrscheinlichkeits-Ranking) ==========");
        winCounts.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .forEach(e -> {
                double winChance = (e.getValue() / (double) SIMULATION_COUNT) * 100;
                System.out.printf("%s: %.1f%% (%d Siege)%n", e.getKey(), winChance, e.getValue());
            });
        
        // Ausgabe Durchschnittliche Positionen
        System.out.println("\n========== DURCHSCHNITTLICHE POSITIONEN ==========");
        averagePositions.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .forEach(e -> {
                System.out.printf("%s: %.2f Felder%n", e.getKey(), e.getValue());
            });
        
        // Ausgabe Stack Information (simuliert mit Durchschnittswerten)
        System.out.println("\n========== STACK-INFORMATION ==========");
        System.out.println("Charakter der am weitesten vorankommt trägt andere 'gratis' mit:");
        String frontRunner = averagePositions.entrySet().stream()
            .max(Comparator.comparingDouble(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElse("Unbekannt");
        System.out.printf("\nGeführt von: %s (auf Feld ~%.1f im Schnitt)%n", 
            frontRunner, averagePositions.get(frontRunner));
        
        System.out.println("\n=== Simulation abgeschlossen ===");
    }
}