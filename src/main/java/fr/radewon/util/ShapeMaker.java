package fr.radewon.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ShapeMaker {

    public static List<Location> sphere (Location l, int size, boolean hollow) {
        List<Location> blocks = new ArrayList<Location>();
        int bX = l.getBlockX();
        int bY = l.getBlockY();
        int bZ = l.getBlockZ();
        double distance;

        for (int x = bX-size ; x <= bX+size ; x++) {
            for (int y = bY-size ; y <= bY+size ; y++) {
                for (int z = bZ-size ; z <= bZ+size ; z++) {
                    distance = ((bX-x)*(bX-x))+((bZ-z)*(bZ-z))+((bY-y)*(bY-y));

                    if (distance < size * size && !l.getBlock().equals(Material.AIR) && !(hollow && distance < ((size-1) * (size-1)))) {
                        Location loc = new Location(l.getWorld(),x,y,z);
                        blocks.add(loc);
                    }
                }
            }
        }
        return blocks;
    }

    public static List<Location> sphere(Location l, int size) {
        return sphere(l, size, false);
    }

    public static void generateSphere(List<Location> l, Material m) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Location loc : l) {
                p.sendBlockChange(loc, m.createBlockData());
            }
        }
    }

    public static void resetSphere(List<Location> l) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Location loc : l) {
                p.sendBlockChange(loc, loc.getBlock().getBlockData());
            }
        }
    }

    public static List<Location> circle (int radius, Location l) {
        List<Location> blocks = new ArrayList<Location>();
        int points = 32;
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = l.getX() + radius * Math.cos(angle);
            double z = l.getZ() + radius * Math.sin(angle);
            int y = l.getBlockY();

            blocks.add(new Location(l.getWorld(), x, y, z));
        }
        return blocks;
    }

    public static Location blockForward(int distance, Location l) {
        Vector dir = l.getDirection();
        dir.normalize();
        return l.add(dir.multiply(distance));
    }
}
