package com.benstone.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Ben on 1/22/2016.
 * An actor to simply test scripting functionality
 */
public class TestActor extends Image
{
    public TestActor(Texture texture)
    {
        super(texture);

        setBounds(getX(), getY(), getWidth(), getHeight());


    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        batch.setColor(this.getColor());

        ((TextureRegionDrawable)getDrawable()).draw(batch, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

    }
}
