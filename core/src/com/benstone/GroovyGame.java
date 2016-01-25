package com.benstone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

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
import com.benstone.Actors.GroovyActor;
import groovy.lang.Binding;
import groovy.lang.GroovyShell ;
import groovy.lang.GroovyClassLoader ;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine ;
import java.io.File;

public class GroovyGame extends ApplicationAdapter implements InputProcessor{

	// Scene2D
	private Stage stage;

	// UI
	private Skin skin;
	private Table table;
	private TextButton startButton;
	private TextButton quitButton;

	// Groovy
	private GroovyShell shell;

	@Override
	public void create () {

		// Scene2D
		stage = new Stage(new ScreenViewport());

		// Groovy
		shell = new GroovyShell(new Binding());

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
		stage.addActor(table);


		// Order that the events arrive. Priority matters.
		InputMultiplexer im = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(im);

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

		Group g = stage.getRoot().findActor("GroupTestActors");

		GroovyActor actor = (GroovyActor) g.findActor("GroovyActor");

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
		GroovyActor hitActor = (GroovyActor) stage.hit(coord.x, coord.y, false);

		if (hitActor != null)
		{
			Gdx.app.log("Hit", hitActor.getName());

			// TODO Bring up Dialog that allows you to edit the script file of the hitActor and print any exceptions that occur
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

}
