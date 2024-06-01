package fr.radewon.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NlnZone implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player p = (Player) sender;
        Location l, l2;

        final int SIZE = 11;

        for (int i = 0 ; i < SIZE ; i++) {
            for (int j = 0 ; j < SIZE ; j++ ) {
                for (int k = 0 ; k < SIZE ; k++ ) {
                    l = recenterLocation(p.getLocation(), SIZE);
                    l2 = recenterLocation(new Location(Bukkit.getWorld("Hangar"),-118, 134, -126), SIZE);
                    l.setX(l.getX()+i); l2.setX(l2.getX()+i);
                    l.setY(l.getY()+j); l2.setY(l2.getY()+j);
                    l.setZ(l.getZ()+k); l2.setZ(l2.getZ()+k);

                    if (l.getBlock().getType().isCollidable() && l2.getBlock().getType().isAir()) {
                        p.sendBlockChange(l, Material.BARRIER.createBlockData());
                    }
                    else if (l2.getBlock().getType().isBlock()) {
                        p.sendBlockChange(l, l2.getBlock().getBlockData());
                    }
                    else if (i == 0 || i == SIZE-1 || j == 0 || j == SIZE-1 || k == 0 || k == SIZE-1 || l.getBlock().getType().isCollidable()) {
                        p.sendBlockChange(l, Material.BARRIER.createBlockData());
                    }
                    else {
                        p.sendBlockChange(l, Material.AIR.createBlockData());
                    }
                }
            }
        }



        return true;
    }

    private Location recenterLocation(Location l, int size) {
        l.setX(l.getX()-size/2);
        l.setY(l.getY()-size/2);
        l.setZ(l.getZ()-size/2);
        return l;
    }
}
