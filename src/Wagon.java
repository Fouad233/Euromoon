public class Wagon {
    private static final int capaciteit = 80;

    public static int getCapaciteit() {
        return capaciteit;
    }

    @Override
    public String toString(){
        return "Wagon{capaciteit=" + capaciteit + "}";
    }
}
