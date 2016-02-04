package com.benstone.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.benstone.Utils.Constants;

/**
 * A purely experimental class
 */
public class TestActor extends Actor {

    protected Body body;
    // Screen rendered box
    protected Rectangle screenRectangle;

    TextureRegion textureRegion;

    public TestActor(Texture texture, Body inBody)
    {
        textureRegion = new TextureRegion(texture);

        setBounds(getX(), getY(), getWidth(), getHeight());

        this.body = inBody;
        screenRectangle = new Rectangle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Divide width in two because Box2D doubles it
        screenRectangle.x = transformToScreen(body.getPosition().x - 4 / 2);
        screenRectangle.y = transformToScreen(body.getPosition().y - 4 / 2);
        screenRectangle.width = transformToScreen(4);
        screenRectangle.height = transformToScreen(4);

    }

    protected float transformToScreen(float n) {
        return Constants.WORLD_TO_SCREEN * n;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        batch.setColor(this.getColor());

        batch.draw(textureRegion, screenRectangle.x, screenRectangle.y, screenRectangle.getWidth(),
                screenRectangle.getHeight());

    }


}
