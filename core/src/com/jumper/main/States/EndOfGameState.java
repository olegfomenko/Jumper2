package com.jumper.main.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jumper.main.Buttons.Button;
import com.jumper.main.Buttons.MenuButton;
import com.jumper.main.Buttons.RestartButton;
import com.jumper.main.Game.Clouds;
import com.jumper.main.GameStateManager;
import com.jumper.main.Main;

import java.io.File;


import static com.jumper.main.Main.HEIGHT;
import static com.jumper.main.Main.WIDTH;

import static com.jumper.main.States.HelloPage.*;

public class EndOfGameState implements State {

    private OrthographicCamera camera;
    private GameStateManager gsm;
    private Texture bg, lose, gameover;

    private RestartButton restart;
    private MenuButton menu;
    private State game;

    private BitmapFont printScore;

    private File scoreFile;
    private FileHandle editScore;

    private Clouds clouds;

    private int score, maxScore;

    public EndOfGameState(GameStateManager gsm, State game) {
        this.gsm = gsm;
        this.game = game;

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();

        bg = new Texture("bg.png");
        lose = new Texture("lose.png");
        gameover = new Texture("gameover.png");
        restart = new RestartButton(WIDTH / 2 - BUTTON_WIDTH / 2, HEIGHT / 2 - BUTTON_HEIGHT - 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        menu = new MenuButton(restart.getX(), restart.getY() + restart.getHeight() + 20, BUTTON_WIDTH, BUTTON_HEIGHT);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("tahoma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 35;
        parameter.color = Color.BLACK;
        printScore = generator.generateFont(parameter);
        generator.dispose();

        scoreFile = new File(Main.storage + Main.directory + "/score.txt");
        editScore = new FileHandle(scoreFile);
        maxScore = Integer.parseInt(editScore.readString());

        clouds = new Clouds(100, HEIGHT - 100, 210, 120, 200);
    }

    public void setScore(int score) {
        this.score = score;
        if(score > maxScore) {
            editScore.writeString("" + score, false);
            maxScore = score;
        }
    }

    @Override
    public void update(float dt) {
        clouds.update(dt, 0);

        if(Gdx.input.isTouched()) {
            Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            Rectangle input = new Rectangle(v.x, v.y,  1, 1);
            restart.checkPress(input);
            menu.checkPress(input);
        } else {
            restart.checkPressed();
            if(restart.isPressed()) {
                restart.setPressed(false);
                gsm.pop();

                if(game instanceof SingleGameState) ((SingleGameState)game).reset();
                if(game instanceof OnlineGameState) ((OnlineGameState)game).reset();
            }

            menu.checkPressed();
            if(menu.isPressed()) {
                menu.setPressed(false);
                gsm.pop();
                gsm.pop();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(bg, 0, 0, WIDTH, HEIGHT);
        clouds.render(sb);
        restart.render(sb);
        menu.render(sb);
        //sb.draw(lose, WIDTH / 2 - 115, menu.getY() + menu.getHeight(), 230, 195);

        float y = restart.getY() - 100;
        printScore.draw(sb, "Your score is " + score, restart.getX(), y);
        printScore.draw(sb, "Best score is " + maxScore, restart.getX(), y - 50);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        lose.dispose();
        restart.dispose();
        clouds.dispose();
    }
}
