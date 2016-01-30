package com.benstone.Actors;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.benstone.Screens.PlayScreen;
import groovy.lang.GroovyShell;

/**
 * Created by Ben on 1/29/2016.
 */
public class Player extends B2DGroovyActor implements InputProcessor
{
    // Movement States
    public boolean moveRight = false;
    public boolean moveLeft = false;
    public boolean moveUp = false;
    public boolean moveDown = false;

    // Velocity
    public float speedSideToSide = 1f;
    public float speedForwardAndBack = 1f;

    public Player(Texture texture, GroovyShell inShell, String scriptFileName,
                  final World world, boolean isStatic, boolean fixedRotation,
                  short cBits, short mBits, short gIndex,
                  float inSpeedSideToSide, float inSpeedForwardAndBack)
    {
        super(texture, inShell, scriptFileName,
        world, isStatic, fixedRotation,
        cBits, mBits, gIndex);

        // Velocity
        speedSideToSide = inSpeedSideToSide;
        speedForwardAndBack = inSpeedForwardAndBack;
    }

    ///////////////////////////////////////////////////////////////////////////
    //						Core Game Loop									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void act(float delta)
    {
        super.act(delta);
    }

    ///////////////////////////////////////////////////////////////////////////
    //								Input									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyDown(int keycode) {
        return false;
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
