public enum GarageState {
    INIT,              // Schlüssel eingeben
    UNLOCKED,          // Zugang erlaubt
    WAITING_FOR_CAR,   // Einfahrt frei
    CAR_DETECTED,      // Auto erkannt
    GATE_OPEN,         // Tor offen
    GATE_CLOSING,      // Tor schließt
    GATE_CLOSED        // Tor geschlossen
}
