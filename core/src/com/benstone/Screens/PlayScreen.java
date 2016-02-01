package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.*;
import com.benstone.Actors.GroovyActor;
import com.benstone.Actors.Player;
import com.benstone.B2DUserData.GroundUserData;
import com.benstone.B2DUserData.PlayerUserData;
import com.benstone.GroovyGame;
import com.benstone.Utils.B2DUtils;
import com.benstone.Utils.Constants;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * Created by Ben on 1/27/2016.
 */
public class PlayScreen implements Screen, InputProcessor, ContactListener
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

    // Game
    Player player;

    public PlayScreen(GroovyGame inGame)
    {
        game = inGame;

        // Initialize Scene2D
        // Pass in the min world width and height
        stageGameWorld = new Stage(new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT ));

        ///////////////////////////////////////////////////////////////////////////
        //				                    UI                               	 //
        ///////////////////////////////////////////////////////////////////////////

        stageGUI = new Stage(new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT ));

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

        // JUMP BUTTON
        TextButton jumpButton = new TextButton("Jump", skin);
        jumpButton.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor)
            {
                player.Jump();
            }
        });

        jumpButton.setSize(100, 100);
        jumpButton.setPosition(Gdx.graphics.getWidth() - 100, 0);

        // Add widgets to rootTable
        rootTable.add(title).center().top();

        // Add rootTable to stageGameWorld
        stageGUI.addActor(rootTable);
        stageGUI.addActor(jumpButton);

        ///////////////////////////////////////////////////////////////////////////
        //				                    Box2D                              	 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Box2D
        // Set gravity vector and do allow objects to sleep since we will have a lot of physics objects
        world = new World(Constants.WORLD_GRAVITY, true);
        world.setContactListener(this);
        b2dr = new Box2DDebugRenderer();

        ///////////////////////////////////////////////////////////////////////////
        //				                    Groovy                             	 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Groovy
        shell = new GroovyShell(new Binding());

        ///////////////////////////////////////////////////////////////////////////
        //				             Populate World                            	 //
        ///////////////////////////////////////////////////////////////////////////

        // PLAYER
        Texture playerTexture = new Texture(Gdx.files.internal(Constants.PLAYER_IMAGE_PATH));

        Body playerBody = B2DUtils.makeBody(world, Constants.PLAYER_SPAWN_X, Constants.PLAYER_SPAWN_Y,
                Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT,
                BodyDef.BodyType.DynamicBody, true,
                Constants.BIT_PLAYER, Constants.BIT_GROUND, (short) 0,
                new PlayerUserData());

        player = new Player(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, playerTexture,
                playerBody, shell, Constants.PLAYER_SCRIPT,
                Constants.PLAYER_SIDE_TO_SIDE_SPEED, Constants.PLAYER_JUMP_FORCE);

        stageGameWorld.addActor(player);

        currentGroovyActor = player;

        // GROUND
        Texture groundTexture = new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH));

        float groundWidth = Gdx.graphics.getWidth() / Constants.WORLD_TO_SCREEN;
        float groundHeight = 2;

        Body groundBody = B2DUtils.makeBody(world, (Gdx.graphics.getWidth() / 2) / Constants.WORLD_TO_SCREEN, 1,
                groundWidth, groundHeight,
                BodyDef.BodyType.KinematicBody, true,
                Constants.BIT_GROUND, Constants.BIT_PLAYER, (short) 0,
                new GroundUserData());

        GroovyActor ground = new GroovyActor(groundWidth, groundHeight, groundTexture,
                groundBody, shell, Constants.GROUND_SCRIPT);

        stageGameWorld.addActor(ground);

        // OBSTACLE
        Texture obstacleTexture = new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH));

        float obstacleWidth = 2;
        float obstacleHeight = 6;

        Body obstacleBody = B2DUtils.makeBody(world, (Gdx.graphics.getWidth() / 2) / Constants.WORLD_TO_SCREEN, 5,
                obstacleWidth, obstacleHeight,
                BodyDef.BodyType.KinematicBody, true,
                Constants.BIT_GROUND, Constants.BIT_PLAYER, (short) 0,
                new GroundUserData());

        GroovyActor obstacle = new GroovyActor(obstacleWidth, obstacleHeight, obstacleTexture,
                obstacleBody, shell, Constants.GROUND_SCRIPT);

        stageGameWorld.addActor(obstacle);

        ///////////////////////////////////////////////////////////////////////////
        //				                    Debug                             	 //
        ///////////////////////////////////////////////////////////////////////////

        // UI
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
        b2dr.render(world, stageGUI.getCamera().combined.scl(Constants.WORLD_TO_SCREEN));
    }

    ///////////////////////////////////////////////////////////////////////////
    //						Maintenance Methods								 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.

        // Input
        // Order that the events arrive. Priority matters.
        InputMultiplexer im = new InputMultiplexer(stageGameWorld, stageGUI, this);
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
        // Set values on Key Down
        switch (keycode)
        {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.A:
                player.setMoveLeft(true);
                break;
            case Input.Keys.D:
                player.setMoveRight(true);
                break;
            case Input.Keys.SPACE:
                player.Jump();
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        // Set values on Key Down
        switch (keycode)
        {
            case Input.Keys.A:
                player.setMoveLeft(false);
                break;
            case Input.Keys.D:
                player.setMoveRight(false);
                break;
        }

        return true;
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
    //								Contact									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void beginContact(Contact contact)
    {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((B2DUtils.bodyIsPlayer(a) && B2DUtils.bodyIsGround(b)) ||
                (B2DUtils.bodyIsGround(a) && B2DUtils.bodyIsPlayer(b)))
        {
            player.Grounded();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    ///////////////////////////////////////////////////////////////////////////
    //								Getters									 //
    ///////////////////////////////////////////////////////////////////////////

    public GroovyActor getCurrentGroovyActor()
    {
        return currentGroovyActor;
    }


}
