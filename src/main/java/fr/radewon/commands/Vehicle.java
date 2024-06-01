package fr.radewon.commands;

import fr.radewon.vehicle.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class Vehicle implements CommandExecutor {
    /*put("car",new Car());
    put("train", new Train());
    put("boat", new Boat());
    put("spaceship", new Spaceship());
    put("train", new Train());*/
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("new")) {
            if (args.length > 2) {
                try {
                    int texture = Integer.parseInt(args[2]);
                    makeVehicle(args[1], texture, args[3]);

                } catch (Exception e) {
                    p.sendMessage(e.getMessage());
                }
                return true;
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("modify")) {
            return true;
        }
        return true;
    }

    private void makeVehicle(String s, int i, String d) {
        Vehicles v = new Vehicles();
        if (s.equalsIgnoreCase("car")) new Car(i, d);
        else if (s.equalsIgnoreCase("train")) new Train(i, d);
        else if (s.equalsIgnoreCase("boat")) new Boat(i, d);
        else if (s.equalsIgnoreCase("spaceship")) new Spaceship(i, d);
        else if (s.equalsIgnoreCase("plane")) new Plane(i, d);
    }
}
