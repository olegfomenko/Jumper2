package com.jumper.main.Buttons;

import com.badlogic.gdx.graphics.Texture;

public class StartSingleButton extends Button {

    public StartSingleButton(float x, float y, float width, float height) {
        super(new Texture("startSingle.png"), new Texture("startSingle_press.png"), new Texture("startSingle.png"), width, height, x, y);
    }
}
