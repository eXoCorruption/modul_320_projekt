import java.io.IOException;
import java.util.Scanner;

class Main {

    private static final String KEYCODE = "1234";
    private static final int AUTO_CLOSE_SECONDS = 10;

    private static boolean isQuitCommand(String input) {
        return input != null && input.equalsIgnoreCase("q");
    }

    /**
     * Wartet auf Benutzereingabe, schliesst aber automatisch nach 10 Sekunden.
     * Gibt die Eingabe zurück, oder null bei Timeout.
     */
    
    private static String waitForInputOrCountdown(Scanner scanner, int seconds) throws InterruptedException {
        for (int i = seconds; i > 0; i--) {
            System.out.print("\rTor schliesst automatisch in " + i + "s  (3 = jetzt, q = Beenden): ");
            System.out.flush();
            for (int j = 0; j < 10; j++) {
                try {
                    if (System.in.available() > 0) {
                        System.out.println();
                        return scanner.nextLine().trim();
                    }
                } catch (IOException e) { /* ignorieren */ }
                Thread.sleep(100);
            }
        }
        System.out.println();
        return null; // Timeout, schliesst automatisch
    }

    public static void main(String[] args) throws Exception {

        GarageUI ui = new GarageUI();
        Scanner scanner = new Scanner(System.in);
        GarageLogger logger = new GarageLogger();

        String state = GarageState.INIT;

        boolean running = true;
        
        logger.log("Programm gestartet - Garagentor System aktiviert");

        try {
            while (running) {

                switch (state) {

                case GarageState.INIT: {
                    ui.showMessage("Schlüssel erforderlich",
                            "Bitte Code eingeben (oder q zum Beenden):");

                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus INIT-Zustand");
                        running = false;
                    } else if (input.equals(KEYCODE)) {
                        logger.log("Korrekter Code eingegeben - Zugang gewährt");
                        state = GarageState.UNLOCKED;
                    } else {
                        logger.log("Falscher Code eingegeben");
                        ui.showMessage("FALSCHER CODE", "Bitte erneut versuchen.");
                    }
                    break;
                }

                case GarageState.UNLOCKED: {
                    ui.showMessage("Zugang erlaubt", "Enter zum Starten, q zum Beenden.");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus UNLOCKED-Zustand");
                        running = false;
                    } else {
                        logger.log("Einfahrt freigegeben - warte auf Auto");
                        state = GarageState.WAITING_FOR_CAR;
                    }
                    break;
                }

                case GarageState.WAITING_FOR_CAR: {
                    ui.showGarageStatus(state, "GRUEN");
                    System.out.println("Eingabe: 1 = Auto erkannt, q = Beenden");

                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus WAITING_FOR_CAR-Zustand");
                        running = false;
                    } else if (input.equals("1")) {
                        logger.log("Auto erkannt - Tor wird geöffnet");
                        state = GarageState.CAR_DETECTED;
                    }
                    break;
                }

                case GarageState.CAR_DETECTED: {
                    ui.showGarageStatus(state, "ROT");
                    logger.log("Tor öffnet sich...");
                    Thread.sleep(8000);
                    logger.log("Tor offen - Auto fährt ein");
                    logger.logEinfahrt();
                    state = GarageState.GATE_OPEN;
                    break;
                }

                case GarageState.GATE_OPEN: {
                    ui.showGarageStatus(state, "GRUEN");
                    String input = waitForInputOrCountdown(scanner, AUTO_CLOSE_SECONDS);
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus GATE_OPEN-Zustand");
                        running = false;
                    } else {
                        if (input == null) {
                            logger.log("Countdown abgelaufen - Tor schliesst automatisch");
                        } else {
                            logger.log("Tor schliesst sich...");
                        }
                        state = GarageState.GATE_CLOSING;
                    }
                    break;
                }

                case GarageState.GATE_CLOSING: {
                    ui.showGarageStatus(state, "ROT");
                    Thread.sleep(5000);
                    logger.log("Tor geschlossen");
                    state = GarageState.GATE_CLOSED;
                    break;
                }

                case GarageState.GATE_CLOSED: {
                    ui.showGarageStatus(state, "ROT");
                    System.out.println("Eingabe: 4 = neue Einfahrt, 5 = Ausfahrt, q = Beenden");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus GATE_CLOSED-Zustand");
                        running = false;
                    } else if (input.equals("4")) {
                        logger.log("Zurück zu Wartezustand - Einfahrt frei");
                        state = GarageState.WAITING_FOR_CAR;
                    } else if (input.equals("5")) {
                        logger.log("Ausfahrt ausgelöst - Tor öffnet für Ausfahrt");
                        state = GarageState.AUSFAHRT_GATE_OPENING;
                    }
                    break;
                }

                case GarageState.AUSFAHRT_GATE_OPENING: {
                    ui.showGarageStatus(state, "ROT");
                    logger.log("Tor öffnet sich für Ausfahrt...");
                    Thread.sleep(8000);
                    logger.log("Tor offen - Auto fährt aus");
                    logger.logAusfahrt();
                    state = GarageState.AUSFAHRT_GATE_OPEN;
                    break;
                }

                case GarageState.AUSFAHRT_GATE_OPEN: {
                    ui.showGarageStatus(state, "GRUEN");
                    String input = waitForInputOrCountdown(scanner, AUTO_CLOSE_SECONDS);
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus AUSFAHRT_GATE_OPEN-Zustand");
                        running = false;
                    } else {
                        if (input == null) {
                            logger.log("Countdown abgelaufen - Tor schliesst automatisch nach Ausfahrt");
                        } else {
                            logger.log("Tor schliesst sich nach Ausfahrt...");
                        }
                        state = GarageState.GATE_CLOSING;
                    }
                    break;
                }
                default:
                    break;
                }
            }
        } finally {
            ui.close();
            logger.close();
            scanner.close();
        }
    }
}
