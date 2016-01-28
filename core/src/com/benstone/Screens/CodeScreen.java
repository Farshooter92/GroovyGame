package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.benstone.Actors.GroovyActor;
import com.benstone.GroovyGame;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * Created by Ben on 1/27/2016.
 */
public class CodeScreen implements Screen, InputProcessor
{

    ///////////////////////////////////////////////////////////////////////////
    //						    Instance Variables							 //
    ///////////////////////////////////////////////////////////////////////////

    // Reference to main game object to switch screens and access its data
    GroovyGame game;

    // Scene2D
    private Stage stage;

    // UI
    private Table rootTable;
    private Skin skin;
    private TextArea codeArea;

    // Current GroovyActor
    private GroovyActor groovyActor;


    ///////////////////////////////////////////////////////////////////////////
    //						    Constructors								 //
    ///////////////////////////////////////////////////////////////////////////

    public CodeScreen(GroovyGame inGame)
    {
        game = inGame;

        // Scene2D
        // stage = new Stage(new FitViewport(640, 480));
        // stage = new Stage(new ExtendViewport(640, 480));
        stage = new Stage(new ScreenViewport());

        ///////////////////////////////////////////////////////////////////////////
        //						       ROOT TABLE								 //
        ///////////////////////////////////////////////////////////////////////////

        rootTable = new Table();

        // Skin acts as a container for all drawables
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Want table to take the whole screen
        rootTable.center();
        rootTable.setFillParent(true);

        ///////////////////////////////////////////////////////////////////////////
        //						        CODE AREA								 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Widgets with skin
        codeArea = new TextArea("Enter Code Here", skin);
        Window codeWindow = new Window("Code Editor", skin);
        codeWindow.add(codeArea).expand().fill();

        ///////////////////////////////////////////////////////////////////////////
        //						    ACTION BUTTONS								 //
        ///////////////////////////////////////////////////////////////////////////

        Window actionButtonsWindow = new Window("Action Buttons", skin);

        // Align all widgets to top
        actionButtonsWindow.top();

        // COMPILE BUTTON

        TextButton compileButton = new TextButton("Compile", skin);
        compileButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor)
            {
                System.out.println("Compile Button Pressed");

                if (groovyActor != null)
                {
                    try
                    {
                        groovyActor.evaluateScript(codeArea.getText());
                    }
                    catch (CompilationFailedException cfe)
                    {
                        // TODO print message to exception window
                        System.out.println("Script comiplation failed.");
                    }
                }

            }
        });

        actionButtonsWindow.add(compileButton).expandX().fillX();

        // RUN BUTTON

        TextButton runButton = new TextButton("Run", skin);
        runButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                System.out.println("Run Button Pressed");

                // Switch to play screen and run code
            }
        });

        actionButtonsWindow.row();
        actionButtonsWindow.add(runButton).expandX().fillX();

        // RESET BUTTON

        TextButton resetButton = new TextButton("Reset", skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                System.out.println("Reset Button Pressed");
            }
        });

        actionButtonsWindow.row();
        actionButtonsWindow.add(resetButton).expandX().fillX();

        ///////////////////////////////////////////////////////////////////////////
        //					CONSOLE AND EXCEPTION WINDOWS						 //
        ///////////////////////////////////////////////////////////////////////////

        Window consoleWindow = new Window("Console", skin);
        Window exceptionsWindow = new Window("Exceptions", skin);
        SplitPane sp = new SplitPane(consoleWindow, exceptionsWindow, false, skin);

        ///////////////////////////////////////////////////////////////////////////
        //				ADD WIDGETS TO TABLE AND ADD ACTORS TO STAGE           	 //
        ///////////////////////////////////////////////////////////////////////////

        // Add widgets to table here
        // Table formatting is sequential from top to bottom
        // Wrap widgets in Container to do manual transforms
        // Use ChangeListener where applicable in widgets instead of Clicked
        rootTable.add(codeWindow).expand().fill();
        rootTable.add(actionButtonsWindow).width(150).fillY();
        rootTable.row();
        //rootTable.add(consoleWindow).colspan(2).height(100).fillX();
        rootTable.add(sp).colspan(2).height(100).fillX();


        // Add actors to stage
        stage.addActor(rootTable);

        // Debug
        rootTable.setDebug(true);
        actionButtonsWindow.debug();

        // Order that the events arrive. Priority matters.
        InputMultiplexer im = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(im);
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

        // Get the currently selected Groovy Actor
        groovyActor = game.playScreen.getCurrentGroovyActor();

        //codeArea.setText();
    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose() {

    }

    ///////////////////////////////////////////////////////////////////////////
    //								Input									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.G)
        {
            game.setScreen(game.playScreen);
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
    //								Setters									 //
    ///////////////////////////////////////////////////////////////////////////

    public void setGroovyActor(GroovyActor groovyActor) {
        this.groovyActor = groovyActor;
    }
}
