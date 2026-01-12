public class Wagon {
    private final String id;
    private final int capaciteit;

    public Wagon(String id, int capaciteit) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Wagon id is verplicht.");
        }
        if (capaciteit <= 0) {
            throw new IllegalArgumentException("Capaciteit moet groter zijn dan 0.");
        }
        this.id = id.trim();
        this.capaciteit = capaciteit;
    }

    public String getId() {
        return id;
    }

    public int getCapaciteit() {
        return capaciteit;
    }
}
