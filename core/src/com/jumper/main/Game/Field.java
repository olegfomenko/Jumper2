package com.jumper.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.jumper.main.GameStateManager;
import com.jumper.main.States.EndOfGameState;
import com.jumper.main.States.SingleGameState;
import java.util.Vector;

import static com.jumper.main.Main.HEIGHT;

public class Field {
    private GameStateManager gsm;
    private Player player;
    private OnlinePlayer onlinePlayer;

    private EndOfGameState end;
    private SingleGameState game;

    private Clouds clouds;

    private Vector<SubField> subFields;

    private BitmapFont printScore;
    private int score;

    public static final float Y_VELOSITY = 2000, X_VELOSITY = 400, A = 5000; // 2000 400 5000

    public Field(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public Field(GameStateManager gsm, SingleGameState game) {
        this.gsm = gsm;
        this.game = game;

        subFields = new Vector<SubField>();
        subFields.add(new SubField(80, -10, 70, 300));

        subFields.add(new SubField(subFields.lastElement().getX() + subFields.lastElement().getWidth() + getRandom(), -10, 70, getNewSub()));
        subFields.add(new SubField(subFields.lastElement().getX() + subFields.lastElement().getWidth() + getRandom(), -10, 70, getNewSub()));

        subFields.get(0).used();

        player = new Player(subFields.get(0).getX() + subFields.get(0).getWidth() / 2 - 25, subFields.get(0).getY() + subFields.get(0).getHeight(), 50, 50);

        end = new EndOfGameState(gsm, game);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("tahoma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 25;
        parameter.color = Color.BLACK;
        printScore = generator.generateFont(parameter);
        generator.dispose();

        clouds = new Clouds(300, HEIGHT - 100, 210, 120, 200);
    }

    public void reSet() {
        subFields.get(0).reSet(80, -10, 300);
        subFields.get(1).reSet(subFields.get(0).getX() + subFields.get(0).getWidth() + getRandom(), -10, getNewSub());
        subFields.get(2).reSet(subFields.get(1).getX() + subFields.get(1).getWidth() + getRandom(), -10, getNewSub());

        player.setPosition(subFields.get(0).getX() + subFields.get(0).getWidth() / 2 - 25, subFields.get(0).getY() + subFields.get(0).getHeight());

        subFields.get(0).used();
        score = 0;
    }

    public float getNewSub() {
        return (float)(Math.random() * 200 + 250);
    }
    public float getRandom() {
        return  (float)(Math.random() * 100) + 100;
    }

    public void update(float dt) {
        player.update(dt);
        if(!player.isActive()) clouds.update(dt, X_VELOSITY); else clouds.update(dt, 0);
        if(!player.isActive()) {
            for (SubField sf : subFields) sf.update(dt);
            if(subFields.get(0).getX() + subFields.get(0).getWidth() < 0) {
                SubField sf = subFields.get(0);
                subFields.remove(0);
                sf.reSet(subFields.lastElement().getX() + subFields.lastElement().getWidth() + getRandom(), -10, getNewSub());
                subFields.add(sf);
            }
        }
        check();
    }

    public void check() {
        for(SubField sf : subFields) {
            Rectangle s = sf.getSubField(), p = player.getPlayer();
            if(p.y <= 200) {
                end.setScore(score);
                gsm.push(end);
                return;
            }

            if(s.overlaps(p)) {
                if(p.x + p.getWidth() / 2 >= s.x && p.x + p.getWidth() / 2 <= s.x + s.width) {
                    player.contact();
                    player.setPosition(p.x, s.y + s.height);
                    if(!sf.isUsed()) ++score;
                    sf.used();
                } else {
                    end.setScore(score);
                    gsm.push(end);
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
        clouds.render(sb);

        for (SubField sf : subFields) sf.render(sb);

        printScore.draw(sb, "Score: " + score, 15, HEIGHT - 15);

        player.render(sb);
    }

    public void dispose() {
        player.dispose();
        end.dispose();
        clouds.dispose();
    }
}
