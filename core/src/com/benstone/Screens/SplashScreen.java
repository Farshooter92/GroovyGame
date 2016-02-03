package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.benstone.Actors.GroovyActor;
import com.benstone.GroovyGame;
import com.benstone.Utils.Constants;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * Created by RockyII on 1/28/16.
 */
public class SplashScreen implements Screen, InputProcessor {

    // Reference to main game object to switch screens and access its data
    GroovyGame game;

    // Scene2D
    private Stage stage;

    public SplashScreen(GroovyGame inGame)
    {
        game = inGame;

        // Scene2D
        // stage = new Stage(new FitViewport(640, 480));
        // stage = new Stage(new ExtendViewport(640, 480));
        stage = new Stage(new ScreenViewport());

        ///////////////////////////////////////////////////////////////////////////
        //						       ROOT TABLE								 //
        ///////////////////////////////////////////////////////////////////////////

        Table rootTable = new Table();

        // Skin acts as a container for all drawables
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Want table to take the whole screen
        rootTable.center();
        rootTable.setFillParent(true);

        ///////////////////////////////////////////////////////////////////////////
        //						        Widgets 								 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Widgets with skin
        Label gameTitle = new Label(
                "Groovy Game\n"+
                        "Created by Ben Stone\n\n" +
                        "Instructions:\n" +
                        "Click on an object to access its script. " +
                        "The code editor will allow you to change the scripts and " +
                        "modify the behavior of the actors." +
                        "The scripting language is Groovy so knowledge of " +
                        "Groovy is highly recommeded.\n\n" +
                        "Goal:\n" +
                        "Modify the blocks around your character to aid in your escape.\n\n" +
                        "Press ENTER to start.",
                skin);

        gameTitle.setAlignment(Align.center);
        gameTitle.setWrap(true);


        ///////////////////////////////////////////////////////////////////////////
        //				ADD WIDGETS TO TABLE AND ADD ACTORS TO STAGE           	 //
        ///////////////////////////////////////////////////////////////////////////

        // Add widgets to table here
        // Table formatting is sequential from top to bottom
        // Wrap widgets in Container to do manual transforms
        // Use ChangeListener where applicable in widgets instead of Clicked
        rootTable.add(gameTitle).center().fill().width(Gdx.graphics.getWidth()/2);

        // Add actors to stage
        stage.addActor(rootTable);

        if (Constants.DEBUG_BUILD) {
            // Debug
            rootTable.setDebug(true);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //						Core Game Loop									 //
    ///////////////////////////////////////////////////////////////////////////

    public void update(float delta)
    {

    }

    @Override
    public void render(float delta)
    {
        //separate our update logic from render
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    ///////////////////////////////////////////////////////////////////////////
    //						Maintenance Methods								 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.

        // Input
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height)
    {
        // true means camera will be recentered. Good for UI
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide()
    {
        // Called when game.setScreen is called

    }

    @Override
    public void dispose()
    {
        // TODO Free up resources
        // If it implements the Disposable interface then it should be disposed.
        stage.dispose();
        game.dispose();
    }

    ///////////////////////////////////////////////////////////////////////////
    //								Input									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.ENTER)
        {
            game.setScreen(game.getPlayScreen());
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
