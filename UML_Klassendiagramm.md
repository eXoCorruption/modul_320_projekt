# UML Klassendiagramm – modul_320_projekt

Dieses Dokument enthält das UML-Klassendiagramm basierend auf dem aktuellen Code-Stand.

```mermaid
classDiagram
    direction TB

    class Main {
        -KEYCODE : String
        -AUTO_CLOSE_SECONDS : int
        -isQuitCommand(input : String)$ boolean
        -waitForInputOrCountdown(scanner : Scanner, seconds : int)$ String
        +main(args : String[])$ void
    }

    class GarageLogger {
        -LOG_FILE : String$
        -EIN_AUSFAHRT_FILE : String$
        -TIME_FORMAT : DateTimeFormatter$
        -DATE_TIME_FORMAT : DateTimeFormatter$
        +GarageLogger()
        -initFile(file : String, title : String) void
        +log(message : String) void
        +logEinfahrt() void
        +logAusfahrt() void
        -writeToFile(file : String, line : String) void
        +close() void
        -closeFile(file : String) void
    }

    class GarageUI {
        +GarageUI()
        +showMessage(title : String, msg : String) void
        +showGarageStatus(state : String, ampel : String) void
        +close() void
    }

    class GarageState {
        <<utility>>
        +INIT : String$
        +UNLOCKED : String$
        +WAITING_FOR_CAR : String$
        +CAR_DETECTED : String$
        +GATE_OPEN : String$
        +GATE_CLOSING : String$
        +GATE_CLOSED : String$
        +AUSFAHRT_GATE_OPENING : String$
        +AUSFAHRT_GATE_OPEN : String$
    }

    %% Assoziationen und Abhängigkeiten
    Main --> GarageLogger : uses
    Main --> GarageUI : uses
    Main --> GarageState : references
    Main --> Scanner : uses (java.util)
```

    Main ..> GarageUI : uses
    Main ..> GarageLogger : uses
    Main ..> GarageState : uses constants
```
