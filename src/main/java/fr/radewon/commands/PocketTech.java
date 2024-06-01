package fr.radewon.commands;

import fr.radewon.data.CONSTANTS;
import fr.radewon.data.PocketMemory;
import fr.radewon.exceptions.ExceedsBlockLimitException;
import fr.radewon.exceptions.InvalidCoordinatesException;
import fr.radewon.exceptions.NoBlockSelectedException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PocketTech implements CommandExecutor, TabCompleter {
    private static final ChatColor C = ChatColor.GREEN;
    private static final ChatColor R = ChatColor.RED;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Missing arguments.");
            return true;
        }
        Player p = (Player) sender;
        PocketMemory m = PocketMemory.getPocketMemory(p);
        if (args[0].equalsIgnoreCase("pos1")) {
            try {
                m.setPosFirst((p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getEyeLocation().getDirection(), CONSTANTS.RAYCAST_RANGE).getHitBlock().getLocation()));

                sender.sendMessage(C + "Pos1 set to block: " + m.getPosFirst().toString());
            } catch (NoBlockSelectedException e) {
                sender.sendMessage(R + e.getMessage());
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("pos2")) {
            try {
                m.setPosSecond((p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getEyeLocation().getDirection(), CONSTANTS.RAYCAST_RANGE).getHitBlock().getLocation()));

                sender.sendMessage(C + "Pos2 set to block: " + m.getPosSecond().toString());
            } catch (NoBlockSelectedException e) {
                sender.sendMessage(R + e.getMessage());
            }
            return true;
        }

        else if (args[0].equalsIgnoreCase("hpos1")) {
            try {
                m.setPosFirst(p.getLocation());

                sender.sendMessage(C + "Pos1 set to block: " + m.getPosFirst().toString());
            } catch (NoBlockSelectedException e) {
                sender.sendMessage(R + e.getMessage());
            }
        }
        else if (args[0].equalsIgnoreCase("hpos2")) {
            try {
                m.setPosSecond(p.getLocation());

                sender.sendMessage(C + "Pos2 set to block: " + m.getPosSecond().toString());
            } catch (NoBlockSelectedException e) {
                sender.sendMessage(R + e.getMessage());
            }
        }

        else if (args[0].equalsIgnoreCase("store")) {
            m.setWorld(p.getWorld());
            try {
                m.store();

                sender.sendMessage(C + "Positions were successfully stored");
            } catch (InvalidCoordinatesException e) {
                sender.sendMessage(R + e.getMessage());
            }
            return true;
        }

        else if (args[0].equalsIgnoreCase("spot")) {
            try {
                m.setSelectedSpot((p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getEyeLocation().getDirection(), CONSTANTS.RAYCAST_RANGE).getHitBlock().getLocation()));

                sender.sendMessage(C + "Spot set to block: " + m.getSelectedSpot().toString());
            } catch (NoBlockSelectedException e) {
                sender.sendMessage(R + e.getMessage());
            }
        }

        else if (args[0].equalsIgnoreCase("launch")) {
            if (m.isStored() && m.hasSpot()) {
                try {
                    m.isUnderLimit();

                    startTech(p);
                } catch (ExceedsBlockLimitException e) {
                    sender.sendMessage(R + e.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> l = new ArrayList<>();
            l.add("launch");
            l.add("hpos1");
            l.add("hpos2");
            l.add("pos1");
            l.add("pos2");
            l.add("spot");
            l.add("store");
            return l;
        }
        return null;
    }

    private void startTech(Player p) {
        p.sendMessage(C + "Starting the deployment!");
        iterate(p);
        p.sendMessage(C + "Pocket Structure Deployed!");
    }

    private void iterate(Player p) {
        Location l, l2;
        PocketMemory m = PocketMemory.getPocketMemory(p);
        int ii = (m.getPosFirst().getBlockX() >= m.getPosSecond().getBlockX()) ? -1 : 1;
        int ji = (m.getPosFirst().getBlockY() >= m.getPosSecond().getBlockY()) ? -1 : 1;
        int ki = (m.getPosFirst().getBlockZ() >= m.getPosSecond().getBlockZ()) ? -1 : 1;
        for (int i = 0; i < m.getXLength(); i++) {
            for (int j = 0; j < m.getYLength(); j++) {
                for (int k = 0; k < m.getZLength(); k++) {
                    l = m.getSelectedSpot().clone();
                    l2 = m.getStoredFirst().clone();

                    int deltaX = i * ii;
                    int deltaY = j * ji;
                    int deltaZ = k * ki;

                    double newX = l.getX() + deltaX;
                    double newY = l.getY() + deltaY;
                    double newZ = l.getZ() + deltaZ;

                    l2.setX(l2.getX() + deltaX);
                    l2.setY(l2.getY() + deltaY);
                    l2.setZ(l2.getZ() + deltaZ);

                    p.sendBlockChange(new Location(l.getWorld(), newX, newY, newZ), l2.getBlock().getBlockData());
                }
            }
        }
    }
}
