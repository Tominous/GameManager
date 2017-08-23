package me.rtn.gamemanager.commands;

import me.rtn.gamemanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/*
 * GameManager 
 * Created by George at 7:01 PM on 14-Aug-17  
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
public class CommandManager implements CommandExecutor {

    private JoinCommand joinCommand;
    private ListCommand listCommand;

    public CommandManager(){
        this.joinCommand = new JoinCommand();
        this.listCommand = new ListCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Invalid command! Try /games join or /games list");
        } else {
            String arguement = args[0];
            List<String> newArgs = new ArrayList<>();

            for(int i = 0; i < args.length; i++){
                if(i == 0){
                    continue;
                }
                 newArgs.add(args[i]);
            }
            if(arguement.equalsIgnoreCase("join")){
                if(!Main.getInstance().isSingleServerMode()){
                    this.joinCommand.execute(sender, newArgs.toArray(new String[0]));
                }
            } else if(arguement.equalsIgnoreCase("list")){
                this.listCommand.execute(sender, newArgs.toArray(new String[0]));
            }
        }
        return true;
    }
}
