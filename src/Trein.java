import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trein {
    private final Locomotief locomotief;
    private final List<Wagon> wagons = new ArrayList<>();

    public Trein(Locomotief locomotief){
        this.locomotief = Objects.requireNonNull(locomotief, "locomotief mag niet leeg zijn");
    }

    public Locomotief getLocomotief() {
        return locomotief;
    }
    public List<Wagon> getWagons() {
        return List.copyOf(wagons);
    }

    public boolean addWagon(Wagon wagon){
        Objects.requireNonNull(wagon, "wagon mag niet leeg zijn");

        if (wagons.size() >= locomotief.getMaxWagons()){
            System.out.println("Maximum aantal wagons is bereikt voor "+ locomotief.getType());
            return false;
        }
        wagons.add(wagon);
        return true;
    }

    public int getTotalCapaciteit(){
        return wagons.stream()
                .mapToInt(Wagon::getCapaciteit)
                .sum();
    }

    @Override
    public String toString(){
        return "Trein( locomotief =" + locomotief + ", wagons =" + wagons.size() + ", capaciteit =" + getTotalCapaciteit() + "}";
    }
}
