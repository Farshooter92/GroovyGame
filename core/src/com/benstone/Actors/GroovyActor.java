package com.benstone.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ben on 1/22/2016.
 * An actor to simply test scripting functionality
 */
public class GroovyActor extends Image
{
    private Script script;

    public GroovyActor(Texture texture, GroovyShell shell, String scriptFileName)
    {
        super(texture);

        setBounds(getX(), getY(), getWidth(), getHeight());

        File file;

        try {
            file = new File(scriptFileName);

            script = shell.parse(file);
        }
        catch (IOException e)
        {
            // Print a message saying we caught the exception
            Gdx.app.log("File not found exception", scriptFileName);

            // Try to set the file to the default
            try
            {
                file = new File("default.groovy");

                script = shell.parse(file);
            }
            catch (IOException ioe)
            {
                Gdx.app.log("File not found exception", "default.groovy");
            }
        }

        // TODO figure a way to store data in script
        // Invoke the constructor passing in the reference to the actor.
        // DOES NOT WORK. ONLY A METHOD CALL.
        //script.invokeMethod("Constructor", this);

        script.setProperty("Actor", this);

        // Debugging purposes only. Should be called outside the class
        updateScript();

    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        batch.setColor(this.getColor());

        ((TextureRegionDrawable)getDrawable()).draw(batch, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

    }

    public void updateScript()
    {
        script.run();
    }
}
