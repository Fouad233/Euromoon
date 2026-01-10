public class Ticket {
    private Passenger passenger;
    private String klasse;
    private Reis reis;

    public Ticket(Passenger passenger, String klasse, Reis reis){
        this.passenger = passenger;
        this.klasse = klasse;
        this.reis = reis;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getKlasse() {
        return klasse;
    }

    public Reis getReis() {
        return reis;
    }

    @Override
    public String toString(){
        return "Klasse: " + klasse;
    }
}
