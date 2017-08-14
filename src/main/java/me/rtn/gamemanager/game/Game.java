package me.rtn.gamemanager.game;

import me.rtn.gamemanager.data.DataHandler;
import me.rtn.gamemanager.data.RollbackHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
