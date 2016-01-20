package com.benstone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import groovy.lang.GroovyShell ;
import groovy.lang.GroovyClassLoader ;
import groovy.util.GroovyScriptEngine ;
import java.io.File;

public class GroovyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

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

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	static void runWithGroovyShell() throws Exception {
		new GroovyShell().parse( new File("test.groovy") ).invokeMethod( "hello_world", null ) ;
	}

	static void runWithGroovyClassLoader() throws Exception {
		// Declaring a class to conform to a java interface class would get rid of
		// a lot of the reflection here
		Class scriptClass = new GroovyClassLoader().parseClass( new File("test.groovy") ) ;
		Object scriptInstance = scriptClass.newInstance() ;
		scriptClass.getDeclaredMethod( "hello_world", new Class[] {} ).invoke( scriptInstance, new Object[] {} ) ;
	}

	static void runWithGroovyScriptEngine() throws Exception {
		// Declaring a class to conform to a java interface class would get rid of
		// a lot of the reflection here
		Class scriptClass = new GroovyScriptEngine( "." ).loadScriptByName("test.groovy") ;
		Object scriptInstance = scriptClass.newInstance() ;
		scriptClass.getDeclaredMethod( "hello_world", new Class[] {} ).invoke( scriptInstance, new Object[] {} ) ;
	}

}
