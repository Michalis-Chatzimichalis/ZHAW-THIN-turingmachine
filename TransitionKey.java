import java.util.Objects;

/**
 * Repräsentiert den Schlüssel für eine Übergangsregel in der Turingmaschine,
 * bestehend aus dem aktuellen Zustand und dem gelesenen Symbol.
 */
public class TransitionKey {
    final String state;
    final Symbol readSymbol; // Verwendet das externe Enum Symbol

    /**
     * Konstruktor für einen TransitionKey.
     * @param state Der aktuelle Zustand.
     * @param readSymbol Das unter dem Kopf gelesene Symbol.
     */
    public TransitionKey(String state, Symbol readSymbol) {
        // Überprüfungen auf null könnten hier hinzugefügt werden
        this.state = state;
        this.readSymbol = readSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionKey that = (TransitionKey) o;
        // Verwendet Enum-Vergleich für readSymbol
        return readSymbol == that.readSymbol && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, readSymbol);
    }

    @Override
    public String toString() {
        // Hilfreich für Debugging
        return "(" + state + ", " + readSymbol.getChar() + ")";
    }
} 