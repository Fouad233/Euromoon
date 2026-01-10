import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reis {

    private final String code;
    private final String vertrekStation;
    private final String aankomstStation;
    private final LocalDateTime vertrekTijd;
    private final Trein trein;

    private final int plaatsenEersteKlasse;
    private final int plaatsenTweedeKlasse;

    private Conductor conductor;
    private final List<Steward> stewards = new ArrayList<>();

    private final List<Passenger> passengers = new ArrayList<>();

    public Reis(String code,
                String vertrekStation,
                String aankomstStation,
                LocalDateTime vertrekTijd,
                Trein trein,
                int plaatsenEersteKlasse,
                int plaatsenTweedeKlasse) {

        this.code = requireNonBlank(code, "code");
        this.vertrekStation = requireNonBlank(vertrekStation, "vertrekStation");
        this.aankomstStation = requireNonBlank(aankomstStation, "aankomstStation");
        this.vertrekTijd = Objects.requireNonNull(vertrekTijd, "vertrekTijd mag niet null zijn");
        this.trein = Objects.requireNonNull(trein, "trein mag niet null zijn");

        if (plaatsenEersteKlasse < 0 || plaatsenTweedeKlasse < 0) {
            throw new IllegalArgumentException("plaatsen mogen niet negatief zijn");
        }
        this.plaatsenEersteKlasse = plaatsenEersteKlasse;
        this.plaatsenTweedeKlasse = plaatsenTweedeKlasse;
    }

    private static String requireNonBlank(String s, String field) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(field + " mag niet leeg zijn");
        }
        return s.trim();
    }

    public String getCode() { return code; }
    public String getVertrekStation() { return vertrekStation; }
    public String getAankomstStation() { return aankomstStation; }
    public LocalDateTime getVertrekTijd() { return vertrekTijd; }
    public Trein getTrein() { return trein; }

    public Conductor getConductor() { return conductor; }
    public List<Steward> getStewards() { return List.copyOf(stewards); }
    public List<Passenger> getPassengers() { return List.copyOf(passengers); }

    public void setConductor(Conductor conductor) {
        this.conductor = Objects.requireNonNull(conductor, "conductor mag niet null zijn");
    }

    public void addSteward(Steward steward) {
        stewards.add(Objects.requireNonNull(steward, "steward mag niet null zijn"));
    }

    public boolean hasAvailableSeat(Klasse klasse) {
        long bookedEerste = passengers.stream()
                .filter(p -> p.getTicket() != null && p.getTicket().getKlasse() == Klasse.EERSTE)
                .count();

        long bookedTweede = passengers.stream()
                .filter(p -> p.getTicket() != null && p.getTicket().getKlasse() == Klasse.TWEEDE)
                .count();

        return switch (klasse) {
            case EERSTE -> bookedEerste < plaatsenEersteKlasse;
            case TWEEDE -> bookedTweede < plaatsenTweedeKlasse;
        };
    }

    public boolean addPassenger(Passenger passenger) {
        Objects.requireNonNull(passenger, "passenger mag niet null zijn");

        Ticket t = passenger.getTicket();
        if (t == null) {
            System.out.println("Fout: Passenger heeft geen ticket.");
            return false;
        }
        if (t.getReis() != this) {
            System.out.println("Fout: Ticket hoort niet bij deze reis.");
            return false;
        }
        if (!hasAvailableSeat(t.getKlasse())) {
            System.out.println("Fout: Geen plaatsen meer beschikbaar in " + t.getKlasse());
            return false;
        }

        return passengers.add(passenger);
    }

    public boolean isReadyToDepart() {
        return conductor != null && stewards.size() >= 3;
    }

    @Override
    public String toString() {
        return "Reis{" +
                "code='" + code + '\'' +
                ", " + vertrekStation + " -> " + aankomstStation +
                ", vertrekTijd=" + vertrekTijd +
                ", trein=" + trein +
                ", conductor=" + (conductor != null ? conductor.getNaam() : "GEEN") +
                ", stewards=" + stewards.size() +
                ", passengers=" + passengers.size() +
                '}';
    }
}
