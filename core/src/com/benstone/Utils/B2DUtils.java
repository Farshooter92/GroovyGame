package com.benstone.Utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.benstone.B2DUserData.UserData;
import com.benstone.Enums.UserDataType;


/**
 * Created by Ben on 1/30/2016.
 */
public class B2DUtils
{
    /**
     * Utility method to create a B2D body
     * @param world B2D world instance to which the body will belong to
     * @param x Position X in the world
     * @param y Position Y in the world
     * @param width Width in world coordinates
     * @param height Hwight in world coordinates
     * @param bodyType
     * @param fixedRotation
     * @param cBits What is it
     * @param mBits What it will block
     * @param gIndex
     * @param userData
     * @return
     */
    public static Body makeBody(final World world, float x, float y, float width, float height,
                                BodyDef.BodyType bodyType, boolean fixedRotation,
                                short cBits, short mBits, short gIndex, UserData userData)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.position.set(x , y );

        bodyDef.type = bodyType;

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;

        body.createFixture(fixtureDef);

        body.resetMassData();
        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    /**
     * Helper function to get the actual coordinates in my world
     *
     * @param x X screen coordinate
     * @param y Y screen coordinate
     */
    public static Vector3 translateScreenToWorldCoordinates(Camera camera, int x, int y)
    {
        return camera.unproject(new Vector3(x, y, 0));
    }

    /**
     * Used to transform B2D coordinates to Screen Coordinates
     * @param n
     * @return
     */
    public static float transformToScreen(float n)
    {
        return Constants.WORLD_TO_SCREEN * n;
    }

    /**
     * Returns true if body contains Player user data
     * @param body
     * @return
     */
    public static boolean bodyIsPlayer(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.PLAYER;
    }

    /**
     * Returns true if body contains Ground user data
     * @param body
     * @return
     */
    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }

}
