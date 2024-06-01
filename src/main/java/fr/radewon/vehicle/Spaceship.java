package fr.radewon.vehicle;

public class Spaceship extends Vehicles {
    private int texture;

    public Spaceship(int i, String description) {
        super(description, VehicleType.AIRCRAFT);
        this.texture = i;
    }

    public Spaceship(int i) {
        this(i,"NULL");
    }

    public Spaceship() {
        super();
    }
}
