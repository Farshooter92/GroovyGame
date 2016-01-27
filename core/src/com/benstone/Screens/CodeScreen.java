package com.benstone.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.benstone.GroovyGame;

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

        // UI
        rootTable = new Table();

        // Skin acts as a container for all drawables
        skin = new Skin();

        skin.add("default", new BitmapFont(Gdx.files.internal("calibri.fnt")));

        skin.add("cursor", new Texture("textCursor.png"));

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));



        // Want table to take the whole screen
        rootTable.setFillParent(true);;

        // Clipping
        // table.setClip(true);

        // Initialize Widgets

        Window.WindowStyle codeWindowStyle = new Window.WindowStyle();
        codeWindowStyle.titleFont = skin.getFont("default");
        codeWindowStyle.titleFontColor = Color.BLACK;
        codeWindowStyle.background = skin.newDrawable("white", Color.WHITE);
        codeWindowStyle.stageBackground = skin.newDrawable("white", Color.BLUE);
        skin.add("default", codeWindowStyle);

        // Set up the style for the code area
        TextField.TextFieldStyle codeAreaStyle = new TextField.TextFieldStyle();
        codeAreaStyle.font = skin.getFont("default");
        codeAreaStyle.fontColor = Color.BLACK;
        codeAreaStyle.cursor = skin.getDrawable("cursor");
        codeAreaStyle.background = skin.newDrawable("white", Color.WHITE);
        skin.add("default", codeAreaStyle);

        // Initialize Widgets with skin
        codeArea = new TextArea("Enter Code Here", skin);
        Window codeWindow = new Window("Code Editor", codeWindowStyle);
        //codeWindow.add(codeArea);

        Window actionButtonsWindow = new Window("Action Buttons", codeWindowStyle);

        Window consoleWindow = new Window("Console", codeWindowStyle);

        // Add widgets to table here
        // Table formatting is sequential from top to bottom
        // Wrap widgets in Container to do manual transforms
        // Use ChangeListener where applicable in widgets instead of Clicked
        rootTable.add(codeArea).expand().fill();
        rootTable.add(actionButtonsWindow).width(150).fillY();
        rootTable.row();
        rootTable.add(consoleWindow).colspan(2).height(100).fillX();

        // Add actors to stage
        stage.addActor(rootTable);

        // Debug
        rootTable.setDebug(true);

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
}
