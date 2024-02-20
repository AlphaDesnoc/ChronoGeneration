package com.chrono.chronogeneration;

import com.chrono.chronoapi.handlers.CommandHandler;
import com.chrono.chronoapi.handlers.EventHandler;
import com.chrono.chronoapi.logger.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChronoGeneration extends JavaPlugin {
    private static PluginLogger logger;

    @Override
    public void onEnable() {
        logger = new PluginLogger(this.getDescription().getName());

        new CommandHandler().register(this, "com.chrono.chronogeneration.commands");
        new EventHandler().register(this, "com.chrono.chronogeneration.events");
    }

    public static PluginLogger getPluginLogger()
    {
        return logger;
    }
}