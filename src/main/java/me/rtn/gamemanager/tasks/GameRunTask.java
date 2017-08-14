package me.rtn.gamemanager.tasks;

import me.rtn.gamemanager.Main;
import me.rtn.gamemanager.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * GameManager 
 * Created by George at 5:30 PM on 14-Aug-17  
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
public class GameRunTask extends BukkitRunnable {

    private Game game;
    private int startIn = 20;
    private Player player = (Player) Main.getInstance().getServer().getOnlinePlayers();

    public GameRunTask(Game game){
        this.game = game;
        this.game.setGameState(Game.GameState.PREP);
        //can add players
    }

    @Override
    public void run() {
        if(startIn <= 1){
            this.cancel();
            this.game.setGameState(Game.GameState.ACTIVE);
        }
    }
}
