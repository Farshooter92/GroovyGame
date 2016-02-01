package com.benstone.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.benstone.Actors.Player;
import groovy.lang.GroovyShell;

import java.io.*;

/**
 * Created by Ben on 1/29/2016.
 */
public class MapMaker {


    public static void populateWorld(Stage stage, String mapFileName, int tileWidth, Player player,
                                     Texture texture, GroovyShell inShell, String scriptFileName,
                                     final World world, boolean isStatic, boolean fixedRotation,
                                     short cBits, short mBits, short gIndex)
    {
        int x = 0;
        int y = 0;


        try {

            File mapFile = new File(mapFileName);
            FileReader fr = new FileReader(mapFile);
            BufferedReader br = new BufferedReader(fr);


            String line = br.readLine();

            while (line != null)
            {
                line = br.readLine();

                for (int i = 0; i < line.length(); i++)
                {
                    char currentChar = line.charAt(i);

                    switch (currentChar)
                    {
                        case 'P':
                            if (player != null)
                            {
//                                player.getBody().setTransform(new Vector2(x / PPM, y / PPM), 0);
                            }
                            break;
                        case 'W':

//                            B2DGroovyActor actor = new B2DGroovyActor(texture, inShell, scriptFileName,
//                                    world, isStatic, fixedRotation,
//                                    cBits, mBits, gIndex);
//                            actor.getBody().setTransform(new Vector2(x / PPM, y / PPM), 0);
//                            stage.addActor(actor);
                            break;
                    }
                }

                x += tileWidth;
                y += tileWidth;

            }

            fr.close();
            br.close();
        }
        catch (Exception e)
        {
            System.out.println("Problem loading Map");
        }

    }

}
