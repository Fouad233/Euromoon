import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Eén geplande reis tussen twee stations op een bepaald tijdstip.
 * Beheert de gekoppelde trein, personeel en de lijst passagiers met een geldig ticket.
 */
public class Reis {
    private final String code;
    private final String vertrekStation;
    private final String aankomstStation;
    private final LocalDateTime vertrekTijd;
    private final Trein trein;

    private final int plaatsenEerste;
    private final int plaatsenTweede;

    private final List<Staff> personeel = new ArrayList<>();
    private final List<Passenger> passengers = new ArrayList<>();

    public Reis(String code, String vertrekStation, String aankomstStation, LocalDateTime vertrekTijd,
                Trein trein, int plaatsenEerste, int plaatsenTweede) {

        this.code = requireNonBlank(code, "code");
        this.vertrekStation = requireNonBlank(vertrekStation, "vertrekStation");
        this.aankomstStation = requireNonBlank(aankomstStation, "aankomstStation");

        if (this.vertrekStation.equalsIgnoreCase(this.aankomstStation)) {
            throw new IllegalArgumentException("Vertrek en aankomst mogen niet hetzelfde zijn.");
        }

        this.vertrekTijd = Objects.requireNonNull(vertrekTijd, "vertrektijd mag niet leeg zijn");
        if (this.vertrekTijd.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Datum/tijd mag niet in het verleden liggen.");
        }

        this.trein = Objects.requireNonNull(trein, "trein mag niet leeg zijn");

        if (plaatsenEerste < 0 || plaatsenTweede < 0) {
            throw new IllegalArgumentException("Plaatsen mogen niet negatief zijn.");
        }

        int totalSeats = plaatsenEerste + plaatsenTweede;
        int trainCap = trein.getTotaleCapaciteit();
        if (trainCap > 0 && totalSeats > trainCap) {
            throw new IllegalArgumentException("Plaatsen (" + totalSeats + ") mogen niet groter zijn dan treincapaciteit (" + trainCap + ").");
        }

        this.plaatsenEerste = plaatsenEerste;
        this.plaatsenTweede = plaatsenTweede;
    }

    private static String requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is verplicht.");
        }
        return value.trim();
    }

    public String getCode() { return code; }
    public String getVertrekStation() { return vertrekStation; }
    public String getAankomstStation() { return aankomstStation; }
    public LocalDateTime getVertrekTijd() { return vertrekTijd; }
    public Trein getTrein() { return trein; }

    public List<Staff> getPersoneel() { return List.copyOf(personeel); }
    public List<Passenger> getPassengers() { return List.copyOf(passengers); }

    public void voegPersoneelToe(Staff s) {
        Objects.requireNonNull(s, "personeel mag niet leeg zijn");
        if (personeel.contains(s)) return;
        personeel.add(s);
    }

    public boolean heeftPlaats(Klasse klasse) {
        int geboektEerste = 0;
        int geboektTweede = 0;

        for (Passenger p : passengers) {
            if (p.getTicket() == null) continue;
            if (p.getTicket().getKlasse() == Klasse.EERSTE) geboektEerste++;
            if (p.getTicket().getKlasse() == Klasse.TWEEDE) geboektTweede++;
        }

        return switch (klasse) {
            case EERSTE -> geboektEerste < plaatsenEerste;
            case TWEEDE -> geboektTweede < plaatsenTweede;
        };
    }

    public boolean addPassenger(Passenger passenger) {
        Objects.requireNonNull(passenger, "passenger mag niet leeg zijn");
        Ticket t = passenger.getTicket();
        if (t == null) return false;
        if (t.getReis() != this) return false;
        if (!heeftPlaats(t.getKlasse())) return false;
        if (passengers.contains(passenger)) return false;

        passengers.add(passenger);
        return true;
    }

    public String detail() {
        return toString() + " | personeel=" + personeel.size() + " | passengers=" + passengers.size();
    }

    @Override
    public String toString() {
        return "Reis{" + code + ": " + vertrekStation + "->" + aankomstStation + ", vertrek=" + vertrekTijd + "}";
    }
}
