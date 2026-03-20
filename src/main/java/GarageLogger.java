import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GarageLogger {
    
    private static final String LOG_FILE            = "./protocol/garage_protocol.txt";
    private static final String EIN_AUSFAHRT_FILE   = "./protocol/einausfahrt_protocol.txt";
    private static final DateTimeFormatter TIME_FORMAT     = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public GarageLogger() {
        initFile(LOG_FILE,           "=== GARAGENTOR PROTOKOLL ===");
        initFile(EIN_AUSFAHRT_FILE,  "=== EIN-/AUSFAHRT PROTOKOLL ===");
    }

    private void initFile(String file, String title) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(title + "\n");
            writer.write("Start: " + LocalDateTime.now().format(DATE_TIME_FORMAT) + "\n");
            writer.write("================================\n\n");
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen der Datei " + file + ": " + e.getMessage());
        }
    }

    public void log(String message) {
        String logEntry = "[" + LocalDateTime.now().format(TIME_FORMAT) + "] " + message;
        writeToFile(LOG_FILE, logEntry);
    }

    public void logEinfahrt() {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMAT);
        writeToFile(EIN_AUSFAHRT_FILE, "[" + timestamp + "] EINFAHRT erfasst");
        log("*** EINFAHRT erfasst: " + timestamp + " ***");
    }

    public void logAusfahrt() {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMAT);
        writeToFile(EIN_AUSFAHRT_FILE, "[" + timestamp + "] AUSFAHRT erfasst");
        log("*** AUSFAHRT erfasst: " + timestamp + " ***");
    }

    private void writeToFile(String file, String line) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(line + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in " + file + ": " + e.getMessage());
        }
    }

    public void close() {
        closeFile(LOG_FILE);
        closeFile(EIN_AUSFAHRT_FILE);
    }

    private void closeFile(String file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write("\n================================\n");
            writer.write("Ende: " + LocalDateTime.now().format(DATE_TIME_FORMAT) + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Fehler beim Abschliessen von " + file + ": " + e.getMessage());
        }
    }
}
