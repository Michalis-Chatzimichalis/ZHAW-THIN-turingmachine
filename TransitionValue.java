/**
 * Repräsentiert das Ergebnis einer Übergangsregel in der Turingmaschine,
 * bestehend aus dem nächsten Zustand, dem zu schreibenden Symbol und der Kopfbewegung.
 */
public class TransitionValue {
    public String nextState;
    public Symbol writeSymbol;   // Verwendet das externe Enum Symbol
    public Direction moveDirection; // Verwendet das externe Enum Direction

    /**
     * Konstruktor für einen TransitionValue.
     * @param nextState Der Zustand, in den gewechselt wird.
     * @param writeSymbol Das Symbol, das auf das Band geschrieben wird.
     * @param moveDirection Die Richtung, in die der Kopf bewegt wird.
     */
    public TransitionValue(String nextState, Symbol writeSymbol, Direction moveDirection) {
        // Überprüfungen auf null könnten hier hinzugefügt werden
        this.nextState = nextState;
        this.writeSymbol = writeSymbol;
        this.moveDirection = moveDirection;
    }

    @Override
    public String toString() {
        // Hilfreich für Debugging
        return "-> (" + nextState + ", " + writeSymbol.getChar() + ", " + moveDirection.getChar() + ")";
    }
} 