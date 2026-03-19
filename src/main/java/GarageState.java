public class GarageState {
    // Zustände der Garageneinfahrt
    public static final String INIT = "INIT";                       // Schlüssel eingeben
    public static final String UNLOCKED = "UNLOCKED";               // Zugang erlaubt
    public static final String WAITING_FOR_CAR = "WAITING_FOR_CAR"; // Einfahrt frei
    public static final String CAR_DETECTED = "CAR_DETECTED";       // Auto erkannt (Einfahrt)
    public static final String GATE_OPEN = "GATE_OPEN";             // Tor offen (Einfahrt)
    public static final String GATE_CLOSING = "GATE_CLOSING";       // Tor schliesst
    public static final String GATE_CLOSED = "GATE_CLOSED";         // Tor geschlossen
    public static final String AUSFAHRT_GATE_OPENING = "AUSFAHRT_GATE_OPENING"; // Tor öffnet für Ausfahrt
    public static final String AUSFAHRT_GATE_OPEN = "AUSFAHRT_GATE_OPEN";       // Tor offen - Auto fährt aus
}
