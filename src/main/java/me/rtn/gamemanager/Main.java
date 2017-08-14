package me.rtn.gamemanager;

import me.rtn.gamemanager.data.DataHandler;
import me.rtn.gamemanager.game.Game;
import me.rtn.gamemanager.game.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
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

        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();

        //todo commands

        this.isSingleServerMode = getConfig().getBoolean("single-server-mode");

        if(this.isSingleServerMode){
            gamesLimit = 1;
        } else {
            gamesLimit = getConfig().getInt("max-games");
        }

        if(DataHandler.getDataHandler().getFileConfiguration().getConfigurationSection("games") != null){
            for(String gameName : DataHandler.getDataHandler().getFileConfiguration().getConfigurationSection("games").getKeys(false)){
                Game game = new Game(gameName);
                //todo register game
            }
        } else {
            getLogger().warning("[!] No games are currently running! [!]");
        }
    }

    @Override
    public void onDisable() {
        instance = this;

        for(Map.Entry<Player, GamePlayer> entry : playerGameMap.entrySet()){
            try{
                //todo lobby point
            } catch(Exception e){
                getLogger().info("Error: " + e);
            }
            entry.getKey().getInventory().clear();
            entry.getKey().getInventory().setArmorContents(new ItemStack[0]);
        }
    }
}
