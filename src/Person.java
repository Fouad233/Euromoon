import java.time.LocalDate;
import java.util.Objects;

public abstract class Person {
    private final String naam;
    private final String achternaam;
    private final String rijksregisternummer;
    private final LocalDate geboortedatum;

    protected Person(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        this.naam = requireNonBlank(naam, "naam");
        this.achternaam = requireNonBlank(achternaam, "achternaam");
        this.rijksregisternummer = normaliseerRrn(rijksregisternummer);
        this.geboortedatum = Objects.requireNonNull(geboortedatum, "geboortedatum mag niet leeg zijn");
        if (this.geboortedatum.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Geboortedatum mag niet in de toekomst liggen.");
        }
    }

    private static String requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is verplicht.");
        }
        return value.trim();
    }

    private static String normaliseerRrn(String rrn) {
        if (rrn == null) {
            throw new IllegalArgumentException("rijksregisternummer is verplicht.");
        }
        String digits = rrn.replaceAll("\\D", "");
        if (digits.length() != 11) {
            throw new IllegalArgumentException("Rijksregisternummer moet exact 11 cijfers bevatten.");
        }
        return digits;
    }

    public String getNaam() { return naam; }
    public String getAchternaam() { return achternaam; }
    public String getRijksregisternummer() { return rijksregisternummer; }
    public LocalDate getGeboortedatum() { return geboortedatum; }

    @Override
    public String toString() {
        return naam + " " + achternaam + " (RRN: " + rijksregisternummer + ")";
    }
}
