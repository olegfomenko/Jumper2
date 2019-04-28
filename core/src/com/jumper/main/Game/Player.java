package com.jumper.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.jumper.main.Game.Field.*;
import static com.jumper.main.Main.HEIGHT;

public class Player {
    private Texture player;
    private float x, y, width, height;
    private boolean pressed, active;

    private float currentVelosity;

    public Player(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        player = new Texture("player.png");
        pressed = false;
        active = true;
        currentVelosity = Y_VELOSITY;
    }

    public Rectangle getPlayer() {
        return new Rectangle(x, y, width, height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void contact() {
        pressed = false;
        active = true;
        currentVelosity = Y_VELOSITY;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(float dt) {
        if(Gdx.input.isTouched()) {
            pressed = true;
            active = false;
        } else if(pressed) {
            pressed = false;
            if(currentVelosity > 0) currentVelosity = 1;
        }

        if(!active) {
            currentVelosity -= A * dt;
            y += currentVelosity * dt;
        }

        if(y + height >= HEIGHT && pressed) {
            currentVelosity = -currentVelosity;
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(player, x, y, width, height);
    }

    public void dispose() {
        player.dispose();
    }
}
