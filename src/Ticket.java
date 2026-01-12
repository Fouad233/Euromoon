import java.util.Objects;

public class Ticket {
    private final Passenger passenger;
    private final Klasse klasse;
    private final Reis reis;

    public Ticket(Passenger passenger, Klasse klasse, Reis reis) {
        this.passenger = Objects.requireNonNull(passenger, "passenger mag niet leeg zijn");
        this.klasse = Objects.requireNonNull(klasse, "klasse mag niet leeg zijn");
        this.reis = Objects.requireNonNull(reis, "reis mag niet leeg zijn");
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Klasse getKlasse() {
        return klasse;
    }

    public Reis getReis() {
        return reis;
    }

    @Override
    public String toString() {
        return "Ticket{reis=" + reis.getCode() + ", rrn=" + passenger.getRijksregisternummer() + ", klasse=" + klasse + "}";
    }
}
