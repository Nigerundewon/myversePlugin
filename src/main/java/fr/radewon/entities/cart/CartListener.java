package fr.radewon.entities.cart;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CartListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        Player p = event.getPlayer();
        Bukkit.getLogger().info("test");
        Entity clicked = event.getRightClicked();
        if (clicked instanceof ArmorStand && Cart.anchorMap.containsKey(clicked)) {
            Cart c = Cart.anchorMap.get(clicked);
            if (!c.isStorageFull()) {
                if (p.getInventory().getItemInOffHand().getType() == Material.AIR && ! Cart.isPullingCart(p)) {
                    c.sit(p);
                }
                else {
                    ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                    ItemStack i = p.getInventory().getItemInOffHand();
                    as.setItem(EquipmentSlot.HEAD,i);
                    c.sit(as);
                }
            }
        }
        else if (clicked instanceof ArmorStand && Cart.pullMap.containsKey(clicked)) {
            Cart c = Cart.pullMap.get(clicked);
            if (!c.isAlreadyPulled() && !Cart.isSitting(p)) {
                c.pull(p);
            }
        }
    }
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();

        if (Cart.isPullingCart(p)) {
            Cart c = Cart.getCartByPuller(p);
            c.stopPulling();
        }
        if (Cart.isSitting(p)) {
            Cart c = Cart.getCartBySitter(p);
            c.unSit(p);
        }
    }
}
