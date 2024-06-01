package fr.radewon.visuals;

import fr.radewon.util.ShapeMaker;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EMP {

    private Plugin plugin;

    public EMP(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startTask(Location center, int size, Material m, boolean hollow) {
        BukkitRunnable runnable = new BukkitRunnable() {
            List<Location> loc = new ArrayList<Location>();
            int counter = 0;
            @Override
            public void run() {
                if (counter < size) {
                    ShapeMaker.resetSphere(loc);
                    loc = ShapeMaker.sphere(center, counter, hollow);
                    ShapeMaker.generateSphere(loc, m);
                    counter++;
                } else {
                    ShapeMaker.resetSphere(loc);
                    cancel();
                }
            }
        };

        runnable.runTaskTimer(plugin, 0, 4);
    }

    private void delay(int ticks) {
        try {
            Thread.sleep(ticks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
