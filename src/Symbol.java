// Definiert die Symbole, die von der Turingmaschine verwendet werden.
public enum Symbol {
    ZERO('0', "0"),
    ONE('1', "00"),
    BLANK('_', "000"),
    I('I', "0000"),
    C('C', "00000"),
    D('D', "000000"),
    W('W', "0000000"),
    X('X', "00000000");

    private final char displayChar;
    private final String code; // Die binäre Kodierung des Symbols

    Symbol(char displayChar, String code) {
        this.displayChar = displayChar;
        this.code = code;
    }

    /**
     * Gibt die Zeichenrepräsentation des Symbols zurück (z.B. '0', '1', '_').
     * @return Das Zeichen des Symbols.
     */
    public char getChar() {
        return displayChar;
    }

    /**
     * Findet ein Symbol anhand seiner binären Kodierung.
     * @param code Der Kodierungsstring (z.B. "0", "00", "000").
     * @return Das entsprechende Symbol-Enum.
     * @throws IllegalArgumentException Wenn der Code ungültig ist.
     */
    public static Symbol fromCode(String code) {
        for (Symbol s : values()) {
            if (s.code.equals(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unsupported symbol code: " + code);
    }

    /**
     * Findet ein Symbol anhand seiner Zeichenrepräsentation.
     * @param c Das Zeichen (z.B. '0', '1', '_').
     * @return Das entsprechende Symbol-Enum.
     * @throws IllegalArgumentException Wenn das Zeichen keinem Symbol zugeordnet ist.
     */
    public static Symbol fromChar(char c) {
        if (c == BLANK.displayChar) return BLANK;
        for (Symbol s : values()) {
            if (s.displayChar == c) {
                return s;
            }
        }
       throw new IllegalArgumentException("Unsupported symbol character: " + c);
    }

     /**
      * Gibt die Zeichenrepräsentation des Symbols zurück.
      * @return Das Zeichen des Symbols als String.
      */
     @Override
     public String toString() {
         return String.valueOf(displayChar);
     }
} 