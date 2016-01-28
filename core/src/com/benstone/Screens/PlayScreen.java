package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.benstone.Actors.GroovyActor;
import com.benstone.GroovyGame;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * Created by Ben on 1/27/2016.
 */
public class PlayScreen implements Screen, InputProcessor
{
    // Reference to main game object to switch screens and access its data
    GroovyGame game;

    // Scene2D
    private Stage stage;

    // Groovy
    private GroovyShell shell;

    private GroovyActor currentGroovyActor;

    public PlayScreen(GroovyGame inGame)
    {
        game = inGame;

        // Scene2D
        // stage = new Stage(new FitViewport(640, 480));
        // stage = new Stage(new ExtendViewport(640, 480));
        stage = new Stage(new ScreenViewport());

        // Groovy
        shell = new GroovyShell(new Binding());

        // Create Actors
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        GroovyActor myGroovyActor = new GroovyActor(texture, shell, "rotateObject.groovy");

        // Set Actor Properties
        myGroovyActor.setName("GroovyActor");
        Vector2 pos = stage.screenToStageCoordinates(
                new Vector2(
                        (float) Gdx.graphics.getWidth()/2 - texture.getWidth()/2,
                        (float) Gdx.graphics.getHeight()/2 + texture.getHeight()/2));

        myGroovyActor.setPosition(pos.x, pos.y);

        // Create Groups
        Group testActors = new Group();

        // Set group properties
        testActors.setName("GroupTestActors");

        // Add actors to groups
        // Order they are added is the order they are drawn!
        testActors.addActor(myGroovyActor);

        // Add Actors to stage
        stage.addActor(testActors);

        // TODO make it so currentGroovyActor is set by clicking on it
        currentGroovyActor = myGroovyActor;

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
        // Order that the events arrive. Priority matters.
        InputMultiplexer im = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(im);
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
    }

    ///////////////////////////////////////////////////////////////////////////
    //								Input									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.G)
        {
            game.setScreen(game.codeScreen);
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

    ///////////////////////////////////////////////////////////////////////////
    //								Getters									 //
    ///////////////////////////////////////////////////////////////////////////

    public GroovyActor getCurrentGroovyActor()
    {
        return currentGroovyActor;
    }
}
