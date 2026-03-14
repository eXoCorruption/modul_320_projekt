import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GarageLogger {
    
    private static final String LOG_FILE = "garage_protocol.txt";
    private static final DateTimeFormatter TIME_FORMAT = 
        DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public GarageLogger() {
        // Datei initialisieren
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writer.write("=== GARAGENTOR PROTOKOLL ===\n");
            writer.write("Start: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "\n");
            writer.write("================================\n\n");
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Log-Datei: " + e.getMessage());
        }
    }
    
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);
        String logEntry = "[" + timestamp + "] " + message;
        
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in Log-Datei: " + e.getMessage());
        }
    }
    
    public void close() {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write("\n================================\n");
            writer.write("Ende: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Fehler beim Abschließen der Log-Datei: " + e.getMessage());
        }
    }
}
