// Definiert die Bewegungsrichtungen des Lese-/Schreibkopfes.
public enum Direction {
    LEFT('L', "0"),
    RIGHT('R', "00");

    private final char displayChar;
    private final String code;

    Direction(char displayChar, String code) {
        this.displayChar = displayChar;
        this.code = code;
    }

    /**
     * Gibt die Zeichenrepräsentation der Richtung zurück ('L' oder 'R').
     * @return Das Zeichen der Richtung.
     */
    public char getChar() {
        return displayChar;
    }

    /**
     * Findet eine Richtung anhand ihrer binären Kodierung.
     * @param code Der Kodierungsstring ("0" oder "00").
     * @return Das entsprechende Direction-Enum.
     * @throws IllegalArgumentException Wenn der Code ungültig ist.
     */
    public static Direction fromCode(String code) {
        for (Direction d : values()) {
            if (d.code.equals(code)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unsupported move code: " + code);
    }

     /**
      * Gibt die Zeichenrepräsentation der Richtung zurück.
      * @return Das Zeichen der Richtung als String.
      */
     @Override
     public String toString() {
         return String.valueOf(displayChar);
     }
} 