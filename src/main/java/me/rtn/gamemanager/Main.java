package me.rtn.gamemanager;

import me.rtn.gamemanager.commands.CommandManager;
import me.rtn.gamemanager.data.DataHandler;
import me.rtn.gamemanager.data.RollbackHandler;
import me.rtn.gamemanager.game.Game;
import me.rtn.gamemanager.game.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    private Map<Player, Game> playerGameMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();

        getCommand("games").setExecutor(new CommandManager());

        this.isSingleServerMode = getConfig().getBoolean("single-server-mode");

        if (this.isSingleServerMode) {
            gamesLimit = 1;
        } else {
            gamesLimit = getConfig().getInt("max-games");
        }

        if (DataHandler.getDataHandler().getFileConfiguration().getConfigurationSection("games") != null) {
            for (String gameName : DataHandler.getDataHandler().getFileConfiguration().getConfigurationSection("games").getKeys(false)) {
                Game game = new Game(gameName);
                boolean status = this.registerGame(game);
                if(!status){
                    getLogger().info("[!] Can't load game: " + gameName + "[!]");
                }
            }
        } else {
            getLogger().warning("[!] No games are currently running! [!]");
        }
    }

    @Override
    public void onDisable() {
        instance = this;

        for (Map.Entry<Player, Game> entry : playerGameMap.entrySet()) {
            try {
                entry.getKey().teleport(getLobbyPoint());
            } catch (Exception e) {
                getLogger().info("Error: " + e);
            }
            entry.getKey().getInventory().clear();
            entry.getKey().getInventory().setArmorContents(new ItemStack[0]);
        }
        for(Game game : getGames()){
            for(Player player : game.getWorld().getPlayers()){
                try{
                    player.teleport(getLobbyPoint());
                } catch(Exception e){
                    getInstance().getLogger().info("Error: " + e);
                }
            }
            try {
                RollbackHandler.getRollbackHandler().rollback(game.getWorld());
            } catch(Exception e){
                getInstance().getLogger().info("Error: " + e);
            }
        }
    }

    private Location lobbyPoint = null;

    public Location getLobbyPoint() {
        if (lobbyPoint == null) {
            int x = 0;
            int y = 0;
            int z = 0;
            //probably should be doubles but fuck it
            String world = "world";

            try {
                x = getInstance().getConfig().getInt("lobby-point.x");
                y = getInstance().getConfig().getInt("lobby-point.y");
                z = getInstance().getConfig().getInt("lobby-point.z");
                world = getInstance().getConfig().getString("lobby-point.world");
            } catch (Exception e) {
                e.printStackTrace();
            }
            lobbyPoint = new Location(Bukkit.getWorld(world), x, y, z);
        }
        return lobbyPoint;
    }

    private boolean registerGame(Game game) {
        if (games.size() == gamesLimit && gamesLimit != -1) {
            return false;
        }
        games.add(game);
        return true;
    }

    public Game getActiveGame(String gameName) {
        for (Game game : games) {
            if (game.getDisplayName().equalsIgnoreCase(gameName))
                return game;
        }
        return null;
    }

    public void setGame(Player player, Game game){
        if(game == null){
            this.playerGameMap.remove(player);
        } else {
            this.playerGameMap.put(player, game);
        }
    }
    public Game getGame(String gameName) {
        for (Game game : games) {
            if (game.getDisplayName().equalsIgnoreCase(gameName))
                return game;
        }
        return null;
    }

    public Set<Game> getGames() {
        return games;
    }

    public int getGamesLimit() {
        return gamesLimit;
    }

    public boolean isSingleServerMode() {
        return isSingleServerMode;
    }

    public Map<Player, Game> getPlayerGameMap() {
        return playerGameMap;
    }
}
