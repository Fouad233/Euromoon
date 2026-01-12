import java.time.LocalDate;

public class BagagePersoneel extends Staff {
    public BagagePersoneel(String naam, String achternaam, String rrn, LocalDate dob) {
        super("bagagepersoneel", naam, achternaam, rrn, dob);
    }
}
