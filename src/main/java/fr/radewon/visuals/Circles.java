package fr.radewon.visuals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;

public class Circles {

    public static void particleCircle(List<Location> l, Particle pt) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Location loc : l) {
                p.spawnParticle(pt,loc,1);
            }
        }
    }
}
