import java.util.Objects;

public class Locomotief {
    private final LocomotiefType type;

    public Locomotief(LocomotiefType type){
        this.type = Objects.requireNonNull(type, "type mag niet leeg zijn");
    }

    public LocomotiefType getType() {
        return type;
    }

    public int getMaxWagons(){
        return type.getMaxWagons();
    }

    @Override
    public String toString() {
        return "Locomotief{" + type + ", maxWagons=" + getMaxWagons() + "}";
    }
}
