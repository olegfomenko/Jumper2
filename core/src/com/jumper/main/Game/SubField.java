package com.jumper.main.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.jumper.main.Game.Field.*;


public class SubField {
    private Texture subfield;
    private float x, y, width, height;
    private boolean used;

    public SubField(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        subfield = new Texture("subfield.png");
        used = false;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void used() {
        used = true;
    }

    public boolean isUsed() {
        return used;
    }

    public void reSet(float x, float y, float height) {
        this.x = x;
        this.y = y;
        this.height = height;
        used = false;
    }

    public Rectangle getSubField() {
        return new Rectangle(x, y, width, height);
    }

    public void update(float dt) {
        x -= X_VELOSITY * dt;
    }

    public void render(SpriteBatch sb) {
        sb.draw(subfield, x, y, width, height);
    }

    public void dispose() {
        subfield.dispose();
    }
}
