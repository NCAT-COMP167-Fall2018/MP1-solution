/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thekongcontroller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import thekongmodel.BarrelData;
import thekongmodel.HeroData;
import thekongmodel.KongData;
import thekongmodel.LadderData;
import thekongmodel.LevelCollection;
import thekongmodel.LevelData;
import thekongmodel.PlatformData;
import thekongmodel.PlayerProfile;
import thekongmodel.PlayerProfileCollection;
import thekongmodel.PrincessData;
import thekongmodel.SpriteDataCollection;

/**
 *
 * @author CCannon
 */
public class IOController {

    public static PlayerProfileCollection readPlayerProfiles(String playerProfileConfigFileName) {
        PlayerProfileCollection collection = new PlayerProfileCollection(playerProfileConfigFileName);

        try {
            Scanner reader = new Scanner(new File(playerProfileConfigFileName));
            String activeProfileName = reader.nextLine().split(",")[0].trim();

            while (reader.hasNext()) {
                String[] playerData = reader.nextLine().split(",");
                PlayerProfile newProfile = new PlayerProfile(playerData[0]);
                newProfile.setHighScore(Integer.parseInt(playerData[1].trim()));
                newProfile.setTotalScore(Integer.parseInt(playerData[2].trim()));
                newProfile.setGamesPlayed(Integer.parseInt(playerData[3].trim()));
                newProfile.setLevelsWon(Integer.parseInt(playerData[4].trim()));

                collection.addPlayerProfile(newProfile);
            }

            for (int i = 0; i < collection.getNumPlayerProfiles(); i++) {
                if (collection.getPlayerProfile(i).getPlayerName().equalsIgnoreCase(activeProfileName)) {
                    collection.setActiveProfile(collection.getPlayerProfile(i));
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Unable to read PlayerProfileCollection from " + playerProfileConfigFileName + ", returning an empty collection");
        }

        return collection;
    }

    public static void writePlayerProfiles(PlayerProfileCollection collection) {
        try {
            PrintWriter writer = new PrintWriter(new File(collection.getPlayerProfileConfigFileName()));
            writer.println(collection.toString());
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Unable to write PlayerProfileCollection to file " + collection.getPlayerProfileConfigFileName());
        }
    }
}
