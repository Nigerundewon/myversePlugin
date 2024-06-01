package fr.radewon.data;

import fr.radewon.MyversePlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class ThrownItem {
    private static final Map<Player,ThrownItem> itemMemory = new HashMap<>();
    private MyversePlugin plugin;
    public ThrownItem(MyversePlugin plugin) {
        this.plugin = plugin;
    }
    private ItemStack item;
    private Location origin;
    private Location dest;
    private double speed = 0;
    public void setItem(ItemStack i) {
        this.item = i;
    }

    public void setOrigin(Location l) {
        l.setY(l.getY()+1);
        this.origin = l;
    }

    public void setDest(Location l) {
        l.setY(l.getY()+1);
        this.dest = l;
    }

    public void setSpeed(double d) {
        this.speed = d;
    }

    public void launch() {
        double dx = this.dest.getX() - this.origin.getX();
        double dy = this.dest.getY() - this.origin.getY();
        double dz = this.dest.getZ() - this.origin.getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        if (this.speed <= 0) this.speed = 1.0;

        double time = distance / this.speed;

        double initialVelocityX = dx / time;
        double initialVelocityY = dy / time;
        double initialVelocityZ = dz / time;

        Item i = this.origin.getWorld().dropItem(this.origin, item);
        i.setVelocity(new Vector(initialVelocityX, 2.0*initialVelocityY, initialVelocityZ).normalize().multiply(speed*distance/24));

        new BukkitRunnable() {

            @Override
            public void run() {
                double dx = dest.getX() - i.getLocation().getBlockX();
                //double dy = dest.getY() - i.getLocation().getBlockY();
                double dz = dest.getZ() - i.getLocation().getBlockZ();
                double newHorizontalDistance = Math.sqrt(dx * dx + dz * dz);
                if (i.isOnGround()) {
                    i.setVelocity(new Vector(0,0,0));
                    this.cancel();
                    return;
                }
                else if (i.getLocation().getY()/origin.getY() > 0.50){
                    i.setVelocity(new Vector(initialVelocityX,-(Math.cos(Math.PI * newHorizontalDistance/horizontalDistance)),initialVelocityZ));
                }

            }
        }.runTaskTimer(plugin, 2, 1);
    }
}
