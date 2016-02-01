package com.benstone.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import groovy.lang.GroovyShell;

/**
 * Created by Ben on 1/29/2016.
 */
public class Player extends GroovyActor{

    // Movement
    private boolean moveLeft = false;
    private boolean moveRight = false;
    public float sideToSideSpeed = 1;

    private boolean isJumping = false;
    private boolean isGrounded = false;
    private float jumpForce = 1;

    public Player(float inWidth, float inHeight, Texture texture, Body inBody,
                  GroovyShell inShell, String scriptFileName,
                  float inSideToSideSpeed, float inJumpForce)
    {
        super(inWidth, inHeight, texture, inBody,
            inShell, scriptFileName);

        sideToSideSpeed = inSideToSideSpeed;
        jumpForce = inJumpForce;
    }

    ///////////////////////////////////////////////////////////////////////////
    //				            Core Game Loop                             	 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void act(float delta)
    {
        super.act(delta);

        // Reset values so we don't move when nothing is pressed
        int sideToSideDir = 0;

        // Should I move to left
        if(moveLeft)
        {
            sideToSideDir -= 1;
        }

        // Should I move to right
        if(moveRight)
        {
            sideToSideDir += 1;
        }

        // Update Body Position
        body.setLinearVelocity(new Vector2(sideToSideDir * sideToSideSpeed, body.getLinearVelocity().y));

    }

    ///////////////////////////////////////////////////////////////////////////
    //				                 Movement                             	 //
    ///////////////////////////////////////////////////////////////////////////

    public void Jump()
    {
        if (!isJumping && isGrounded) {
            // Update Body Position
            body.applyForceToCenter(new Vector2(0, jumpForce), true);
            isJumping = true;
        }
    }

    public void Grounded()
    {
        isJumping = false;
        isGrounded = true;
    }

    ///////////////////////////////////////////////////////////////////////////
    //				                 Setters                             	 //
    ///////////////////////////////////////////////////////////////////////////

    public void setMoveRight(boolean moveRight)
    {
        this.moveRight = moveRight;
    }

    public void setMoveLeft(boolean moveLeft)
    {
        this.moveLeft = moveLeft;
    }
}
