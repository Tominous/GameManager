package me.rtn.gamemanager.commands;

import me.rtn.gamemanager.Main;
import me.rtn.gamemanager.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * GameManager 
 * Created by George at 5:56 PM on 14-Aug-17  
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
public class ListCommand extends SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        for(Game game : Main.getInstance().getGames()){
            player.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "----------------------");
            player.sendMessage(ChatColor.GREEN + "Active games: ");
            player.sendMessage(ChatColor.GOLD + game.getDisplayName() + " state: " + game.getGameState() + " players: "
                    + game.getPlayers().size() + "/" + game.getMaxPlayers());
            player.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "----------------------");
        }
    }
}
