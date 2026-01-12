import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataStore {
    private final List<Passenger> passengers = new ArrayList<>();
    private final List<Staff> staff = new ArrayList<>();
    private final List<Trein> treinen = new ArrayList<>();
    private final List<Reis> reizen = new ArrayList<>();
    private final List<Ticket> tickets = new ArrayList<>();

    public List<Passenger> getPassengers() { return List.copyOf(passengers); }
    public List<Staff> getStaff() { return List.copyOf(staff); }
    public List<Trein> getTreinen() { return List.copyOf(treinen); }
    public List<Reis> getReizen() { return List.copyOf(reizen); }
    public List<Ticket> getTickets() { return List.copyOf(tickets); }

    public void registerPassenger(Passenger p) {
        Objects.requireNonNull(p);
        checkUniekeRrn(p.getRijksregisternummer());
        passengers.add(p);
    }

    public void addStaff(Staff s) {
        Objects.requireNonNull(s);
        checkUniekeRrn(s.getRijksregisternummer());
        staff.add(s);
    }

    private void checkUniekeRrn(String rrn) {
        boolean inPassengers = passengers.stream().anyMatch(x -> x.getRijksregisternummer().equals(rrn));
        boolean inStaff = staff.stream().anyMatch(x -> x.getRijksregisternummer().equals(rrn));
        if (inPassengers || inStaff) {
            throw new IllegalArgumentException("Rijksregisternummer bestaat al.");
        }
    }

    public void addTrein(Trein t) {
        Objects.requireNonNull(t);
        treinen.add(t);
    }

    public void addReis(Reis r) {
        Objects.requireNonNull(r);
        if (reizen.stream().anyMatch(x -> x.getCode().equalsIgnoreCase(r.getCode()))) {
            throw new IllegalArgumentException("Reiscode bestaat al.");
        }
        reizen.add(r);
    }

    public Passenger findPassengerByRrn(String rrn) {
        return passengers.stream()
                .filter(p -> p.getRijksregisternummer().equals(rrn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Niet-geregistreerde passagier."));
    }

    public Staff findStaffByRrn(String rrn) {
        return staff.stream()
                .filter(s -> s.getRijksregisternummer().equals(rrn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Personeel niet gevonden."));
    }

    public Reis findReisByCode(String code) {
        return reizen.stream()
                .filter(r -> r.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reis niet gevonden."));
    }

    // US2: personeel niet beschikbaar => niet toewijzen
    public void wijsPersoneelToeAanReis(String reisCode, String staffRrn) {
        Reis r = findReisByCode(reisCode);
        Staff s = findStaffByRrn(staffRrn);

        if (!s.isBeschikbaar()) {
            throw new IllegalStateException("Personeel niet beschikbaar.");
        }

        r.voegPersoneelToe(s);
        s.setBeschikbaar(false);
    }

    // US5: ticket verkopen met checks
    public Ticket sellTicket(String passengerRrn, String reisCode, Klasse klasse) {
        Passenger p = findPassengerByRrn(passengerRrn);
        Reis r = findReisByCode(reisCode);

        boolean heeftTicketVoorReis = tickets.stream().anyMatch(t -> t.getPassenger().equals(p) && t.getReis().equals(r));
        if (heeftTicketVoorReis) {
            throw new IllegalStateException("Passagier heeft al een ticket voor deze reis.");
        }

        if (!r.heeftPlaats(klasse)) {
            throw new IllegalStateException("Geen plaatsen meer beschikbaar.");
        }

        Ticket ticket = new Ticket(p, klasse, r);
        tickets.add(ticket);

        p.setTicket(ticket);

        if (!r.addPassenger(p)) {
            throw new IllegalStateException("Ticket werd niet correct gekoppeld aan de reis.");
        }

        return ticket;
    }
}
