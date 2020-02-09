package ru.bifacial.serverinfo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        if (!(new File(this.getDataFolder(), "config.yml")).exists()) {
            this.createConfig();
        }

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void createConfig() {
        this.getConfig().options().header("Plugin by Bifacial");
        this.getConfig().set("ENTRY_URL", "https://expample.com/api/entry.php");
        this.getConfig().set("LEAVE_URL", "https://expample.com/api/exit.php");

        this.saveConfig();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) throws Exception {
        HashMap<String, String> data = this.getData(e.getPlayer());

        new Request().send("POST", new URL(this.getConfig().getString("ENTRY_URL")), data);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) throws IOException {
        HashMap<String, String> data = this.getData(e.getPlayer());

        new Request().send("POST", new URL(this.getConfig().getString("LEAVE_URL")), data);
    }

    private HashMap<String, String> getData(Player p) {
        HashMap<String,String> data = new HashMap<>();
        data.put("ip", p.getAddress().getAddress().toString());
        data.put("port", String.valueOf(getServer().getPort()));
        data.put("uuid", p.getUniqueId().toString());
        data.put("location", p.getWorld().getName() + "," + p.getLocation().getX() + "," + p.getLocation().getY() + "," + p.getLocation().getZ());

        return data;
    }
}
