import java.time.LocalDate;

public  abstract class Person {
    protected String naam;
    protected String achternaam;
    protected String rijksregisternummer;
    protected LocalDate geboortedatum;

    public Person(String naam, String achternaam, String rijksregisternummer, LocalDate geboortedatum){
        this.naam = naam;
        this.achternaam = achternaam;
        this.rijksregisternummer = rijksregisternummer;
        this.geboortedatum = geboortedatum;
    }

    public String getNaam() {
        return naam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getRijksregisternummer() {
        return rijksregisternummer;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    @Override
    public String toString(){
        return "Naam: " + naam + ", Achternaam: " + achternaam + ", Rijksregisternummer: " + rijksregisternummer + ", Geboortedatum: " + geboortedatum;
    }
}
