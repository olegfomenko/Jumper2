package com.jumper.main.Buttons;

import com.badlogic.gdx.graphics.Texture;

public class StartMultiButton extends Button {

    public StartMultiButton(float x, float y, int width, int height) {
        super(new Texture("startMulti.png"), new Texture("startMulti_press.png"), new Texture("startMulti.png"), width, height, x, y);
    }
}
