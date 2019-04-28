package com.jumper.main.Buttons;

import com.badlogic.gdx.graphics.Texture;

public class RestartButton extends Button {

    public RestartButton(float x, float y, float width, float height) {
        super(new Texture("restart.png"), new Texture("restart_press.png"), new Texture("restart.png"), width, height, x, y);
    }
}
