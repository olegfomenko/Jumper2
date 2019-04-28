package com.jumper.main.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Vector;

import static com.jumper.main.Main.*;


public class Clouds {
    private Texture cloud;
    private float down, up;
    private float distance;
    private float minWidth, maxWidth;
    private Vector<Rectangle> clouds;

    private boolean forFirst = true;

    public static final float CLOUDS_VELOCITY = 100;

    public Clouds(float down, float up, float distance, float minWidth, float maxWidth) {
        this.down = down;
        this.up = up;
        this.distance = distance;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;

        cloud = new Texture("cloud.png");
        clouds = new Vector<Rectangle>();

        int count = (int)Math.ceil(WIDTH / distance) + 2;


        clouds.add(getNewCloud(0));
        clouds.get(0).x = 50;

        for(int i = 2; i < count; ++i) clouds.add(getNewCloud(clouds.lastElement().x));
    }

    private Rectangle getNewCloud(float x) {
        float size = getSize();
        return new Rectangle(x + distance, getPosition(), size, size / 2);
    }

    private float getSize() {
        return (float)(Math.random() * (maxWidth - minWidth) + minWidth);
    }

    private float getPosition() {
        return (float)(Math.random() * (up - down) + down);
    }

    public void update(float dt, float velo) {
        for(Rectangle r : clouds) r.x -= (CLOUDS_VELOCITY + velo) * dt;
        if(clouds.get(0).x + clouds.get(0).width <= 0) {
            clouds.remove(0);
            clouds.add(getNewCloud(clouds.lastElement().x));
        }
    }

    public void render(SpriteBatch sb) {
        for(Rectangle r : clouds) {
            sb.draw(cloud, r.x, r.y, r.width, r.height);
        }
    }

    public void dispose() {
        cloud.dispose();
    }
}
