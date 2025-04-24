import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner; // Für Step-Modus Eingabe

public class Turingmachine {

    // --- Konstanten ---
    // private static final char BLANK_SYMBOL = '_'; // Entfernt, Symbol.BLANK verwenden
    private static final String INITIAL_STATE = "q0";
    private static final int MAX_STEPS = 10000;

    // --- Felder ---
    private String currentState;
    private Map<Integer, Symbol> tape; // Verwendet externes Enum Symbol
    private int headPosition;
    private long stepCount;
    private Map<TransitionKey, TransitionValue> transitions;
    private boolean halted;
    private Scanner scanner;

    // --- Konstruktoren ---
    public Turingmachine() {
        this.tape = new HashMap<>();
        this.headPosition = 0;
        this.stepCount = 0;
        this.currentState = INITIAL_STATE;
        this.transitions = new HashMap<>();
        this.halted = false;
        tape.put(0, Symbol.BLANK); // Verwende externes Enum Symbol.BLANK
    }

    public Turingmachine(String tmCode, String input) {
        this();
        try {
            parseTMCoding(tmCode);
            loadInput(input);
        } catch (IllegalArgumentException e) {
            System.err.println("Error initializing Turing Machine: " + e.getMessage());
            this.halted = true;
        }
    }

    // --- Kodierung/Dekodierung ---
    private String decodeState(String code) {
        if (!code.matches("0+")) throw new IllegalArgumentException("Invalid state code: " + code);
        return "q" + (code.length() - 1);
    }

    // Verwendet jetzt Symbol.fromCode
    private Symbol decodeSymbol(String code) {
        return Symbol.fromCode(code);
    }

    // Verwendet jetzt Direction.fromCode
    private Direction decodeMove(String code) {
        return Direction.fromCode(code);
    }


