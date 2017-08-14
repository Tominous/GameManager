package me.rtn.gamemanager;

import me.rtn.gamemanager.game.Game;
import me.rtn.gamemanager.game.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private Set<Game> games = new HashSet<>();
    private int gamesLimit = 0;
    private boolean isSingleServerMode = false;

    private Map<Player, GamePlayer> playerGameMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {
        instance = this;
    }
}
