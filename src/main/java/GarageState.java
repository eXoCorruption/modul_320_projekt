public enum GarageState {
    INIT,                    // Schlüssel eingeben
    UNLOCKED,                // Zugang erlaubt
    WAITING_FOR_CAR,         // Einfahrt frei
    CAR_DETECTED,            // Auto erkannt (Einfahrt)
    GATE_OPEN,               // Tor offen (Einfahrt)
    GATE_CLOSING,            // Tor schließt
    GATE_CLOSED,             // Tor geschlossen
    AUSFAHRT_GATE_OPENING,   // Tor öffnet für Ausfahrt
    AUSFAHRT_GATE_OPEN       // Tor offen - Auto fährt aus
}
