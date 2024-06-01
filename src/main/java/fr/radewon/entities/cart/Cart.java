package fr.radewon.entities.cart;

import fr.radewon.MyversePlugin;
import fr.radewon.commands.MyverseEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private MyversePlugin plugin;
    protected ArmorStand anchorEntity;
    protected ArmorStand pullEntity;
    protected List<Entity> passengers;
    protected List<ArmorStand> seats;
    protected static Map<ArmorStand, Cart> anchorMap = new HashMap<>();
    protected static Map<ArmorStand, Cart> pullMap = new HashMap<>();
    protected Player puller;

    public Cart(Location l, MyversePlugin plugin) {
        this.plugin = plugin;
        passengers = new ArrayList<>();
        seats = new ArrayList<>();

        anchorEntity = (ArmorStand) l.getWorld().spawnEntity(offsetCartAnchor(l), EntityType.ARMOR_STAND);
        ItemStack i = new ItemStack(Material.BOWL);
        ItemMeta meta = i.getItemMeta();
        meta.setCustomModelData(1);
        i.setItemMeta(meta);
        anchorEntity.setItem(EquipmentSlot.HEAD, i);
        anchorEntity.setInvisible(true);
        anchorEntity.setDisabledSlots(EquipmentSlot.HEAD);
        pullEntity = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);

        registerAnchor(anchorEntity);
        registerPull(pullEntity);
        Location[] seatLocations = new Location[]{
                seatLocationFromAnchor(1,0.5,1),
                seatLocationFromAnchor(-1,0.5,1),
                seatLocationFromAnchor(1,0.5,-1),
                seatLocationFromAnchor(-1,0.5,-1)
        };
        for (Location seatLocation : seatLocations) {
            ArmorStand as = (ArmorStand) seatLocation.getWorld().spawnEntity(seatLocation, EntityType.ARMOR_STAND);
            as.setInvisible(true);
            as.setMarker(true);
            seats.add(as);
        }
    }

    private Location seatLocationFromAnchor(double x, double y, double z) {
        return anchorEntity.getLocation().add(x,y,z);
    }

    private void teleportSeatsToCorners() {
        double distance = .6;
        double height = .4;
        Location l = anchorEntity.getLocation();
        double yaw = l.getYaw();
        double angle = Math.toRadians(yaw);

        double offsetX = Math.sin(angle) * distance;
        double offsetZ = Math.cos(angle) * distance;

        Location corner = l.clone().add(offsetX, 0, offsetZ);
        corner.setPitch(0);
        seats.get(0).teleport(corner.clone().add(offsetX, height, offsetZ));
        seats.get(1).teleport(corner.clone().add(-offsetX, height, -offsetZ));
        seats.get(2).teleport(corner.clone().add(-offsetZ, height, offsetX));
        seats.get(3).teleport(corner.clone().add(offsetZ, height, -offsetX));
    }

    public void pull(Player p) {
        if (puller != null) {
            p.sendMessage("Cart already pulled by an other player!");
            return;
        }
        this.puller = p;

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (puller == null) {
                    cancel();
                    return;
                }
                Location l = p.getLocation();
                anchorEntity.teleport(offsetCartAnchor(l));
                pullEntity.teleport(l);
                teleportSeatsToCorners();
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public void sit(Entity e) {
        if(!isStorageFull()) {
            this.passengers.add(e);
            this.seats.get(passengers.indexOf(e)).addPassenger(e);
        }
    }

    public void unSit(Entity e) {
        this.passengers.remove(e);
        for (Entity pass : passengers) {
            sit(pass);
        }
    }

    private Location offsetCartAnchor(Location l) {
        Location l2 = l.clone();
        l.setPitch(0);
        return l2.add(l.getDirection().multiply(-2));
    }

    public void registerAnchor(ArmorStand armorStand) {
        anchorMap.put(armorStand, this);
    }

    public void registerPull(ArmorStand armorStand) {
        pullMap.put(armorStand, this);
    }

    public boolean isStorageFull() {
        return this.passengers.size() == 4;
    }

    public boolean isAlreadyPulled() {
        return this.puller != null;
    }

    public void stopPulling() {
        this.puller = null;
    }

    public static boolean isPullingCart(Player p) {
        for (Cart c : anchorMap.values()) {
            if (c.puller != null && c.puller.equals(p))
                return true;
        }
        return false;
    }

    public static Cart getCartByPuller(Player p) {
        for (Cart c : anchorMap.values()) {
            if (c.puller != null && c.puller.equals(p)) {
                return c;
            }
        }
        return null;
    }

    public static boolean isSitting(Player p) {
        for (Cart cart : anchorMap.values()) {
            for (Entity e : cart.passengers) {
                if (e.equals(p))
                    return true;
            }
        }
        return false;
    }

    public static Cart getCartBySitter(Player p) {
        for (Cart cart : anchorMap.values()) {
            for (Entity e : cart.passengers) {
                if (e.equals(p))
                    return cart;
            }
        }
        return null;
    }

    /*public void killCart() {

    }*/
}
