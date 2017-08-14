package me.rtn.gamemanager;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {
        instance = this;
    }
}
