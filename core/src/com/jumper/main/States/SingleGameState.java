package com.jumper.main.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jumper.main.Game.Field;
import com.jumper.main.GameStateManager;

import static com.jumper.main.Main.*;

public class SingleGameState implements GameState {
    private OrthographicCamera camera;
    private GameStateManager gsm;

    private Texture bg;

    protected Field field;

    public SingleGameState(GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();

        bg = new Texture("bg.png");

        field = new Field(gsm, this);
    }

    public void reset() {
        field.reSet();
    }

    @Override
    public void update(float dt) {
        field.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(bg, 0, 0, WIDTH, HEIGHT);
        field.render(sb);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        field.dispose();
    }
}
