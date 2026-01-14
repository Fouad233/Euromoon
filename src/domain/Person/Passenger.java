import java.time.LocalDate;
/**
 * Een passagier die tickets kan aankopen voor reizen.
 * Wordt gekoppeld aan een Ticket om te kunnen boarden.
 */
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
