package fr.radewon.commands;

import fr.radewon.MyversePlugin;
import fr.radewon.data.CONSTANTS;
import fr.radewon.data.ThrownItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class Throw implements CommandExecutor, TabCompleter {
    private MyversePlugin plugin;

    public Throw(MyversePlugin plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player p = (Player) sender;
        ThrownItem t = new ThrownItem(this.plugin);
        if (args.length > 0 && args[0].equalsIgnoreCase("player")) {
            if (args.length > 1) {
                Player p2 = Bukkit.getPlayer(args[1]);
                if (p2 != null) t.setDest(p2.getLocation());
                if (args.length > 2) {
                    try {
                        double d = Double.parseDouble(args[2]);

                        t.setSpeed(d);
                    } catch (NumberFormatException e) {
                        p.sendMessage(e.getMessage());
                    }
                }
            }
        }
        else if (args.length == 1) try {
            double d = Double.parseDouble(args[0]);

            t.setSpeed(d);
            t.setDest(p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getEyeLocation().getDirection(), CONSTANTS.RAYCAST_RANGE).getHitBlock().getLocation());
        } catch (NumberFormatException e) {
            p.sendMessage(e.getMessage());
        }
        else {
            t.setDest(p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getEyeLocation().getDirection(), CONSTANTS.RAYCAST_RANGE).getHitBlock().getLocation());
        }
        t.setItem(p.getInventory().getItemInMainHand());
        t.setOrigin(p.getLocation());
        p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        t.launch();
        return true;
        }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> l = new ArrayList<>();
            l.add("player");
            return l;
        }
        return null;
    }
}
