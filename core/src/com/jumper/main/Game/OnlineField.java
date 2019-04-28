package com.jumper.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jumper.main.GameStateManager;
import com.jumper.main.Main;
import com.jumper.main.States.EndOfGameState;
import com.jumper.main.States.OnlineGameState;
import com.jumper.main.States.SingleGameState;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import static com.jumper.main.Main.HEIGHT;
import static com.jumper.main.Game.Field.*;


public class OnlineField {

    private DatagramSocket client;

    private GameStateManager gsm;
    private OnlinePlayer player, opponent;

    private EndOfGameState end;
    private OnlineGameState game;

    private Clouds clouds;

    private Vector<SubField> subFields;

    private BitmapFont printScore;
    private int score;

    private boolean isGame = false;

    private int playersCount, fieldsCount;

    public OnlineField(GameStateManager gsm, DatagramSocket client, OnlineGameState game) {
        this.gsm = gsm;
        this.client = client;
        this.game = game;

        toSend("CREATE");

        subFields = new Vector<SubField>();

        end = new EndOfGameState(gsm, game);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("tahoma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 25;
        parameter.color = Color.BLACK;
        printScore = generator.generateFont(parameter);
        generator.dispose();

        clouds = new Clouds(300, HEIGHT - 100, 210, 120, 200);
    }


    private void requestHandler(String request) throws NullPointerException{
        System.out.print(request);
        String[] s = request.split(" ");

        if(s[0] == "CREATING") {
            playersCount = Integer.parseInt(s[1]);
            fieldsCount = Integer.parseInt(s[2]);
        } else if(s[0] == "FIELD") {
            subFields.add(new SubField(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4])));
        } else if(s[0] == "YOU") {
            if(player == null) player = new OnlinePlayer(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
            else player.setPosition(Float.parseFloat(s[1]), Float.parseFloat(s[2]));
            score = Integer.parseInt(s[5]);

        } else if(s[0] == "PLAYER") {
            if(opponent == null) opponent = new OnlinePlayer(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]));
            else opponent.setPosition(Float.parseFloat(s[1]), Float.parseFloat(s[2]));
        } else if(s[0] == "START") {
            isGame = true;
        } else if(s[0] == "FINISH") {
            end.setScore(score);
            gsm.push(end);
        }
    }

    public void toSend(String s) {
        try {
            byte b[] = s.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName(Main.address), Main.port);
            client.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(float dt) {
        clouds.update(dt, 0);
        if(isGame) {
            player.update();
            if(player.isPressed()) toSend("TRUE"); else toSend("FALSE");
            toSend("GET");
        }

        try {
            byte b[] = new byte[100000];
            DatagramPacket dp = new DatagramPacket(b, b.length);
            client.receive(dp);

            int last;
            for(last = 0; last < b.length && b[last] != 0; ++last);

            requestHandler(new String(b, 0, last));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e ) {
            e.printStackTrace();
        }

        if(isGame) {
            opponent.setPosition(opponent.getX() - player.getX() + 50, opponent.getY());
            for(SubField sf : subFields) sf.setPosition(sf.getX() - player.getX() + 50, sf.getY());
        }
    }

    public void render(SpriteBatch sb) {
        clouds.render(sb);

        if(isGame) {
            for (SubField sf : subFields) sf.render(sb);

            printScore.draw(sb, "Score: " + score, 15, HEIGHT - 15);

            player.render(sb);
        }
    }

    public void dispose() {
        player.dispose();
        clouds.dispose();
        opponent.dispose();
        for(SubField sf : subFields) sf.dispose();
    }

}
