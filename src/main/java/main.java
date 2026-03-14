import java.util.Scanner;

class Main {

    private static final String KEYCODE = "1234";

    private static boolean isQuitCommand(String input) {
        return input != null && input.equalsIgnoreCase("q");
    }

    public static void main(String[] args) throws Exception {

        GarageUI ui = new GarageUI();
        Scanner scanner = new Scanner(System.in);
        GarageLogger logger = new GarageLogger();

        GarageState state = GarageState.INIT;

        boolean running = true;
        
        logger.log("Programm gestartet - Garagentor System aktiviert");

        try {
            while (running) {

                switch (state) {

                case INIT: {
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
                        Thread.sleep(1000);
                    }
                    break;
                }

                case UNLOCKED: {
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

                case WAITING_FOR_CAR: {
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

                case CAR_DETECTED: {
                    ui.showGarageStatus(state, "ROT");
                    logger.log("Tor öffnet sich...");
                    Thread.sleep(8000);
                    logger.log("Tor offen - Auto fährt ein");
                    state = GarageState.GATE_OPEN;
                    break;
                }

                case GATE_OPEN: {
                    ui.showGarageStatus(state, "GRUEN");
                    System.out.println("Eingabe: 3 = Tor schließen, q = Beenden");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus GATE_OPEN-Zustand");
                        running = false;
                    } else if (input.equals("3")) {
                        logger.log("Tor schließt sich...");
                        state = GarageState.GATE_CLOSING;
                    }
                    break;
                }

                case GATE_CLOSING: {
                    ui.showGarageStatus(state, "ROT");
                    Thread.sleep(5000);
                    logger.log("Tor geschlossen");
                    state = GarageState.GATE_CLOSED;
                    break;
                }

                case GATE_CLOSED: {
                    ui.showGarageStatus(state, "ROT");
                    System.out.println("Eingabe: 4 = zurück zu WARTEN, q = Beenden");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        logger.log("Benutzer beendet Programm aus GATE_CLOSED-Zustand");
                        running = false;
                    } else if (input.equals("4")) {
                        logger.log("Zurück zu Wartezustand - Einfahrt frei");
                        state = GarageState.WAITING_FOR_CAR;
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
