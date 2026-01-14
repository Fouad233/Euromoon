import java.time.LocalDate;

public class Conductor extends Staff {
    public Conductor(String naam, String achternaam, String rrn, LocalDate dob) {
        super("conductor", naam, achternaam, rrn, dob);
    }
}
