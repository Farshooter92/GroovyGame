package com.benstone.Actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

/**
 * Created by Ben on 2/8/2016.
 */
public class CodeArea extends TextArea {
    public CodeArea(String text, Skin skin) {
        super(text, skin);
    }

    public CodeArea(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public CodeArea(String text, TextFieldStyle style) {
        super(text, style);
    }
}
