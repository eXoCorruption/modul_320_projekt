public class GarageUI {

    public GarageUI() {
    }

    public void showMessage(String title, String msg) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.println(msg);
    }

    public void showGarageStatus(String state, String ampel) {
        System.out.println();
        System.out.println("GARAGENEINFAHRT TERMINAL");
        System.out.println("========================");
        System.out.println("Zustand: " + state);
        System.out.println("Ampel: " + ampel);
    }

    public void close() {
        // Keine Ressourcen zu schließen in der Konsolen-Variante
    }
}