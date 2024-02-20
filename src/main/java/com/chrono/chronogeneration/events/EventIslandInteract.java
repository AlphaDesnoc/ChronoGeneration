package com.chrono.chronogeneration.events;

import com.chrono.chronoapi.ChronoAPI;
import com.chrono.chronoapi.cache.DataIsland;
import com.chrono.chronoapi.cache.DataPlayer;
import com.chrono.chronoapi.structures.DataArea;
import com.chrono.chronoapi.structures.DataPos;
import com.chrono.chronogeneration.ChronoGeneration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public final class EventIslandInteract implements Listener
{

    private final ChronoAPI api = ChronoAPI.getAPI();

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event)
    {

    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event)
    {

    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {

    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();

        final Location from = event.getFrom();
        final Location to = event.getTo();

        if (from.getPitch() != to.getPitch() && from.getYaw() != to.getYaw()) {
            return;
        }

        for (final DataPlayer dataPlayer : api.getPlayerData().asMap().values()) {
            final DataIsland island = dataPlayer.getIsland();

            if (island == null) {
                return;
            }

            if (isInside(player, island.getArea()) && !isInside(to, island.getArea())){
                player.teleport(from);
            }
        }
    }

    public boolean isInside(final Player player, final DataArea area)
    {
        final Location loc = player.getLocation();
        final DataPos a = area.getPos1();
        final DataPos b = area.getPos2();
        final DataPos c = area.getPos3();

        return a.getX() < loc.getBlockX()
                && loc.getBlockX() < c.getX()
                //Check de X
                && b.getZ() < loc.getBlockZ()
                && loc.getBlockZ() < a.getZ();
    }

    public boolean isInside(final Location loc, final DataArea area)
    {
        final DataPos a = area.getPos1();
        final DataPos b = area.getPos2();
        final DataPos c = area.getPos3();

        return a.getX() < loc.getBlockX()
                && loc.getBlockX() < c.getX()
                //Check de X
                && b.getZ() < loc.getBlockZ()
                && loc.getBlockZ() < a.getZ();
    }

}
