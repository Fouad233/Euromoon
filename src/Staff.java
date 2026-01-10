import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Staff extends Person{
    private final List<String> certificaties = new ArrayList<>();

    protected Staff(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum){
        super(naam, achternaam, rijksregisternummer, geboortedatum);
    }

    public List<String> getCertificaties(){
        return Collections.unmodifiableList(certificaties);
    }

    public void addCertificatie(String certificatie){
        if (certificatie == null || certificatie.isBlank()){
            throw new IllegalArgumentException("Certificatie mag niet leeg zijn");
        }
        certificaties.add(certificatie);
    }
}
