package com.benstone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.benstone.Actors.TestActor;
import groovy.lang.GroovyShell ;
import groovy.lang.GroovyClassLoader ;
import groovy.util.GroovyScriptEngine ;
import java.io.File;
import com.badlogic.gdx.utils.Timer;

public class GroovyGame extends ApplicationAdapter implements InputProcessor{

	// Scene2D
	private Stage stage;

	// UI
	private Skin skin;
	private Table table;
	private TextButton startButton;
	private TextButton quitButton;

	@Override
	public void create () {

		stage = new Stage(new ScreenViewport());

		// Initialize UI
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);

		table.setPosition(0, Gdx.graphics.getHeight());

		startButton = new TextButton("New Game", skin);
		quitButton = new TextButton("Quit Game", skin);

		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Clicked", "Yes you did");
			}
		});

		// Add elements actors to Table
		// Table formatting is sequential from top to bottom
		table.padTop(30);
		table.add(startButton).padBottom(30);
		table.row();
		table.add(quitButton);

		// Create Actors
		Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
		TestActor myTestActor = new TestActor(texture);

		// Set Actor Properties
		myTestActor.setName("TestActor");
		Vector2 pos = stage.screenToStageCoordinates(
				new Vector2(
						(float) Gdx.graphics.getWidth()/2 - texture.getWidth()/2,
						(float) Gdx.graphics.getHeight()/2 + texture.getHeight()/2));

		myTestActor.setPosition(pos.x, pos.y);

		// Create Groups
		Group testActors = new Group();

		// Set group properties
		testActors.setName("GroupTestActors");

		// Add actors to groups
		// Order they are added is the order they are drawn!
		testActors.addActor(myTestActor);

		// Add Actors to stage
		stage.addActor(testActors);
		stage.addActor(table);


		// Order that the events arrive. Priority matters.
		InputMultiplexer im = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(im);

		// Groovy
		try {
			runWithGroovyShell() ;
			runWithGroovyClassLoader() ;
			runWithGroovyScriptEngine() ;
		}
		catch (Exception e)
		{
			System.out.println("We done messed up.");
		}

	}

	///////////////////////////////////////////////////////////////////////////
	//						Core Game Loop									 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	///////////////////////////////////////////////////////////////////////////
	//						Maintenance Methods								 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void dispose()
	{
		super.dispose();

		// TODO Free up resources

	}

	///////////////////////////////////////////////////////////////////////////
	//								Input									 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public boolean keyDown(int keycode)
	{
		// stage is just a container
		// stage.getRoot().findActor("ace");
		Array<Actor> actors = stage.getActors();

		Group g = null;

		// TODO Find a better way to get a actor from the stage
		for(int i = 0; i < actors.size -1; i++)
		{
			if(actors.get(i).getName().compareTo("GroupTestActors") == 0)
			{
				g = (Group) actors.get(i);
			}
		}

		TestActor actor = (TestActor) g.findActor("TestActor");

		if (keycode == Input.Keys.RIGHT)
		{
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
			{
				actor.setRotation(actor.getRotation() + 1f);
			}
			else
			{
				g.setRotation(g.getRotation() + 1f);
			}
		}

		if (keycode == Input.Keys.LEFT)
		{
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
			{
				actor.setRotation(actor.getRotation() - 1f);
			}
			else
			{
				g.setRotation(g.getRotation() - 1f);
			}
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		// Screen Coords Top Left of screen
		// Stage Coords bottom left of the world

		// Normally you should store this variable somewhere else and override its value
		Vector2 coord = stage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));

		// True if you only want to catch touchables
		// Can be overriden to be different shapes
		Actor hitActor = stage.hit(coord.x, coord.y, false);

		if (hitActor != null)
		{
			Gdx.app.log("Hit", hitActor.getName());
		}

		return true;
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
	//								Groovy									 //
	///////////////////////////////////////////////////////////////////////////

	static void runWithGroovyShell() throws Exception
	{
		new GroovyShell().parse( new File("test.groovy") ).invokeMethod( "hello_world", null ) ;
	}

	static void runWithGroovyClassLoader() throws Exception
	{
		// Declaring a class to conform to a java interface class would get rid of
		// a lot of the reflection here
		Class scriptClass = new GroovyClassLoader().parseClass( new File("test.groovy") ) ;
		Object scriptInstance = scriptClass.newInstance() ;
		scriptClass.getDeclaredMethod( "hello_world", new Class[] {} ).invoke( scriptInstance, new Object[] {} ) ;
	}

	static void runWithGroovyScriptEngine() throws Exception
	{
		// Declaring a class to conform to a java interface class would get rid of
		// a lot of the reflection here
		Class scriptClass = new GroovyScriptEngine( "." ).loadScriptByName("test.groovy") ;
		Object scriptInstance = scriptClass.newInstance() ;
		scriptClass.getDeclaredMethod( "hello_world", new Class[] {} ).invoke( scriptInstance, new Object[] {} ) ;
	}
}
