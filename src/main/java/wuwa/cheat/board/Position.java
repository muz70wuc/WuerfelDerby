package wuwa.cheat.board;

public class Position {
    private int fieldNumber;  // 1-32
    private FieldProperty fieldProperty;  // Spezielle Eigenschaften
    
    public Position(int fieldNumber, FieldProperty property) {
        this.fieldNumber = fieldNumber;
        this.fieldProperty = property;
    }
    
    public int getFieldNumber() {
        return fieldNumber;
    }
    
    public FieldProperty getFieldProperty() {
        return fieldProperty;
    }
    
    public void setFieldProperty(FieldProperty property) {
        this.fieldProperty = property;
    }
    
    @Override
    public String toString() {
        return "Feld " + fieldNumber + " (" + fieldProperty + ")";
    }
    
    public enum FieldProperty {
        NORMAL,                // Normales Feld
        VORSCHUBMECHANISMUS,  // +1 Feld vorwärts
        HEMMMECHANISMUS,       // -1 Feld rückwärts
        RAUMZEITRISS           // Wird neu gestapelt
    }
}
