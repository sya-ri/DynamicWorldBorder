package dev.s7a.DynamicWorldBorder;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Objects;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("dynamicworldborder")).setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    Player centerPlayer;
    BukkitTask scheduler;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null) {
                centerPlayer = player;
                scheduler = Bukkit.getScheduler().runTaskTimer(this, () -> {
                    World world = player.getWorld();
                    if (player.isOnline()) {
                        world.getWorldBorder().setCenter(player.getLocation());
                    } else {
                        getLogger().info(player.getName() + " がログアウトしたので自動変更を停止しました");
                    }
                }, 0, 20);
                sender.sendMessage("[DynamicWorldBorder] 中心プレイヤーを " + player.getName() + " に変更しました");
            } else {
                sender.sendMessage("[DynamicWorldBorder] 存在しないプレイヤーです");
            }
        } else {
            if (scheduler != null) {
                scheduler.cancel();
            }
            centerPlayer = null;
            scheduler = null;
            sender.sendMessage("[DynamicWorldBorder] 自動変更を停止しました");
        }
        return true;
    }

    @EventHandler
    public void on(PlayerSpawnLocationEvent event) {
        if (centerPlayer != null) {
            event.setSpawnLocation(centerPlayer.getLocation());
        }
    }

    @EventHandler
    public void on(PlayerRespawnEvent event) {
        if (centerPlayer != null) {
            event.setRespawnLocation(centerPlayer.getLocation());
        }
    }
}
