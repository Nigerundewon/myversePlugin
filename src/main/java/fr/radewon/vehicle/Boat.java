package fr.radewon.vehicle;

public class Boat extends Vehicles {
    private int texture;

    public Boat(int i, String description) {
        super(description, VehicleType.VESSEL);
        this.texture = i;
    }

    public Boat(int i) {
        this(i,"NULL");
    }

    public Boat() {
        super();
    }
}
