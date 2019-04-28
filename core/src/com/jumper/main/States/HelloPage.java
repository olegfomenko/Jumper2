package com.jumper.main.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jumper.main.Buttons.Button;
import com.jumper.main.Buttons.StartMultiButton;
import com.jumper.main.Buttons.StartSingleButton;
import com.jumper.main.Game.Clouds;
import com.jumper.main.GameStateManager;

import java.util.ArrayList;

import static com.jumper.main.Main.*;

public class HelloPage implements State {
    private OrthographicCamera camera;
    private GameStateManager gsm;

    private Texture bg;
    private StartSingleButton single;
    private StartMultiButton multi;

    private SingleGameState singleGame;
    private OnlineGameState onlineGame;

    private ArrayList<Button> buttons;

    private Clouds clouds;

    public static final int BUTTON_WIDTH = 220, BUTTON_HEIGHT = 100;

    public HelloPage(GameStateManager gsm) {
        this.gsm = gsm;

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();

        bg = new Texture("bg.png");
        single = new StartSingleButton(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 2 - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        multi = new StartMultiButton(single.getX(), single.getY() + BUTTON_HEIGHT + 20, BUTTON_WIDTH, BUTTON_HEIGHT);

        buttons = new ArrayList<Button>();

        buttons.add(single);
        buttons.add(multi);

        singleGame = new SingleGameState(gsm);
        onlineGame = new OnlineGameState(gsm);

        clouds = new Clouds(100, HEIGHT - 100, 210, 120, 200);
    }

    @Override
    public void update(float dt) {
        clouds.update(dt, 0);
        if(Gdx.input.isTouched()) {
            Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            Rectangle input = new Rectangle(v.x, v.y,  1, 1);
            for(Button b : buttons) b.checkPress(input);
        } else {
            for(Button b : buttons) b.checkPressed();

            if(single.isPressed()) {
                single.setPressed(false);

                singleGame.reset();
                gsm.push(singleGame);
            }

            if(multi.isPressed()) {
                multi.setPressed(false);

                onlineGame.reset();
                gsm.push(onlineGame);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(bg, 0, 0, WIDTH, HEIGHT);
        clouds.render(sb);
        for(Button b : buttons) b.render(sb);

        sb.end();
    }

    @Override
    public void dispose() {
        for(Button b : buttons) b.dispose();
        bg.dispose();
        clouds.dispose();
    }
}
