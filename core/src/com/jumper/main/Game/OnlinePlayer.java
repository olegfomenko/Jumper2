package com.jumper.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OnlinePlayer {
    private Texture player;

    private float x, y, width, height;
    private boolean pressed;

    public OnlinePlayer(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        player = new Texture("player.png");
        pressed = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void update() {
        pressed = Gdx.input.isTouched();
    }

    public void render(SpriteBatch sb) {
        sb.draw(player, x, y, width, height);
    }

    public void dispose() {
        player.dispose();
    }
}
