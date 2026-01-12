Euromoon – Programming Advanced Project
📌 Projectomschrijving

Dit project is een proof of concept command-line Java applicatie voor Euromoon, een fictieve treinmaatschappij die internationale treinreizen binnen Europa organiseert.

De applicatie laat toe om:

* passagiers te registreren

* personeel te beheren (conducteurs, stewards, bagagepersoneel)

* treinen en reizen aan te maken

* tickets te verkopen

* boardinglijsten te genereren

Het project werd ontwikkeld in het kader van het vak Programming Advanced en focust op:

* objectgeoriënteerd ontwerp

* foutafhandeling en validatie

* planmatig werken met een product backlog

* duidelijke documentatie

⚙️ Technologieën

Programmeertaal: Java

Applicatietype: Command Line Interface (CLI)

Opslag: In-memory (geen database)

▶️ Applicatie starten

Open het project in een Java IDE (bv. IntelliJ IDEA of Eclipse)

Zorg dat Java 17 of hoger geïnstalleerd is

Start de applicatie via de Main-klasse

Bij het opstarten verschijnt een keuzemenu in de terminal

📋 Functionaliteiten (CLI-menu)

Bij het starten van de applicatie kan de gebruiker onder andere:

* een passagier registreren

* personeel toevoegen (conductor, steward, bagagepersoneel)

* een trein aanmaken met locomotief en wagons

* een reis aanmaken

* personeel toewijzen aan een reis

* een ticket verkopen (1e of 2e klasse)

* een boardinglijst genereren

* overzichten bekijken van reizen, personeel en tickets

Het menu bevat foutcontroles en blijft actief bij ongeldige invoer.

🧱 Domeinmodel (overzicht)
Personen

Person (abstract)

Passenger

Staff

Conductor

Steward

BagagePersoneel

Elke persoon heeft:

* naam

* achternaam

* rijksregisternummer (uniek)

* geboortedatum

Personeel heeft bijkomend:

* een lijst van certificaten

* een beschikbaarheidsstatus

Treinen

Locomotief

Type 373 → max. 8 wagons

Type 374 → max. 10 wagons

Wagon

* positieve capaciteit

Trein

* bestaat uit één locomotief en meerdere wagons

* totale capaciteit = som van wagon-capaciteiten

Reizen en tickets

Reis

* vertrekstation

* aankomststation

* vertrekdatum en -tijd (ISO-formaat)

* gekoppelde trein

* aantal plaatsen 1e en 2e klasse

* toegewezen personeel

* passagiers met tickets

Ticket

* gekoppeld aan één passagier en één reis

* klasse: eerste of tweede

🎫 Ticketverkoop

Bij het verkopen van een ticket:

* moet de passagier geregistreerd zijn

* moet de reis bestaan

* wordt gecontroleerd of er nog plaats is in de gekozen klasse

* kan een passagier slechts één ticket per reis hebben

Bij fouten (volle klasse, niet-bestaande passagier, …) wordt een duidelijke foutmelding getoond.

📄 Boardinglijst

Bij het genereren van een boardinglijst wordt een tekstbestand aangemaakt met de naam:

boarding_<reisCode>_<yyyyMMdd_HHmm>.txt


Voorbeeld:

boarding_R-001_20260305_1230.txt


Het bestand bevat enkel passagiers met een geldig ticket voor de gekozen reis, met per passagier:

* naam

* achternaam

* rijksregisternummer

* reisklasse

Als er geen tickets zijn voor de reis, wordt geen bestand aangemaakt en verschijnt een foutmelding.

🧪 Foutafhandeling

De applicatie voorziet foutcontroles voor onder andere:

* dubbele rijksregisternummers

* ongeldige invoer (lege velden, fout formaat)

* geboortedatum in de toekomst

* vertrek- en aankomststation gelijk

* vertrekdatum in het verleden

* overschrijden van trein- of klassecapaciteit

* toewijzen van niet-beschikbaar personeel

* niet-bestaande passagiers of reizen

De applicatie crasht niet bij foutieve invoer.

🗂️ Planning & structuur

De product backlog is toegevoegd als Excel-bestand

User stories en acceptance criteria zijn uitgewerkt

De code volgt een duidelijke scheiding tussen:

* domeinlogica

* opslag (in-memory)

* gebruikersinteractie (CLI)

📚 Bronnen & AI-tools

Voor dit project werden volgende bronnen geraadpleegd:

* Oracle Java Documentation
https://docs.oracle.com/en/java/

* Stack Overflow
https://stackoverflow.com/

* ChatGPT (OpenAI)

ChatGPT werd gebruikt ter ondersteuning bij:

* het formuleren en herwerken van user stories en acceptance criteria

* het controleren van ontwerpkeuzes en validatieregels

* het verduidelijken van Java- en OOP-concepten

Alle code is begrepen en bewust geïmplementeerd door de auteur.

👤 Auteur

Naam: Fouad Badi
Opleiding: Graduaat Programmeren
Vak: Programming Advanced