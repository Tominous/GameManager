package me.rtn.gamemanager.commands;

import me.rtn.gamemanager.Main;
import me.rtn.gamemanager.game.Game;
import me.rtn.gamemanager.game.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * GameManager 
 * Created by George at 5:57 PM on 14-Aug-17  
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
public class JoinCommand extends SubCommand{
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "Invalid usage!");
            } else {
                for(Game game : Main.getInstance().getGames()){
                    for(GamePlayer gamePlayer : game.getPlayers()){
                        if(game.getPlayers().contains(gamePlayer)){
                            player.sendMessage(ChatColor.RED + "You're already in that game!");
                            return;
                        } else {
                            if(gamePlayer.getPlayer() == player){
                                player.sendMessage(ChatColor.RED + "You're already in that game!");
                            }
                        }
                    }
                }
            }
            Game game = Main.getInstance().getGame(args[0]);
            if(game == null){
                player.sendMessage(ChatColor.RED + "Game is invalid!");
                return;
            }
            game.joinGame(new GamePlayer(player));
        } else {
            sender.sendMessage(ChatColor.RED + "Do you seriously think the console can play the game?!");
        }
    }
}
