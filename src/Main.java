import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final DataStore store = new DataStore();

    public static void main(String[] args) {
        seed();

        while (true) {
            printMenu();
            String keuze = sc.nextLine().trim();

            try {
                switch (keuze) {
                    case "1" -> registreerPassagier();
                    case "2" -> voegPersoneelToe();          // Optie A: subclasses
                    case "3" -> maakTrein();
                    case "4" -> maakReis();
                    case "5" -> verkoopTicket();
                    case "6" -> genereerBoardinglijst();
                    case "7" -> wijsPersoneelToeAanReis();
                    case "8" -> overzicht();
                    case "0" -> {
                        System.out.println("Stop.");
                        return;
                    }
                    default -> System.out.println("Ongeldige keuze. Probeer opnieuw.");
                }
            } catch (Exception ex) {
                System.out.println("Fout: " + ex.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==== MENU ====");
        System.out.println("1) Passagier registreren");
        System.out.println("2) Personeel toevoegen (Conductor/Steward/BagagePersoneel)");
        System.out.println("3) Trein aanmaken");
        System.out.println("4) Reis aanmaken");
        System.out.println("5) Ticket verkopen");
        System.out.println("6) Boardinglijst genereren");
        System.out.println("7) Personeel toewijzen aan reis");
        System.out.println("8) Overzicht");
        System.out.println("0) Stop");
        System.out.print("Keuze: ");
    }

    private static String ask(String label) {
        System.out.print(label + ": ");
        return sc.nextLine().trim();
    }

    private static int askInt(String label) {
        while (true) {
            String s = ask(label);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Geef een geldig getal in.");
            }
        }
    }

    private static void registreerPassagier() {
        System.out.println("\n-- Passagier registreren --");
        String naam = ask("Naam");
        String achternaam = ask("Achternaam");
        String rrn = ask("Rijksregisternummer (11 cijfers)");
        LocalDate dob = LocalDate.parse(ask("Geboortedatum (YYYY-MM-DD)"));

        Passenger p = new Passenger(naam, achternaam, rrn, dob);
        store.registerPassenger(p);

        System.out.println("OK: Passagier geregistreerd.");
    }

    // Optie A: echte rol-klassen
    private static void voegPersoneelToe() {
        System.out.println("\n-- Personeel toevoegen --");
        System.out.println("1) Conductor");
        System.out.println("2) Steward");
        System.out.println("3) BagagePersoneel");
        String type = ask("Keuze");

        String naam = ask("Naam");
        String achternaam = ask("Achternaam");
        String rrn = ask("Rijksregisternummer (11 cijfers)");
        LocalDate dob = LocalDate.parse(ask("Geboortedatum (YYYY-MM-DD)"));

        Staff s = switch (type) {
            case "1" -> new Conductor(naam, achternaam, rrn, dob);
            case "2" -> new Steward(naam, achternaam, rrn, dob);
            case "3" -> new BagagePersoneel(naam, achternaam, rrn, dob);
            default -> throw new IllegalArgumentException("Ongeldige keuze.");
        };

        String cert = ask("Certificaat (enter om over te slaan)");
        if (!cert.isBlank()) {
            s.addCertificaat(cert);
        }

        store.addStaff(s);
        System.out.println("OK: Personeel toegevoegd: " + s);
    }

    private static void maakTrein() {
        System.out.println("\n-- Trein aanmaken --");
        System.out.println("LocomotiefType: 1) 373 (max 8 wagons)  2) 374 (max 10 wagons)");
        String keuze = ask("Keuze");
        LocomotiefType type = switch (keuze) {
            case "1" -> LocomotiefType.TYPE_373;
            case "2" -> LocomotiefType.TYPE_374;
            default -> throw new IllegalArgumentException("Onbekend locomotieftype.");
        };

        String locoId = ask("Locomotief ID");
        Trein trein = new Trein(new Locomotief(locoId, type));

        int n = askInt("Aantal wagons");
        for (int i = 1; i <= n; i++) {
            int cap = askInt("Capaciteit wagon " + i);
            trein.addWagon(new Wagon("W" + i, cap));
        }

        store.addTrein(trein);
        System.out.println("OK: Trein aangemaakt.");
    }

    private static void maakReis() {
        System.out.println("\n-- Reis aanmaken --");
        if (store.getTreinen().isEmpty()) {
            throw new IllegalStateException("Geen treinen beschikbaar. Maak eerst een trein.");
        }

        String code = ask("Reiscode");
        String vertrek = ask("Vertrekstation");
        String aankomst = ask("Aankomststation");
        LocalDateTime vertrekTijd = LocalDateTime.parse(ask("VertrekTijd (YYYY-MM-DDTHH:MM)"));

        System.out.println("Kies trein:");
        for (int i = 0; i < store.getTreinen().size(); i++) {
            System.out.println((i + 1) + ") " + store.getTreinen().get(i));
        }
        int idx = askInt("Nummer") - 1;

        int eerste = askInt("Plaatsen 1e klasse");
        int tweede = askInt("Plaatsen 2e klasse");

        Reis r = new Reis(code, vertrek, aankomst, vertrekTijd, store.getTreinen().get(idx), eerste, tweede);
        store.addReis(r);

        System.out.println("OK: Reis aangemaakt.");
    }

    private static void verkoopTicket() {
        System.out.println("\n-- Ticket verkopen --");
        if (store.getPassengers().isEmpty()) throw new IllegalStateException("Geen passagiers geregistreerd.");
        if (store.getReizen().isEmpty()) throw new IllegalStateException("Geen reizen beschikbaar.");

        Passenger p = kiesPassenger();
        Reis r = kiesReis();

        System.out.println("Klasse: 1) EERSTE  2) TWEEDE");
        String k = ask("Keuze");
        Klasse klasse = k.equals("1") ? Klasse.EERSTE : Klasse.TWEEDE;

        Ticket t = store.sellTicket(p.getRijksregisternummer(), r.getCode(), klasse);
        System.out.println("OK: Ticket verkocht: " + t);
    }

    private static void genereerBoardinglijst() {
        System.out.println("\n-- Boardinglijst genereren --");
        if (store.getReizen().isEmpty()) throw new IllegalStateException("Geen reizen beschikbaar.");

        Reis r = kiesReis();
        String path = BoardinglijstGenerator.generate(store, r.getCode());
        System.out.println("OK: Boardinglijst gemaakt: " + path);
    }

    private static void wijsPersoneelToeAanReis() {
        System.out.println("\n-- Personeel toewijzen aan reis --");
        if (store.getStaff().isEmpty()) throw new IllegalStateException("Geen personeel beschikbaar.");
        if (store.getReizen().isEmpty()) throw new IllegalStateException("Geen reizen beschikbaar.");

        Reis r = kiesReis();

        System.out.println("Personeel:");
        for (int i = 0; i < store.getStaff().size(); i++) {
            Staff s = store.getStaff().get(i);
            System.out.println((i + 1) + ") " + s);
        }
        int idx = askInt("Nummer") - 1;
        Staff s = store.getStaff().get(idx);

        store.wijsPersoneelToeAanReis(r.getCode(), s.getRijksregisternummer());
        System.out.println("OK: Personeel toegewezen.");
    }

    private static Passenger kiesPassenger() {
        System.out.println("Passagiers:");
        for (int i = 0; i < store.getPassengers().size(); i++) {
            Passenger p = store.getPassengers().get(i);
            System.out.println((i + 1) + ") " + p);
        }
        int idx = askInt("Nummer") - 1;
        return store.getPassengers().get(idx);
    }

    private static Reis kiesReis() {
        System.out.println("Reizen:");
        for (int i = 0; i < store.getReizen().size(); i++) {
            Reis r = store.getReizen().get(i);
            System.out.println((i + 1) + ") " + r);
        }
        int idx = askInt("Nummer") - 1;
        return store.getReizen().get(idx);
    }

    private static void overzicht() {
        System.out.println("\n--- OVERZICHT ---");
        System.out.println("Passengers: " + store.getPassengers().size());
        System.out.println("Staff: " + store.getStaff().size());
        System.out.println("Treinen: " + store.getTreinen().size());
        System.out.println("Reizen: " + store.getReizen().size());
        System.out.println("Tickets: " + store.getTickets().size());

        System.out.println("\nReizen detail:");
        for (Reis r : store.getReizen()) {
            System.out.println(r.detail());
        }
    }

    private static void seed() {
        Trein t = new Trein(new Locomotief("L-001", LocomotiefType.TYPE_373));
        t.addWagon(new Wagon("W1", 50));
        t.addWagon(new Wagon("W2", 50));
        store.addTrein(t);

        store.registerPassenger(new Passenger("Jan", "Peeters", "90010112345", LocalDate.of(1990, 1, 1)));

        Staff c = new Conductor("Els", "Vermeer", "88020254321", LocalDate.of(1988, 2, 2));
        c.addCertificaat("Basis");
        store.addStaff(c);

        store.addStaff(new Steward("Tom", "Janssens", "92030398765", LocalDate.of(1992, 3, 3)));
        store.addStaff(new BagagePersoneel("Nina", "Maes", "96050533344", LocalDate.of(1996, 5, 5)));

        Reis r = new Reis("R-001", "Brussel", "Antwerpen", LocalDateTime.now().plusDays(1), t, 20, 80);
        store.addReis(r);
    }
}
