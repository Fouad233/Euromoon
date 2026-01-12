import java.time.LocalDate;

public class Steward extends Staff {
    public Steward(String naam, String achternaam, String rrn, LocalDate dob) {
        super("steward", naam, achternaam, rrn, dob);
    }
}
