package com.jumper.main.States;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jumper.main.Game.Field;
import com.jumper.main.Game.OnlineField;
import com.jumper.main.Game.OnlinePlayer;
import com.jumper.main.GameStateManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static com.jumper.main.Main.HEIGHT;
import static com.jumper.main.Main.WIDTH;

public class OnlineGameState implements GameState {
    private OrthographicCamera camera;
    private GameStateManager gsm;

    private Texture bg;

    private OnlineField field;
    private DatagramSocket client;

    public OnlineGameState(GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();

        bg = new Texture("bg.png");

        try {
            client = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        field = new OnlineField(gsm, client, this);
    }

    @Override
    public void update(float dt) {
        field.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.setProjectionMatrix(camera.combined);

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
