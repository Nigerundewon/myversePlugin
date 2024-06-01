package fr.radewon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;

public class WebServerLink implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only can use this command.");
            return true;
        }
        Player player = (Player) sender;
        sender.sendMessage(doPost(player));

        return true;
    }

    private String doPost(Player sender) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("config.prop"));
            String apiUrl = p.getProperty("URL");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Envoyer le nom du joueur dans le corps de la requête
            String playerName = sender.getName();
            String token = generateToken();
            String postData = "user=" + playerName + "&token=" + token;
            return "http://radewon.net/myverse/linked/index.html?token=" + token;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
