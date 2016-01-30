package com.benstone.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import groovy.lang.GroovyShell;

import static com.benstone.Utils.Constants.PPM;

/**
 * Created by Ben on 1/29/2016.
 */
public class B2DGroovyActor extends GroovyActor {

    // Box2D
    private Body body;

    // TODO figure out a way to shorten this long winded constructor
    public B2DGroovyActor (Texture texture, GroovyShell inShell, String scriptFileName,
                           final World world, boolean isStatic, boolean fixedRotation,
                           short cBits, short mBits, short gIndex)
    {
        super(texture, inShell, scriptFileName);

        // Box2D
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.position.set(this.getX() / PPM, this.getY() / PPM);

        if(isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        System.out.println(this.getWidth());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2 / PPM, getHeight() / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        body = world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }

    ///////////////////////////////////////////////////////////////////////////
    //						        Getters	    							 //
    ///////////////////////////////////////////////////////////////////////////
    public Body getBody()
    {
        return body;
    }
}
