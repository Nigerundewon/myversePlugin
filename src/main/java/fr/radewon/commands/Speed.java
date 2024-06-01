package fr.radewon.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0) {
            if (args[0].equals("reset")) {
                p.setFlySpeed(1);
                p.setWalkSpeed(1);
                p.sendMessage("Reset de votre speed");
            }
            else {
                float i = Float.parseFloat(args[0]);
                if (i < 0 || i > 10) {
                    return true;
                } else {
                    if (p.isFlying()) {
                        p.setFlySpeed(i / 10);
                        p.sendMessage("Flying speed set à : " + i);
                    } else {
                        p.setWalkSpeed(i / 10);
                        p.sendMessage("Walking speed set à : " + i);
                    }
                }
            }
        }
        else {
            p.sendMessage("/speed <0-10 | reset>");
        }
        return true;
    }
}
