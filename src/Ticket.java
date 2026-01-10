public class Ticket {
    private final Passenger passenger;
    private final Klasse klasse;
    private final Reis reis;

    public Ticket(Passenger passenger, Klasse klasse, Reis reis){
        this.passenger = passenger;
        this.klasse = klasse;
        this.reis = reis;
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
    public String toString(){
        return "Ticket{" + "klasse=" + klasse + "}";
    }
}
