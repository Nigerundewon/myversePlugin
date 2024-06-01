package fr.radewon.vehicle;

import fr.radewon.commands.Vehicle;
import fr.radewon.data.ThrownItem;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Vehicles {

    private static int INC = 0;
    public static final Map<Integer, Vehicles> vehiclesList = new HashMap<>();

    private int ID;
    private String description;
    private VehicleType type;
    private double maxSpeed = 10.0;
    private boolean spawned = false;

    public Vehicles(String s, VehicleType t) {
        this.ID = INC++;
        this.description = s;
        this.type = t;
        vehiclesList.put(this.ID,this);
    }

    public Vehicles(VehicleType t) {
        this("Null",t);
    }

    public Vehicles() {
        this(VehicleType.TERRESTRIAL);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isSpawned() {
        return this.spawned;
    }

    public int getID() {
        return this.ID;
    }

    public VehicleType getType() {
        return this.type;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(double d) {
        this.maxSpeed = d;
    }

    public Vehicles getVehicleFromID(int i) {
        if (vehiclesList.containsKey(i)) {
            return vehiclesList.get(i);
        }
        return null;
    }

    public void putVehicle(Vehicles v) {
        vehiclesList.put(v.getID(), v);
    }

    public void spawn() {

    }

    public void kill() {

    }
}
