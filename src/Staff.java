import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Staff extends Person {
    private final String rol;
    private final List<String> certificaten = new ArrayList<>();
    private boolean beschikbaar = true;

    public Staff(String rol, String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijksregisternummer, geboortedatum);
        if (rol == null || rol.isBlank()) {
            throw new IllegalArgumentException("rol is verplicht.");
        }
        this.rol = rol.trim();
    }

    public String getRol() {
        return rol;
    }

    public void addCertificaat(String certificaat) {
        if (certificaat == null || certificaat.isBlank()) {
            throw new IllegalArgumentException("Certificaat mag niet leeg zijn.");
        }
        certificaten.add(certificaat.trim());
    }

    public List<String> getCertificaten() {
        return Collections.unmodifiableList(certificaten);
    }

    public boolean isBeschikbaar() {
        return beschikbaar;
    }

    public void setBeschikbaar(boolean beschikbaar) {
        this.beschikbaar = beschikbaar;
    }

    @Override
    public String toString() {
        return super.toString() + " | rol=" + rol + " | beschikbaar=" + beschikbaar;
    }
}
