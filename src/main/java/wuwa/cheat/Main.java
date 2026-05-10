package wuwa.cheat;

import java.util.*;

public class Main {
    private static final int SIMULATION_COUNT = 10000;
    
    public static void main(String[] args) {
        System.out.println("=== Würfelderby Probability Counter ===");
        System.out.println("Simuliere " + SIMULATION_COUNT + " Spiele...\n");
        
        // Statistiken sammeln
        Map<String, Integer> winCounts = new HashMap<>();
        Map<String, List<Integer>> positions = new HashMap<>();
        Map<String, Integer> totalGames = new HashMap<>();
        
        String[] characterNames = {"Chisa", "Mornye", "Lynae", "Aemeath", "Shorekeeper", "Carlotta", "Abbowser"};
        for (String name : characterNames) {
            winCounts.put(name, 0);
            positions.put(name, new ArrayList<>());
            totalGames.put(name, 0);
        }
        
        // Führe Simulationen durch
        for (int sim = 0; sim < SIMULATION_COUNT; sim++) {
            WuerfelDerby game = new WuerfelDerby(sim);
            
            // Charaktere hinzufügen
            // Option 1: Am Startfeld (Feld 1) mit zufälligem Stack-Platz
            // wichtig: characterNames müssen mit den tatsächlichen Charakterklassen übereinstimmen!!!
            game.addCharacterAtStart(new Chisa(), true);
            game.addCharacterAtStart(new Mornye(), true);
            game.addCharacterAtStart(new Lynae(), true);
            game.addCharacterAtStart(new Aemeath(), true);
            game.addCharacterAtStart(new Shorekeeper(), true);
            game.addCharacterAtStart(new Carlotta(), true);
            game.addCharacterAtStart(new Abbowser(), true);
            
            // Option 2: Spezifische Positionen setzen (Beispiele):
            // game.addCharacter(new Phoebe(), 1, 0);      // Feld 1, Level 0 (auf dem Boden)
            // game.addCharacter(new Sigrika(), 1, 1);     // Feld 1, Level 1 (auf Phoebe)
            // game.addCharacter(new Hiyuki(), 2, 0);      // Feld 2, Level 0
            
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