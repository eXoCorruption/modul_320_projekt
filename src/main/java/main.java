import java.util.Scanner;

class Main {

    private static final String KEYCODE = "1234";

    private static boolean isQuitCommand(String input) {
        return input != null && input.equalsIgnoreCase("q");
    }

    public static void main(String[] args) throws Exception {

        GarageUI ui = new GarageUI();
        Scanner scanner = new Scanner(System.in);

        GarageState state = GarageState.INIT;

        boolean running = true;

        try {
            while (running) {

                switch (state) {

                case INIT: {
                    ui.showMessage("Schlüssel erforderlich",
                            "Bitte Code eingeben (oder q zum Beenden):");

                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        running = false;
                    } else if (input.equals(KEYCODE)) {
                        state = GarageState.UNLOCKED;
                    } else {
                        ui.showMessage("FALSCHER CODE", "Bitte erneut versuchen.");
                        Thread.sleep(1000);
                    }
                    break;
                }

                case UNLOCKED: {
                    ui.showMessage("Zugang erlaubt", "Enter zum Starten, q zum Beenden.");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        running = false;
                    } else {
                        state = GarageState.WAITING_FOR_CAR;
                    }
                    break;
                }

                case WAITING_FOR_CAR: {
                    ui.showGarageStatus(state, "GRUEN");
                    System.out.println("Eingabe: 1 = Auto erkannt, q = Beenden");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        running = false;
                    } else if (input.equals("1")) {
                        state = GarageState.CAR_DETECTED;
                    }
                    break;
                }

                case CAR_DETECTED: {
                    ui.showGarageStatus(state, "ROT");
                    Thread.sleep(8000);
                    state = GarageState.GATE_OPEN;
                    break;
                }

                case GATE_OPEN: {
                    ui.showGarageStatus(state, "GRUEN");
                    System.out.println("Eingabe: 3 = Tor schließen, q = Beenden");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        running = false;
                    } else if (input.equals("3")) {
                        state = GarageState.GATE_CLOSING;
                    }
                    break;
                }

                case GATE_CLOSING: {
                    ui.showGarageStatus(state, "ROT");
                    Thread.sleep(5000);
                    state = GarageState.GATE_CLOSED;
                    break;
                }

                case GATE_CLOSED: {
                    ui.showGarageStatus(state, "ROT");
                    System.out.println("Eingabe: 4 = zurück zu WARTEN, q = Beenden");
                    String input = scanner.nextLine().trim();
                    if (isQuitCommand(input)) {
                        running = false;
                    } else if (input.equals("4")) {
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
            scanner.close();
        }
    }
}
