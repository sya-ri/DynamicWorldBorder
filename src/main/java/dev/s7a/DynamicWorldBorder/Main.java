package dev.s7a.DynamicWorldBorder;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("dynamicworldborder")).setExecutor(this);
    }

    BukkitTask scheduler;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null) {
                World world = player.getWorld();
                scheduler = Bukkit.getScheduler().runTaskTimer(this, () -> {
                    if (player.isOnline()) {
                        world.getWorldBorder().setCenter(player.getLocation());
                    } else {
                        getLogger().info(player.getName() + " がログアウトしたので自動変更を停止しました");
                    }
                }, 0, 20);
            } else {
                sender.sendMessage("[DynamicWorldBorder] 存在しないプレイヤーです");
            }
        } else {
            if (scheduler != null) {
                scheduler.cancel();
            }
            scheduler = null;
            sender.sendMessage("[DynamicWorldBorder] 自動変更を停止しました");
        }
        return true;
    }
}
