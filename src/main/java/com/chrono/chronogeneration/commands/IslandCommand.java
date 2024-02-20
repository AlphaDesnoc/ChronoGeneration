package com.chrono.chronogeneration.commands;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataIsland;
import com.chrono.chronoapi.cache.DataPlayer;
import com.chrono.chronoapi.logger.PluginLogger;
import com.chrono.chronoapi.structures.DataArea;
import com.chrono.chronoapi.structures.DataPos;
import com.chrono.chronoapi.structures.IslandStructure;
import com.chrono.chronogeneration.ChronoGeneration;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class IslandCommand extends BukkitCommand
{
    private final ChronoAPI api = ChronoAPI.getAPI();
    private final PluginLogger logger = ChronoGeneration.getPluginLogger();

    public IslandCommand()
    {
        super("island");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args)
    {
        if (!(sender instanceof Player player)) {
            logger.warning("Vous ne pouvez pas faire cela dans la console !");
            return false;
        }

        final UUID uuid = player.getUniqueId();

        final DataPlayer dataPlayer = api.getPlayerData().getIfPresent(uuid);
        final DataIsland island = dataPlayer.getIsland();

        if (island != null) {
            final Location loc = island.getLoc();
            player.teleport(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() + 4, loc.getBlockZ()));
            logger.success(player, "Vous avez été téléporté sur votre ile");
            return true;
        }

        final Location oldLoc = api.getLastIslandCreated();
        Location newLoc;

        if (oldLoc.getBlockX() + 640 > 30_000_000) {
            newLoc = new Location(oldLoc.getWorld(), -30_000_000 + 768, oldLoc.getY(), oldLoc.getZ() + 768);
        } else {
            newLoc = new Location(oldLoc.getWorld(), oldLoc.getBlockX() + 768, oldLoc.getY(), oldLoc.getZ());
        }

        api.getStructuresGenerator().generate(player, new IslandStructure("island"), newLoc);
        final DataArea area = new DataArea(
                new DataPos(newLoc.getBlockX() - 64, newLoc.getBlockZ() + 64),
                new DataPos(newLoc.getBlockX() - 64, newLoc.getBlockZ() - 64),
                new DataPos(newLoc.getBlockX() + 64, newLoc.getBlockZ() + 64),
                new DataPos(newLoc.getBlockX() + 64, newLoc.getBlockZ() - 64)
        );

        dataPlayer.setIsland(new DataIsland(player.getUniqueId(), 1, newLoc, area));
        api.setLastIslandCreated(newLoc);

        return false;
    }

}
