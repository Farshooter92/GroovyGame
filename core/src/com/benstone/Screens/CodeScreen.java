package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.benstone.Actors.CodeArea;
import com.benstone.Actors.GroovyActor;
import com.benstone.GroovyGame;
import com.benstone.Utils.Constants;
import com.benstone.Utils.FileUtils;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.File;
import java.io.IOException;

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
    private CodeArea codeArea;
    private Label consoleText;
    private Label exceptionsText;

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

        // Want table to take the whole screen
        rootTable.center();
        rootTable.setFillParent(true);

        // Skin acts as a container for all drawables
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        ///////////////////////////////////////////////////////////////////////////
        //						        CODE AREA								 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Widgets with skin
        codeArea = new CodeArea("Enter Code Here", skin);
        Window codeWindow = new Window("Code Editor", skin);
        codeWindow.setMovable(false);
        codeWindow.add(codeArea).expand().fill();

        ///////////////////////////////////////////////////////////////////////////
        //						    ACTION BUTTONS								 //
        ///////////////////////////////////////////////////////////////////////////

        Window actionButtonsWindow = new Window("Action Buttons", skin);
        actionButtonsWindow.setMovable(false);
        // Align all widgets to top
        actionButtonsWindow.top();

        // COMPILE BUTTON

        TextButton compileButton = new TextButton("Compile", skin);
        compileButton.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor)
            {

                if (groovyActor != null)
                {
                    try
                    {
                        groovyActor.setGroovyScriptText(codeArea.getText());
                        // If successful clear the exceptions list
                        logException("");
                    }
                    catch (CompilationFailedException cfe)
                    {
                        logException(cfe.getMessage());
                    }
                }
                else
                {
                    Gdx.app.log("Error", "Entered Code window with groovyActor set to null");
                }

            }
        });

        actionButtonsWindow.add(compileButton).expandX().fillX();

        // RUN BUTTON

        TextButton runButton = new TextButton("Run", skin);
        runButton.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor)
            {
                if (groovyActor != null)
                {
                    try
                    {
                        groovyActor.setGroovyScriptText(codeArea.getText());
                        groovyActor.runScript();
                        switchToPlayScreen();
                    }
                    catch (CompilationFailedException cfe)
                    {
                        logException(cfe.getMessage());
                    }
                    catch (Exception e)
                    {
                        logException(e.getMessage());
                    }
                }
                else
                {
                    Gdx.app.log("Error", "Entered Code window with groovyActor set to null");
                }
            }
        });

        actionButtonsWindow.row();
        actionButtonsWindow.add(runButton).expandX().fillX();

        // RESET BUTTON

        TextButton resetButton = new TextButton("Reset", skin);
        resetButton.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor)
            {
                groovyActor.resetScript();
                updateCodeArea();
            }
        });

        actionButtonsWindow.row();
        actionButtonsWindow.add(resetButton).expandX().fillX();

        // EXIT BUTTON

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor)
            {
                switchToPlayScreen();
            }
        });

        actionButtonsWindow.row();
        actionButtonsWindow.add(exitButton).expandX().fillX();

        ///////////////////////////////////////////////////////////////////////////
        //					CONSOLE AND EXCEPTION WINDOWS						 //
        ///////////////////////////////////////////////////////////////////////////

        // This is a label inside of a scroll pane inside of a window
        consoleText = new Label("", skin);
        consoleText.setAlignment(Align.topLeft);

        try {
            File hintFile = new File("hints.txt");
            consoleText.setText(FileUtils.fileToString(hintFile));
        }
        catch (IOException e)
        {
            Gdx.app.log("Error", "Can't find hints.txt");
        }

        ScrollPane consoleScroll = new ScrollPane(consoleText, skin);
        Window consoleWindow = new Window("Helpful Tips", skin);
        consoleWindow.setMovable(false);
        consoleWindow.add(consoleScroll).left().top().expand().fill();

        // This is a label inside of a scroll pane inside of a window
        exceptionsText = new Label("", skin,"default-font", Color.RED);
        exceptionsText.setAlignment(Align.topLeft);
        ScrollPane exceptionsScroll = new ScrollPane(exceptionsText, skin);
        Window exceptionsWindow = new Window("Exceptions", skin);
        exceptionsWindow.setMovable(false);
        exceptionsWindow.add(exceptionsScroll).left().top().expand().fill();

        // Put both windows in a horizontal scroll pane
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
        rootTable.add(sp).colspan(2).height(125).fillX();


        // Add actors to stage
        stage.addActor(rootTable);

        if (Constants.DEBUG_BUILD) {
            // Debug
            rootTable.setDebug(true);
            actionButtonsWindow.debug();
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
        // Order that the events arrive. Priority matters.
        InputMultiplexer im = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(im);

        // Get the currently selected Groovy Actor
        groovyActor = game.getPlayScreen().getCurrentGroovyActor();

        System.out.println(groovyActor.getName());
        updateCodeArea();

        //codeArea.setText();
    }

    @Override
    public void resize(int width, int height) {
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
        game.dispose();
        stage.dispose();
        skin.dispose();
        groovyActor.dispose();
    }

    public void switchToPlayScreen()
    {
        game.setScreen(game.getPlayScreen());
    }

    public void updateCodeArea()
    {
        // Change the code area to display the scriptText from the Groovy actor
        codeArea.setText(groovyActor.getGroovyScriptText());
    }

    private void logException(String exception)
    {
        exceptionsText.setText(exception);
    }

    ///////////////////////////////////////////////////////////////////////////
    //								Input									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.ESCAPE)
        {
            switchToPlayScreen();
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
