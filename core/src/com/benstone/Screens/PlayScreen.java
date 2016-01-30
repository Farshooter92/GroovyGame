package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.benstone.Actors.B2DGroovyActor;
import com.benstone.Actors.GroovyActor;
import com.benstone.GroovyGame;
import com.benstone.Utils.Constants;
import com.benstone.Utils.MapMaker;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import static com.benstone.Utils.Constants.PPM;
import static com.benstone.Utils.Constants.MIN_WORLD_WIDTH;
import static com.benstone.Utils.Constants.MIN_WORLD_HEIGHT;

/**
 * Created by Ben on 1/27/2016.
 */
public class PlayScreen implements Screen, InputProcessor
{
    // Reference to main game object to switch screens and access its data
    GroovyGame game;

    // UI
    private Table rootTable;
    private Skin skin;

    // Scene2D
    private Stage stageGameWorld;
    private Stage stageGUI;

    // Box2D
    private Box2DDebugRenderer b2dr;
    private World world;

    // Groovy
    private GroovyShell shell;
    private GroovyActor currentGroovyActor;

    public PlayScreen(GroovyGame inGame)
    {
        game = inGame;

        // Initialize Scene2D
        // Pass in the min world width and height
        stageGameWorld = new Stage(new FitViewport(MIN_WORLD_WIDTH / PPM, MIN_WORLD_HEIGHT / PPM));

        ///////////////////////////////////////////////////////////////////////////
        //				                    UI                               	 //
        ///////////////////////////////////////////////////////////////////////////

        stageGUI = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // Initialize Root Table
        rootTable = new Table();

        // Want table to take the whole screen
        rootTable.setFillParent(true);
        // Align items top down
        rootTable.center().top();

        // Skin acts as a container for all drawables
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create Widgets Layouts and Widgets
        Label title = new Label("Groovy Game Play Screen", skin);

        // Add widgets to rootTable
        rootTable.add(title).center().top();

        // Add rootTable to stageGameWorld
        stageGUI.addActor(rootTable);

        ///////////////////////////////////////////////////////////////////////////
        //				                    Box2D                              	 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Box2D
        // Set gravity vector and do not allow objects to sleep
        world = new World(new Vector2(0, -9.8f), false);
        b2dr = new Box2DDebugRenderer();

        ///////////////////////////////////////////////////////////////////////////
        //				                    Groovy                             	 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Groovy
        shell = new GroovyShell(new Binding());

        ///////////////////////////////////////////////////////////////////////////
        //				             Populate World                            	 //
        ///////////////////////////////////////////////////////////////////////////

        B2DGroovyActor actor = new B2DGroovyActor(
                new Texture(Gdx.files.internal("test.png")), shell, "rotateObject.groovy",
                world, true, true,
                Constants.BIT_WALL, Constants.BIT_PLAYER, (short) 0);


        stageGameWorld.addActor(actor);

        ///////////////////////////////////////////////////////////////////////////
        //				                    Debug                             	 //
        ///////////////////////////////////////////////////////////////////////////

        // Debug
        rootTable.setDebug(true);

    }

    ///////////////////////////////////////////////////////////////////////////
    //						Core Game Loop									 //
    ///////////////////////////////////////////////////////////////////////////

    public void update(float delta)
    {
        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void render(float delta)
    {
        //separate our update logic from render
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageGameWorld.act(Gdx.graphics.getDeltaTime());
        stageGameWorld.draw();

        stageGUI.act(Gdx.graphics.getDeltaTime());
        stageGUI.draw();

        // Box2D debug renderer
        b2dr.render(world, stageGUI.getCamera().combined.scl(PPM));
    }

    ///////////////////////////////////////////////////////////////////////////
    //						Maintenance Methods								 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.

        // Input
        // Order that the events arrive. Priority matters.
        InputMultiplexer im = new InputMultiplexer(stageGameWorld, this);
        Gdx.input.setInputProcessor(im);


    }

    @Override
    public void resize(int width, int height)
    {
        stageGameWorld.getViewport().update(width, height, false);

        // true means camera will be recentered. Good for UI
        stageGUI.getViewport().update(width, height, true);
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
        stageGameWorld.dispose();
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
        // TODO make it so currentGroovyActor is set by clicking on it

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
