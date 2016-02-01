package com.benstone.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Disposable;
import com.benstone.Utils.B2DUtils;

/**
 * Created by Ben on 1/31/2016.
 */
public class B2DActor extends Actor implements Disposable{

    // Box2D
    protected Body body;
    private float width;
    private float height;

    // Screen rendered box
    protected Rectangle screenRectangle;

    // Texture to be rendered
    TextureRegion image;

    public B2DActor(float inWidth, float inHeight, Texture texture, Body inBody)
    {
        width = inWidth;
        height = inHeight;
        image = new TextureRegion(texture);
        this.body = inBody;

        screenRectangle = new Rectangle();

        setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Divide width in two because Box2D doubles it
        screenRectangle.x = B2DUtils.transformToScreen(body.getPosition().x - width / 2);
        screenRectangle.y = B2DUtils.transformToScreen(body.getPosition().y - height / 2);
        screenRectangle.width = B2DUtils.transformToScreen(width);
        screenRectangle.height = B2DUtils.transformToScreen(height);

        setBounds(screenRectangle.x, screenRectangle.y, screenRectangle.width, screenRectangle.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        batch.setColor(this.getColor());

        batch.draw(image, screenRectangle.x, screenRectangle.y, screenRectangle.getWidth(),
                screenRectangle.getHeight());

    }

    @Override
    public void dispose() {
        image.getTexture().dispose();
    }

    ///////////////////////////////////////////////////////////////////////////
    //				                 Getters                               	 //
    ///////////////////////////////////////////////////////////////////////////

    public Body getBody() {
        return body;
    }
}
