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
    private boolean binaryInput = true;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    public float sideToSideSpeed = 1;
    // Must be between -1 and 1
    public float sideToSideInput = 0;

    private boolean isJumping = false;
    private boolean isGrounded = false;

    private float jumpForce = 1;

    public Player(float inWidth, float inHeight, Texture texture, Body inBody,
                  GroovyShell inShell, String scriptFileName, String uniqueID,
                  float inSideToSideSpeed, float inJumpForce)
    {
        super(inWidth, inHeight, texture, inBody,
            inShell, scriptFileName, uniqueID);

        setScriptProperty("Actor", this);

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

        if(binaryInput) {
            // Reset values so we don't move when nothing is pressed
            int sideToSideDir = 0;

            // Should I move to left
            if (moveLeft) {
                sideToSideDir -= 1;
            }

            // Should I move to right
            if (moveRight) {
                sideToSideDir += 1;
            }

            // Update Body Position
            body.setLinearVelocity(new Vector2(sideToSideDir * sideToSideSpeed, body.getLinearVelocity().y));
        }
        else
        {
            // Update Body Position
            body.setLinearVelocity(new Vector2(sideToSideInput * sideToSideSpeed, body.getLinearVelocity().y));
        }

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

    public void setJumpForce(float jumpForce)
    {
        this.jumpForce = jumpForce;
    }

    public void setMoveRight(boolean moveRight)
    {
        this.binaryInput = true;
        this.moveRight = moveRight;
    }

    public void setMoveLeft(boolean moveLeft)
    {
        this.binaryInput = true;
        this.moveLeft = moveLeft;
    }

    public void setSideToSideInput(float sideToSideInput)
    {
        this.binaryInput = false;
        this.sideToSideInput = sideToSideInput;
    }

    public void setSideToSideSpeed(float sideToSideSpeed)
    {
        this.sideToSideSpeed = sideToSideSpeed;
    }
}
