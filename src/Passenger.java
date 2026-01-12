import java.time.LocalDate;

public class Passenger extends Person {
    private Ticket ticket;

    public Passenger(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijksregisternummer, geboortedatum);
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
