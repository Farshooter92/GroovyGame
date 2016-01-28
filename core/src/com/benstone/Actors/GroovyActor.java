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
    private GroovyShell shell;
    private Script script;

    // TODO set to the script being evaluated
    private String groovyScript;

    public GroovyActor(Texture texture, GroovyShell inShell, String scriptFileName)
    {
        super(texture);

        shell = inShell;

        setBounds(getX(), getY(), getWidth(), getHeight());

        File file;

        try {
            file = new File(scriptFileName);

            script = inShell.parse(file);
        }
        catch (IOException e)
        {
            // Print a message saying we caught the exception
            Gdx.app.log("File not found exception", scriptFileName);

            // Try to set the file to the default
            try
            {
                file = new File("default.groovy");

                script = inShell.parse(file);
            }
            catch (IOException ioe)
            {
                Gdx.app.log("File not found exception", "default.groovy");
            }
        }

        // Invoke the constructor passing in the reference to the actor.
        // DOES NOT WORK. ONLY A METHOD CALL.
        //script.invokeMethod("Constructor", this);

        // Storing Data into the script
        script.setProperty("Actor", this);

        // Debugging purposes only. Should be called outside the class
        // runScript();

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

    public void evaluateScript(String textScript)
    {
        script = shell.parse(textScript);
    }

    public void runScript()
    {
        script.run();
    }
}
