package fr.radewon;

import fr.radewon.commands.*;
import fr.radewon.entities.cart.CartListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyversePlugin extends JavaPlugin {
    private CartListener cartListener;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Starting");
        PluginManager pluginManager = getServer().getPluginManager();
        cartListener = new CartListener();
        pluginManager.registerEvents(cartListener, this);
        getCommand("nlnzone").setExecutor(new NlnZone());
        getCommand("pocket").setExecutor(new PocketTech());
        getCommand("throw").setExecutor(new Throw(this));
        getCommand("visuals").setExecutor(new Visuals(this));
        getCommand("myentity").setExecutor(new MyverseEntity(this, cartListener));
        getCommand("link").setExecutor(new WebServerLink());
        getCommand("speed").setExecutor(new Speed());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Stopping");
    }
}
