package com.benstone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.benstone.Screens.CodeScreen;
import com.benstone.Screens.PlayScreen;
import com.benstone.Screens.SplashScreen;

public class GroovyGame extends Game{

	public PlayScreen playScreen;
	public CodeScreen codeScreen;

	@Override
	public void create ()
	{
		playScreen = new PlayScreen(this);
		codeScreen = new CodeScreen(this);
		// Get the game rolling
		setScreen(new SplashScreen(this));
	}

	///////////////////////////////////////////////////////////////////////////
	//						Core Game Loop									 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void render ()
	{
		super.render();
	}

	///////////////////////////////////////////////////////////////////////////
	//						Maintenance Methods								 //
	///////////////////////////////////////////////////////////////////////////

	@Override
	public void dispose()
	{
		super.dispose();

		// If it implements the Disposable interface then it should be disposed.
	}
}
