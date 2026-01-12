import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trein {
    private final Locomotief locomotief;
    private final List<Wagon> wagons = new ArrayList<>();

    public Trein(Locomotief locomotief) {
        this.locomotief = Objects.requireNonNull(locomotief, "locomotief mag niet leeg zijn");
    }

    public Locomotief getLocomotief() {
        return locomotief;
    }

    public List<Wagon> getWagons() {
        return List.copyOf(wagons);
    }

    public void addWagon(Wagon wagon) {
        Objects.requireNonNull(wagon, "wagon mag niet leeg zijn");
        if (wagons.size() >= locomotief.getMaxWagons()) {
            throw new IllegalStateException("Maximum aantal wagons bereikt voor " + locomotief.getType());
        }
        wagons.add(wagon);
    }

    public int getTotaleCapaciteit() {
        int total = 0;
        for (Wagon w : wagons) total += w.getCapaciteit();
        return total;
    }

    @Override
    public String toString() {
        return "Trein{loco=" + locomotief + ", wagons=" + wagons.size() + ", cap=" + getTotaleCapaciteit() + "}";
    }
}
