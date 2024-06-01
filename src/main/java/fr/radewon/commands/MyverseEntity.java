package fr.radewon.commands;

import fr.radewon.MyversePlugin;
import fr.radewon.entities.cart.Cart;
import fr.radewon.entities.cart.CartListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyverseEntity implements CommandExecutor {
    private MyversePlugin plugin;
    private final CartListener cartListener;

    public MyverseEntity(MyversePlugin plugin, CartListener cartListener) {
        this.plugin = plugin;
        this.cartListener = cartListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("cart")) {
            Cart c = new Cart(p.getLocation(), plugin);
            return true;
        }
        return true;
    }
}
