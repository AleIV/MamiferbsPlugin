package net.noobsters.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;

/**
 * MAMIFERBS
 */
public class Core extends JavaPlugin{

    private static @Getter Core instance;
    private @Getter PaperCommandManager commandManager;
    private @Getter GlobalListener globalListener;

    @Override
    public void onEnable() {
        instance = this;

        commandManager = new PaperCommandManager(this);

        commandManager.registerCommand(new FerbslandCMD(this));

        globalListener = new GlobalListener(this);

        Bukkit.getPluginManager().registerEvents(globalListener, this);

    }

    @Override
    public void onDisable() {

    }
    
}