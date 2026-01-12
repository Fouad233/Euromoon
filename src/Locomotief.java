import java.util.Objects;

public class Locomotief {
    private final String id;
    private final LocomotiefType type;

    public Locomotief(String id, LocomotiefType type) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Locomotief id is verplicht.");
        }
        this.id = id.trim();
        this.type = Objects.requireNonNull(type, "type mag niet leeg zijn");
    }

    public String getId() {
        return id;
    }

    public LocomotiefType getType() {
        return type;
    }

    public int getMaxWagons() {
        return type.getMaxWagons();
    }

    @Override
    public String toString() {
        return "Locomotief{id='" + id + "', type=" + type + "}";
    }
}
