package fr.radewon.commands;

import fr.radewon.MyversePlugin;
import fr.radewon.util.ShapeMaker;
import fr.radewon.visuals.Circles;
import fr.radewon.visuals.EMP;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Visuals implements CommandExecutor {

    private MyversePlugin plugin;

    public Visuals(MyversePlugin plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("emp")) {
            EMP emp = new EMP(plugin);
            emp.startTask(p.getLocation(), Integer.parseInt(args[1]), Material.getMaterial(args[2]), Boolean.parseBoolean(args[3]));
            return true;
        }

        else if (args.length > 0 && args[0].equalsIgnoreCase("circle")) {
            Circles.particleCircle(ShapeMaker.circle(Integer.parseInt(args[1]),ShapeMaker.blockForward(Integer.parseInt(args[2]), p.getLocation())), Particle.valueOf(args[3]));
            return true;
        }
        return true;
    }
}
