package fr.radewon.vehicle;

public class Car extends Vehicles {
    private int texture;

    public Car(int i, String description) {
        super(description, VehicleType.TERRESTRIAL);
        this.texture = i;
    }

    public Car(int i) {
        this(i,"NULL");
    }

    public Car() {
        super();
    }
}
