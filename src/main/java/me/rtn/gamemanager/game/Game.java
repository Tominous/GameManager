package me.rtn.gamemanager.game;

import me.rtn.gamemanager.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * GameManager 
 * Created by George at 1:45 AM on 14-Aug-17  
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

    private Player player = null;
    private PlayerState playerState;
    private List<Location> spawnPoint = new ArrayList<>();

    public Game(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void sendMessage(String message){
        getPlayer().sendMessage(message);
    }

    public void teleport(Location location){
        if(location == null){
            Main.getInstance().getServer().getLogger().info("[GameManager] ! Could not teleport player! !");
        } else {
            getPlayer().teleport(location);
        }
    }

    public enum PlayerState {
        LOBBY, ALIVE, SPEC;
    }
}
