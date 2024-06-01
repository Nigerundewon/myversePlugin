package fr.radewon.data;

import fr.radewon.exceptions.ExceedsBlockLimitException;
import fr.radewon.exceptions.InvalidCoordinatesException;
import fr.radewon.exceptions.NoBlockSelectedException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class PocketMemory {
    private static final Map<Player,PocketMemory> pocketMemory = new HashMap<>();
    private static final int MAX_LIMIT = 65535;

    public static PocketMemory getPocketMemory(Player p) {
        if (!pocketMemory.containsKey(p)) {
            PocketMemory m = new PocketMemory();
            pocketMemory.put(p, m);
            return m;
        }
        return pocketMemory.get(p);
    }
    private World world;
    private Location pos1;
    private Location pos2;

    private Location pos1Stored;
    private Location pos2Stored;
    private World worldStored;
    private Location selectedSpot;

    public void setWorld(World w) {
        this.world = w;
    }
    public void setPosFirst(Location l) throws NoBlockSelectedException {
        if (l == null) throw new NoBlockSelectedException("No block found");
        this.pos1 = l;
    }
    public void setPosSecond(Location l) throws NoBlockSelectedException {
        if (l == null) throw new NoBlockSelectedException("No block found");
        this.pos2 = l;
    }

    public void setSelectedSpot(Location l) throws NoBlockSelectedException {
        if (l == null) throw new NoBlockSelectedException("No block found");
        this.selectedSpot = l;
    }
    public void store() throws InvalidCoordinatesException {
        boolean b = (this.world != null && this.pos1 != null && this.pos2 != null);
        if (b) {
            this.worldStored = this.world;
            this.pos1Stored = this.pos1;
            this.pos2Stored = this.pos2;
        }
        else {
            throw new InvalidCoordinatesException();
        }
    }

    public World getWorld() {
        return this.world;
    }
    public Location getPosFirst() {
        return this.pos1;
    }
    public Location getPosSecond() {
        return this.pos2;
    }
    public Location getStoredFirst() {
        return this.pos1Stored;
    }
    public Location getStoredSecond() {
        return this.pos2Stored;
    }
    public Location getSelectedSpot() {
        return this.selectedSpot;
    }

    public boolean hasSpot() {
        return this.selectedSpot != null;
    }

    public boolean isStored() {
        return (pos1Stored != null && pos2Stored != null && worldStored != null);
    }

    public int getXLength() {
        return abs(this.pos1Stored.getBlockX()-this.pos2Stored.getBlockX());
    }
    public int getYLength() {
        return abs(this.pos1Stored.getBlockY()-this.pos2Stored.getBlockY());
    }
    public int getZLength() {
        return abs(this.pos1Stored.getBlockZ()-this.pos2Stored.getBlockZ());
    }

    public void isUnderLimit() throws ExceedsBlockLimitException {
        int x = this.getXLength();
        int y = this.getYLength();
        int z = this.getZLength();
        if (x*y*z > PocketMemory.MAX_LIMIT) throw new ExceedsBlockLimitException();
    }
}
