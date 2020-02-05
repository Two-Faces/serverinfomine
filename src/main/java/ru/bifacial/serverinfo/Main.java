package ru.bifacial.serverinfo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        if (!(new File(this.getDataFolder(), "config.yml")).exists()) {
            this.createConfig();
        }

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void createConfig() {
        this.getConfig().options().header("Plugin by Bifacial");
        if (!this.getConfig().isBoolean("requestIp")) {
            this.getConfig().set("requestIp", false);
        }

        this.saveConfig();
    }

    @EventHandler(
        priority = EventPriority.HIGH
    )
    public void RequestURI(PlayerJoinEvent e) throws Exception {
        Player p = e.getPlayer();

        URL url = new URL(this.getConfig().getString("requestIp"));

        String data = "ip=" + p.getAddress().getAddress().toString() + "&port=" + getServer().getPort() + "&uuid=" + p.getUniqueId();

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        con.getInputStream();
    }
}
