import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
/**
 * Genereert een boardinglijstbestand voor een gegeven reis.
 * Output is een tekstbestand met passagiersgegevens en bestandsnaam volgens station+datum.
 */
class BoardinglijstGenerator {

    public static String generate(DataStore store, String reisCode) {
        Reis r = store.findReisByCode(reisCode);

        if (r.getPassengers().isEmpty()) {
            throw new IllegalStateException("Reis zonder tickets: boardinglijst niet mogelijk.");
        }

        String ts = r.getVertrekTijd().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String filename = "boarding_" + r.getCode() + "_" + ts + ".txt";

        StringBuilder sb = new StringBuilder();
        sb.append("BOARDINGLIJST\n");
        sb.append("Reis: ").append(r.getCode()).append("\n");
        sb.append("Route: ").append(r.getVertrekStation()).append(" -> ").append(r.getAankomstStation()).append("\n");
        sb.append("Vertrek: ").append(r.getVertrekTijd()).append("\n\n");

        for (Passenger p : r.getPassengers()) {
            Ticket t = p.getTicket();
            if (t == null || t.getReis() != r) continue;

            sb.append(p.getNaam()).append(" ").append(p.getAchternaam())
                    .append(" | RRN: ").append(p.getRijksregisternummer())
                    .append(" | Klasse: ").append(t.getKlasse())
                    .append("\n");
        }

        try {
            Path out = Path.of(filename);
            Files.writeString(out, sb.toString());
            return out.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Kon boardinglijst niet schrijven.", e);
        }
    }
}
