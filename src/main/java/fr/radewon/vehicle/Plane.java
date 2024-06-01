package fr.radewon.vehicle;

public class Plane extends Vehicles {
    private int texture;

    public Plane(int i, String description) {
        super(description, VehicleType.AIRCRAFT);
        this.texture = i;
    }

    public Plane(int i) {
        this(i,"NULL");
    }

    public Plane() {
        super();
    }
}