    private void parseTMCoding(String tmCode) {
        if (tmCode == null || tmCode.isEmpty()) {
            throw new IllegalArgumentException("TM code cannot be empty.");
        }
        transitions.clear();
        String[] rules = tmCode.split("11");
        for (String rule : rules) {
            String[] parts = rule.split("1");
            if (parts.length != 5) {
                if (rule.isEmpty() || (parts.length == 1 && parts[0].isEmpty())) continue;
                if (parts.length == 6 && parts[0].isEmpty()) {
                    String[] temp = new String[5];
                    System.arraycopy(parts, 1, temp, 0, 5);
                    parts = temp;
                } else {
                    throw new IllegalArgumentException("Invalid rule format in code: " + rule + " (parts=" + parts.length + ")");
                }
            }

            try {
                String currentStateStr = decodeState(parts[0]);
                Symbol readSymbol = decodeSymbol(parts[1]); // Ruft die angepasste Methode auf
                String nextStateStr = decodeState(parts[2]);
                Symbol writeSymbol = decodeSymbol(parts[3]); // Ruft die angepasste Methode auf
                Direction moveDir = decodeMove(parts[4]);   // Ruft die angepasste Methode auf

                TransitionKey key = new TransitionKey(currentStateStr, readSymbol);
                TransitionValue value = new TransitionValue(nextStateStr, writeSymbol, moveDir);

                if (transitions.containsKey(key)) {
                    System.err.println("Warning: Duplicate transition rule for state " + key.state + " and symbol " + key.readSymbol.getChar() + ". Overwriting.");
                }
                transitions.put(key, value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Error parsing rule '" + rule + "': " + e.getMessage());
            }
        }
        if (transitions.isEmpty()) {
            throw new IllegalArgumentException("No valid transitions found in TM code.");
        }
        System.out.println("Parsed " + transitions.size() + " transition rules.");
    }


    private void loadInput(String input) {
        String tapeInput;

        if (input.matches("[01]*")) {
            tapeInput = input;
            System.out.println("Using binary input: '" + input + "'");
        } else {
            try {
                int decimalValue = Integer.parseInt(input);
                if (decimalValue < 0) {
                    throw new IllegalArgumentException("Decimal input must be non-negative.");
                }
                StringBuilder unary = new StringBuilder();
                for (int i = 0; i < decimalValue; i++) {
                    unary.append(Symbol.ZERO.getChar()); // Verwende Char von externem Enum
                }
                tapeInput = unary.toString();
                System.out.println("Converted decimal input " + input + " to unary: '" + tapeInput + "'");
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Input must be a binary string or a non-negative decimal integer. Invalid input: " + input);
            }
        }

        tape.clear();
        tape.put(0, Symbol.BLANK); // Verwende externes Enum
        headPosition = 1;

        for (int i = 0; i < tapeInput.length(); i++) {
            try {
                tape.put(headPosition + i, Symbol.fromChar(tapeInput.charAt(i))); // Verwende externes Enum
            } catch (IllegalArgumentException e) {
                System.err.println("Internal error: Failed to convert character '" + tapeInput.charAt(i) + "' to Symbol.");
                this.halted = true;
                return;
            }
        }

        headPosition = tapeInput.isEmpty() ? 0 : 1;
        currentState = INITIAL_STATE;
        stepCount = 0;
        halted = false;
        System.out.println("Input loaded. Initial state: " + currentState + ", Head at: " + headPosition);
    }

    // --- Ausführung ---
    public void step() {
        if (halted) {
            return; // Weniger verbose
        }

        Symbol currentSymbol = tape.getOrDefault(headPosition, Symbol.BLANK); // Verwende externes Enum

        TransitionKey key = new TransitionKey(currentState, currentSymbol);
        TransitionValue rule = transitions.get(key);

        if (rule == null) {
            halted = true;
            System.out.println("No transition found for state=" + currentState + ", symbol=" + currentSymbol.getChar() + ". Machine halted.");
            return;
        }

        currentState = rule.nextState;
        tape.put(headPosition, rule.writeSymbol);

        if (rule.moveDirection == Direction.LEFT) { // Verwende externes Enum
            headPosition--;
        } else if (rule.moveDirection == Direction.RIGHT) { // Verwende externes Enum
            headPosition++;
        }

        if (!tape.containsKey(headPosition)) {
            tape.put(headPosition, Symbol.BLANK); // Verwende externes Enum
        }

        stepCount++;

        if (stepCount >= MAX_STEPS) {
            halted = true;
            System.out.println("Maximum step count (" + MAX_STEPS + ") reached. Assuming halt.");
        }
    }

    public void run(boolean stepMode) {
        if (halted) {
            System.out.println("Machine cannot run, it's already in a halted state.");
            printFinalState();
            return;
        }

        System.out.println("--- Starting Turing Machine ---");
        System.out.println("Mode: " + (stepMode ? "Step" : "Run"));
        printState();

        if (stepMode) {
            scanner = new Scanner(System.in);
        }

        while (!halted) {
            if (stepMode) {
                System.out.println("Press Enter to next step, or type 'run' to finish...");
                String command = scanner.nextLine();
                if ("run".equalsIgnoreCase(command.trim())) {
                    stepMode = false;
                    System.out.println("Switching to run mode...");
                } else if ("quit".equalsIgnoreCase(command.trim()) || "exit".equalsIgnoreCase(command.trim())) {
                    System.out.println("Execution aborted by user.");
                    halted = true;
                    break;
                }
            }

            step();

            if (!halted) {
                if (stepMode) {
                    printState();
                }
            }
        }

        if (scanner != null) {
            scanner.close();
            scanner = null;
        }

        System.out.println("--- Turing Machine Halted ---");
        printFinalState();
    }


    // --- Ausgabe ---
    public void printState() {
        System.out.println("--- Step: " + (stepCount + 1) + " ---");
        System.out.println("b) State: " + currentState);
        System.out.println("d) Head Position: " + headPosition);

        StringBuilder tapeString = new StringBuilder();
        int viewRadius = 15;
        int tapeViewStart = headPosition - viewRadius;
        int tapeViewEnd = headPosition + viewRadius;

        tapeString.append("c) Tape: ...");
        for (int i = tapeViewStart; i <= tapeViewEnd; i++) {
            tapeString.append('|');
            if (i == headPosition) {
                tapeString.append('X');
            } else {
                tapeString.append(tape.getOrDefault(i, Symbol.BLANK).getChar()); // Hole Char von externem Enum
            }
        }
        tapeString.append('|');
        tapeString.append("...");

        System.out.println(tapeString.toString());
        System.out.println("e) Step Count (Displayed): " + (stepCount + 1));
        System.out.println("-------------------------");
    }

    public void printFinalState() {
        System.out.println("=========================");
        System.out.println("      FINAL STATE");
        System.out.println("=========================");
        System.out.println("a) Result (Final Tape):");
        printState();
        StringBuilder result = new StringBuilder();
        Integer minPos = null;
        Integer maxPos = null;

        for (Map.Entry<Integer, Symbol> entry : tape.entrySet()) {
            if (entry.getValue() != Symbol.BLANK) { // Verwende externes Enum
                if (minPos == null || entry.getKey() < minPos) minPos = entry.getKey();
                if (maxPos == null || entry.getKey() > maxPos) maxPos = entry.getKey();
            }
        }

        if (minPos == null) {
            minPos = 0; // Fallback auf 0 wenn Band leer oder nur Blanks
            maxPos = 0;
        }

        int i = minPos;
        // Lese vom ersten bis zum letzten Nicht-Blank Zeichen (inklusive dazwischenliegender Blanks)
        while (i <= maxPos) {
            result.append(tape.getOrDefault(i, Symbol.BLANK).getChar()); // Hole Char von externem Enum
            i++;
        }
        // Hier könnte man noch nachfolgende Blanks am Ende entfernen, wenn gewünscht.

        System.out.println("a) Extracted Result: '" + (result.length() > 0 ? result.toString() : "<empty>") + "'");
        System.out.println("=========================");
    }


    // --- Main Methode ---
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage: java Turingmachine <tm_code> <input> [mode]");
            System.out.println("  <tm_code>: Binary TM code string.");
            System.out.println("  <input>:   Input string (binary or non-negative decimal).");
            System.out.println("  [mode]:    Optional 'step' for step-by-step execution, default is 'run'.");
            System.out.println("\nExample TMs (from lecture notes - ensure correct encoding):");
            System.out.println(" T1 = 010010001010011000101010010110001001001010011000100010001010");
            System.out.println(" T2 = 1010010100100110101000101001100010010100100110001010010100");
            return;
        }

        String tmSource = args[0];
        String input = args[1];
        boolean stepMode = false;
        if (args.length == 3 && "step".equalsIgnoreCase(args[2])) {
            stepMode = true;
        }

        Turingmachine tm = null;

        // Prüfe, ob der TM-Code eine gültige Binärzeichenkette ist
        if (tmSource.matches("[01]+")) {;
            tm = new Turingmachine(tmSource, input);
        } else {
            System.err.println("Error: TM code must be a binary string (containing only '0' and '1'). Invalid code: " + tmSource);
            return;
        }

        if (tm != null && !tm.halted) {
            tm.run(stepMode);
        } else {
            System.err.println("Turing machine initialization failed. Cannot run.");
        }
    }
}