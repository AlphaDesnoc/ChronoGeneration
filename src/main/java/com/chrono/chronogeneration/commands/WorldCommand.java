package com.chrono.chronogeneration.commands;

import com.chrono.chronoapi.logger.PluginLogger;
import com.chrono.chronogeneration.ChronoGeneration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class WorldCommand extends BukkitCommand
{
    private final PluginLogger logger = ChronoGeneration.getPluginLogger();

    public WorldCommand()
    {
        super("world");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args)
    {
        if (!(sender instanceof Player player)) {
            logger.warning("Vous ne pouvez pas faire cela dans la console !");
            return false;
        }

        if (args.length == 0) {
            logger.warning(player, "Veuillez préciser un monde !");
        }

        final World world = Bukkit.getWorld(args[0]);

        if (world == null) {
            logger.warning(player, "Le monde est invalide !");
            return false;
        }

        player.teleport(new Location(world, 0, 0, 0));

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
    {
        if (alias.equalsIgnoreCase("world")) {
            List<String> names = new ArrayList<>();
            Bukkit.getWorlds().forEach(world -> names.add(world.getName()));
            return names;
        }
        return new ArrayList<>();
    }
}
