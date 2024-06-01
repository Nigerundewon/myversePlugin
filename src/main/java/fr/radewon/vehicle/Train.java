package fr.radewon.vehicle;

import fr.radewon.vehicle.train.TrainCar;
import fr.radewon.vehicle.train.TrainEngine;

import java.util.List;

public class Train extends Vehicles {
    private int texture;
    private TrainEngine engine;
    private List<TrainCar> trainCarsList;

    public Train(int i, String description) {
        super(description, VehicleType.TERRESTRIAL);
        this.texture = i;
    }

    public Train(int i) {
        this(i,"NULL");
    }

    public Train() {
        super();
    }
}
