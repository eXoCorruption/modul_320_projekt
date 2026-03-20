# Garage Door Control Software

Professionelle, zustandsbasierte Konsolenanwendung zur Simulation einer Garagentor-Steuerung mit Einfahrt/Ausfahrt, Ampelstatus, Zeitsteuerung und Protokollierung.

## Projektziel

Dieses Projekt modelliert die Steuerung eines Garagentors als **endlichen Zustandsautomaten**. Der Schwerpunkt liegt auf klarer Prozesslogik, nachvollziehbarer Bedienung und lückenloser Ereignisprotokollierung.

## Funktionsumfang

- Zugangsschutz per Codeeingabe
- Steuerung über klar definierte Systemzustände
- Einfahrt- und Ausfahrtprozess mit Torbewegung
- Automatisches Schließen des Tors per Countdown (Timeout)
- Benutzerinteraktion über Terminal-Eingaben
- Zwei getrennte Protokolldateien:
	- System-/Ablaufprotokoll
	- Ein-/Ausfahrtsprotokoll

## Technischer Aufbau

### Relevante Dateien

- `main.java`  
	Einstiegspunkt und zentrale Ablaufsteuerung (State Machine, Benutzereingaben, Countdown, Übergänge).

- `GarageState.java`  
	Enthält die Zustandskonstanten der Anwendung.

- `GarageUI.java`  
	Kapselt die Terminal-Ausgabe (Status- und Hinweismeldungen).

- `GarageLogger.java`  
	Verantwortlich für Initialisierung, Schreiben und Abschluss der Protokolldateien.

- `garage_protocol.txt`  
	Laufzeitprotokoll für Systemereignisse.

- `einausfahrt_protocol.txt`  
	Protokoll der erfassten Ein- und Ausfahrten.

## Zustandsmodell

Die Steuerung arbeitet mit folgenden Zuständen:

- `INIT` – Codeeingabe erforderlich
- `UNLOCKED` – Zugang erlaubt
- `WAITING_FOR_CAR` – wartet auf Fahrzeug an der Einfahrt
- `CAR_DETECTED` – Fahrzeug erkannt, Tor öffnet
- `GATE_OPEN` – Tor offen (Einfahrt)
- `GATE_CLOSING` – Tor schließt
- `GATE_CLOSED` – Tor geschlossen, Auswahl neue Einfahrt/Ausfahrt
- `AUSFAHRT_GATE_OPENING` – Tor öffnet für Ausfahrt
- `AUSFAHRT_GATE_OPEN` – Tor offen (Ausfahrt)

## Bedienlogik (Konsole)

- `q` beendet das Programm aus jedem relevanten Interaktionszustand.
- Zugangscode (derzeit fest im Code): `1234`
- Einfahrt starten: im Zustand `WAITING_FOR_CAR` Eingabe `1`
- Nach geschlossener Torphase:
	- `4` = neue Einfahrt
	- `5` = Ausfahrt
- In offenen Torzuständen (`GATE_OPEN`, `AUSFAHRT_GATE_OPEN`):
	- Automatisches Schließen nach 10 Sekunden
	- Vorzeitiges Schließen durch Eingabe (z. B. `3` als Bedienhinweis)

## Zeitverhalten

- Öffnungsvorgang Tor: ca. 8 Sekunden
- Schließvorgang Tor: ca. 5 Sekunden
- Auto-Close Countdown: 10 Sekunden

Die Zeiten sind aktuell als feste Werte implementiert (`Thread.sleep(...)` bzw. Konstante `AUTO_CLOSE_SECONDS`).

## Logging & Nachvollziehbarkeit

Beim Start werden beide Protokolldateien neu initialisiert (bestehende Inhalte werden überschrieben).  
Während der Laufzeit werden alle wesentlichen Ereignisse mit Zeitstempel geschrieben; zum Programmende werden die Dateien sauber abgeschlossen.

## Kompilieren und Starten

Die folgenden Schritte beziehen sich auf den Ordner `src/main/java`.

1. Java-Dateien kompilieren
2. Anwendung starten

Beispiel (Windows PowerShell oder Terminal):

- `javac *.java`
- `java Main`

## Qualitätsmerkmale

- Klare Trennung der Verantwortlichkeiten (UI, Zustand, Logging, Ablauf)
- Gut nachvollziehbare Zustandsübergänge
- Deterministische Prozesssteuerung mit eindeutigen Benutzereingaben
- Zeitgestempelte Betriebsdokumentation

## Bekannte technische Hinweise

- Der Zugangscode ist aktuell statisch im Quellcode hinterlegt.
- Die Anwendung ist auf eine Konsolenbedienung ausgelegt.
- Die Protokolldateien werden bei jedem Programmstart neu erzeugt.

## Ausblick

Mögliche Weiterentwicklungen für den Produktivbetrieb:

- Externe Konfiguration (Code, Zeiten, Dateipfade)
- Persistente Speicherung ohne Überschreiben historischer Logs
- Erweiterte Fehlerbehandlung und Validierung bei Eingaben
- Zusätzliche Sensor-/Sicherheitszustände (z. B. Hinderniserkennung)
- Unit-Tests für Zustandsübergänge und Logger-Verhalten