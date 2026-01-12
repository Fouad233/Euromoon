public enum LocomotiefType {
    TYPE_373(8),
    TYPE_374(10);

    private final int maxWagons;

    LocomotiefType(int maxWagons) {
        this.maxWagons = maxWagons;
    }

    public int getMaxWagons() {
        return maxWagons;
    }
}
