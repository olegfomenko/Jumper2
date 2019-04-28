package com.jumper.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jumper.main.States.HelloPage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends ApplicationAdapter {
	private SpriteBatch sb;
	private GameStateManager gsm;
	public static float HEIGHT = 720, WIDTH = 400;
	public static String address = "31.131.26.46";
	public static int port = 5000;

	public static String storage = "", directory = "";
	
	@Override
	public void create () {
		storage = Gdx.files.getExternalStoragePath();
		directory = "Jumper";

		System.out.println("DIR: " + storage);
		File dir = new File(storage + directory);
		System.out.println("CHECK: " + dir.canWrite());
		if(!dir.exists()) System.out.println("DIR CREATED: " + dir.mkdir());

		File score = new File(storage + directory + "/score.txt");

		if(!score.exists()) try {
			System.out.println("Creating new file!!");
			score.createNewFile();
			FileHandle fh = new FileHandle(score);
			fh.writeString("0", false);
		} catch (IOException e) {
			System.out.println("Not created");
			e.printStackTrace();
		}

		sb = new SpriteBatch();
		gsm = new GameStateManager();

		HEIGHT = (float)Gdx.graphics.getHeight();
		WIDTH = (float)Gdx.graphics.getWidth();

		float k = HEIGHT / 850;

		HEIGHT /= k;
		WIDTH /= k;

		HelloPage hp = new HelloPage(gsm);
		gsm.push(hp);
	}

	@Override
	public void render () {
		gsm.peek().update(Gdx.graphics.getDeltaTime());
		gsm.peek().render(sb);
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		while(gsm.size() > 0) {
			gsm.peek().dispose();
			gsm.pop();
		}
	}
}
