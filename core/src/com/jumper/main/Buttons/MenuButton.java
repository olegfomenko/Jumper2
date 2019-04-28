package com.jumper.main.Buttons;

import com.badlogic.gdx.graphics.Texture;

public class MenuButton extends Button {

    public MenuButton(float x, float y, float width, float height) {
        super(new Texture("menu.png"), new Texture("menu_press.png"), new Texture("menu.png"), width, height, x, y);
    }
}
