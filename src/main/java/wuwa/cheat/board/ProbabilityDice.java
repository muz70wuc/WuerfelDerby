package wuwa.cheat.board;

import java.util.Random;

/**
 * Ein Würfel mit Wahrscheinlichkeitsverteilung:
 * - Meistens 2 oder 3 (50% Chance gesamt)
 * - 1/7 der Charaktere würfelt manchmal eine 5 (Lucky Roll)
 * - 4 ist selten
 * - 1 ist möglich aber nicht häufig
 */
public class ProbabilityDice {
    private Random random;
    private static final double LUCKY_ROLL_PROBABILITY = 1.0 / 7.0;  // ~14% für High Roller
    
    public ProbabilityDice(Random random) {
        this.random = random;
    }
    
    /**
     * Wirft den Würfel mit spezieller Wahrscheinlichkeitsverteilung
     * @return Zahl zwischen 1-5
     */
    public int roll() {
        return rollWithStandardProbability();
    }
    
    /**
     * Würfel für normale Charaktere (Meistens 2-3, selten hohe Zahlen)
     */
    private int rollWithStandardProbability() {
        double random_val = random.nextDouble();
        
        if (random_val < 0.05) {
            return 1;  // 5% Chance
        } else if (random_val < 0.35) {
            return 2;  // 30% Chance
        } else if (random_val < 0.65) {
            return 3;  // 30% Chance
        } else if (random_val < 0.85) {
            return 4;  // 20% Chance
        } else {
            return 5;  // 15% Chance
        }
    }
    
    /**
     * Würfel für "Lucky Roll" Charaktere (1/7 Charaktere)
     * Sie haben höhere Chancen auf 5
     */
    public int roleLuckyCharacter() {
        double random_val = random.nextDouble();
        
        if (random_val < 0.02) {
            return 1;  // 2% Chance
        } else if (random_val < 0.25) {
            return 2;  // 23% Chance
        } else if (random_val < 0.50) {
            return 3;  // 25% Chance
        } else if (random_val < 0.70) {
            return 4;  // 20% Chance
        } else {
            return 5;  // 30% Chance (doppelt so hoch!)
        }
    }
    
    /**
     * Gibt an, ob ein Charakter ein Lucky Roll Charakter ist
     * @param characterIndex Der Index des Charakters
     * @return true wenn dieser Charakter ein Lucky Roller ist
     */
    public static boolean isLuckyCharacter(int characterIndex) {
        return characterIndex % 7 == 0;  // Jeder 7te Charakter
    }
}
