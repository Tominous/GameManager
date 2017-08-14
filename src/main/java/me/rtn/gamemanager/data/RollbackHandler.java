package me.rtn.gamemanager.data;

import me.rtn.gamemanager.Main;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.*;

/*
 * GameManager 
 * Created by George at 1:37 AM on 14-Aug-17  
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
public class RollbackHandler {

    private static RollbackHandler rollbackHandler = new RollbackHandler();

    public static RollbackHandler getRollbackHandler() {
        return rollbackHandler;
    }
    public void rollback(World world){
        for(Player player : world.getPlayers()){
            //todo lobby point
        }
        Main.getInstance().getServer().unloadWorld(world, false);
        String originalName = world.getName().split("_")[0];
        rollBack(originalName);
    }
    public void rollBack(String worldName){
        String rootDir = Main.getInstance().getServer().getWorldContainer().getAbsolutePath();

        File srcFolder = new File(rootDir + "/" + worldName);
        File destFolder = new File(rootDir + "/" + worldName + "_active");

        delete(destFolder);

        try{
            copyFolder(srcFolder, destFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void delete(File delete){
        if(delete.isDirectory()){
            String[] files = delete.list();

            if(files != null){
                for(String file : files){
                    File toDelete = new File(file);
                    delete(toDelete);
                }
            }
        } else {
            delete.delete();
        }
    }
    private void copyFolder(File src, File dest) throws IOException {
        if(src.isDirectory()){
            if(!dest.exists()){
                dest.mkdir();
            }
            String[] files = src.list();

            if(files != null){
                for(String file : files){
                    File srcFile = new File(src, file);
                    File destfile = new File(dest, file);
                    copyFolder(srcFile, destfile);
                }
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            int length;
            while((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }
}
