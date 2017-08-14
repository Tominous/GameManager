package me.rtn.gamemanager.game;

import com.sun.xml.internal.messaging.saaj.soap.impl.HeaderImpl;
import me.rtn.gamemanager.Main;
import me.rtn.gamemanager.data.DataHandler;
import me.rtn.gamemanager.data.RollbackHandler;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

import static me.rtn.gamemanager.game.Game.GameState.LOBBY;

/*
 * GameManager 
 * Created by George at 1:50 AM on 14-Aug-17  
 * Copyright (C) 2017 RapidTheNerd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Game {

    private String displayName;
    private int maxPlayers;
    private int minPlayers;
    private World world;
    private List<Location> spawnPoints;
    private Location lobbyPoint;
    //active game information
    private Set<GamePlayer> players;
    private Set<GamePlayer> spectators;
    private Map<GamePlayer, Location> gamePlayerToSpawnPoint = new HashMap<>();
    private GameState gameState = LOBBY;
    private Map<GamePlayer, Location> tpSpawnPoinbts = new HashMap<>();
    private boolean movementFrozen = false;

    public Game(String gameName){
        FileConfiguration fileConfiguration = DataHandler.getDataHandler().getFileConfiguration();

        this.displayName = fileConfiguration.getString("games." + gameName + ".displayName");
        this.maxPlayers = fileConfiguration.getInt("games." + gameName + ".maxPlayers");
        this.minPlayers = fileConfiguration.getInt("games." + gameName + ".minPlayers");

        RollbackHandler.getRollbackHandler().rollBack(fileConfiguration.getString("games." + gameName + ".worldName"));

        this.world = Bukkit.createWorld(new WorldCreator(fileConfiguration.getString("games." + gameName + ".worldName")));

        try {
            String[] values = fileConfiguration.getString("games." + gameName + ".lobbyPoint").split(",");
            double x = Double.parseDouble(values[0].split(":")[1]);
            double y = Double.parseDouble(values[1].split(":")[1]);
            double z = Double.parseDouble(values[2].split(":")[1]);
            lobbyPoint = new Location(world, x, y, z);
        }catch(Exception e){
            Main.getInstance().getLogger().info("Error: " + e);
        }
        this.spawnPoints = new ArrayList<>();

        for(String point : fileConfiguration.getStringList("games." + gameName + ".spawnPoints")){

            try {
                String[] values = fileConfiguration.getString("games." + gameName + ".lobbyPoint").split(",");
                double x = Double.parseDouble(values[0].split(":")[1]);
                double y = Double.parseDouble(values[1].split(":")[1]);
                double z = Double.parseDouble(values[2].split(":")[1]);
                Location location = new Location(world, x, y, z);
                spawnPoints.add(location);
            }catch(Exception e) {
                Main.getInstance().getLogger().info("Error: " + e);
            }
        }
    }

    public void setSpectatorSettings(Player player){
        GamePlayer gamePlayer = getGamePlayer(player);

        player.setMaxHealth(20);//deprecated but fuck it
        player.setHealth(player.getMaxHealth());//still deprecated but fuck it
        player.setGameMode(GameMode.SPECTATOR);

        if(gamePlayer != null){
            //todo switch to spectator
        }
    }

    public void assignSpawnPoints(){
        int id = 0;
        for(GamePlayer gamePlayer : getPlayers()){
            try{
                gamePlayerToSpawnPoint.put(gamePlayer, spawnPoints.get(id));
                gamePlayer.teleport(spawnPoints.get(id));
                id += 1;
                gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getMaxHealth());//deprecated but fuck it
            }catch(IndexOutOfBoundsException e){
                Main.getInstance().getServer().getLogger().info("Error: " + e);
            }
        }
    }

    public boolean joinGame(GamePlayer gamePlayer){
        if(isState(GameState.LOBBY) || isState(GameState.STARTING)){
            if(getPlayers().size() == getMaxPlayers()){
                gamePlayer.sendMessage(ChatColor.RED + "Game is full!");
                return false;
            }
            getPlayers().add(gamePlayer);
            gamePlayer.teleport(isState(GameState.LOBBY) ?  lobbyPoint : null);
            gamePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&b" + gamePlayer.getPlayer().getName() + " &b has joined!"
            + "&4" + getPlayers().size() + "/" + getMaxPlayers()));
        }
        return true;
    }

    public GamePlayer getGamePlayer(Player player){
        for(GamePlayer gamePlayer : getPlayers()){
            if(gamePlayer.getPlayer() == player){
                return gamePlayer;
            }
        }
        for(GamePlayer gamePlayer : getSpectators()){
            if(gamePlayer.getPlayer() == player)
                return gamePlayer;
        }
        return null;
    }

    public boolean isState(GameState state){
        return getGameState() == state;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public World getWorld() {
        return world;
    }

    public List<Location> getSpawnPoints() {
        return spawnPoints;
    }

    public Location getLobbyPoint() {
        return lobbyPoint;
    }

    public Set<GamePlayer> getPlayers() {
        return players;
    }

    public Set<GamePlayer> getSpectators() {
        return spectators;
    }

    public Map<GamePlayer, Location> getGamePlayerToSpawnPoint() {
        return gamePlayerToSpawnPoint;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Map<GamePlayer, Location> getTpSpawnPoinbts() {
        return tpSpawnPoinbts;
    }

    public boolean isMovementFrozen() {
        return movementFrozen;
    }

    public enum GameState {
        LOBBY, STARTING, PREP, ACTIVE, ENDING;
    }
}
