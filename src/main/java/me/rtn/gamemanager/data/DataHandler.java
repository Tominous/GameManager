package me.rtn.gamemanager.data;

import me.rtn.gamemanager.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/*
 * GameManager 
 * Created by George at 1:33 AM on 14-Aug-17  
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
public class DataHandler {

    private static DataHandler instance = new DataHandler();

    public static DataHandler getInstance() {
        return instance;
    }

    public DataHandler(){
        this.gameInfoFile = new File(Main.getInstance().getDataFolder(), "gameInfo.yml");
        if(!this.gameInfoFile.exists()){
            try{
                this.gameInfoFile.getParentFile().mkdirs();
                this.gameInfoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.gameInfo = YamlConfiguration.loadConfiguration(this.gameInfoFile);
    }

    private File gameInfoFile;
    private FileConfiguration gameInfo;

    public File getGameInfoFile() {
        return gameInfoFile;
    }

    public void saveGameInfo(){
        try{
            this.gameInfo.save(this.gameInfoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}