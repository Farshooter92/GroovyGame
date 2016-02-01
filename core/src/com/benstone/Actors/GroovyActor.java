package com.benstone.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.StringBuilder;
import com.benstone.Utils.FileUtils;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.*;

/**
 * Created by Ben on 1/22/2016.
 * An actor to simply test scripting functionality
 */
public class GroovyActor extends B2DActor
{
    private GroovyShell shell;
    private Script script;
    private String groovyScriptText;

    public GroovyActor(float inWidth, float inHeight, Texture texture, Body inBody,
                       GroovyShell inShell, String scriptFileName)
    {
        super(inWidth, inHeight, texture, inBody);

        shell = inShell;

        File file;

        try {
            file = new File(scriptFileName);

            script = inShell.parse(file);

            groovyScriptText = FileUtils.fileToString(file);
        }
        catch (IOException e)
        {
            // Print a message saying we caught the exception
            Gdx.app.log(scriptFileName, e.getMessage());

            // We couldn't load the script so set it to default
            resetScriptToDefault();

        }

        // Invoke the constructor passing in the reference to the actor.
        // DOES NOT WORK. ONLY A METHOD CALL.
        //script.invokeMethod("Constructor", this);

        // Storing Data into the script
        script.setProperty("Actor", this);

        // Debugging purposes only. Should be called outside the class
        // runScript();
    }

    ///////////////////////////////////////////////////////////////////////////
    //			    	        Scripting Specific							 //
    ///////////////////////////////////////////////////////////////////////////

    private void evaluateScript(String textScript)
    {
        script = shell.parse(textScript);
    }

    public void resetScriptToDefault()
    {
        // Try to set the file to the default
        try
        {
            File file = new File("default.groovy");

            script = shell.parse(file);

            groovyScriptText = FileUtils.fileToString(file);
        }
        catch (IOException ioe)
        {
            Gdx.app.log("default.groovy", ioe.getMessage());
        }
    }

    public void runScript()
    {
        if (script != null)
        {
            script.run();
        }
        else
        {
            Gdx.app.log("Error", "Groovy script is null.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //			    			    Getters  								 //
    ///////////////////////////////////////////////////////////////////////////

    public String getGroovyScriptText()
    {
        return groovyScriptText;
    }

    ///////////////////////////////////////////////////////////////////////////
    //			    			    Setters  								 //
    ///////////////////////////////////////////////////////////////////////////

    public void setGroovyScriptText(String groovyScriptText) throws CompilationFailedException
    {
        this.groovyScriptText = groovyScriptText;
        this.evaluateScript(this.groovyScriptText);
    }

    public void setGroovyScript(File groovyScriptFile) throws IOException, CompilationFailedException
    {
        this.groovyScriptText = FileUtils.fileToString(groovyScriptFile);
        this.evaluateScript(this.groovyScriptText);
    }


}
