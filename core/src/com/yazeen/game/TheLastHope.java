package com.yazeen.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yazeen.game.Screens.mainMenu;

public class TheLastHope extends Game {

	//Game-Properties
	public static final int G_WIDTH = 400;
	public static final int G_HEIGHT = 208;
	public static final float GPPM = 100; //Pixel per meter-unitScale
	public static final float G_GRAVITY= -10;
	public static final int PLAYER_SPEED = 1;

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short OBSTACLE_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short BULLET_BIT = 64;




	//mainMenu-Object
	mainMenu menu;

	//Game-title
	public static final String TITLE = "The Last Hope";
	public static AssetManager audioManager;

	//SpriteBatch
	public SpriteBatch myBatch;

	@Override
	public void create () {
		myBatch = new SpriteBatch();

		//Hämtar all ljud från assets mappen
		audioManager = new AssetManager();
		audioManager.load("audio/bkms.ogg", Music.class);
		audioManager.load("audio/coin.wav", Sound.class);
		audioManager.load("audio/hurt.wav", Sound.class);
		audioManager.load("audio/run.wav", Sound.class);
		audioManager.load("audio/bullet.wav", Sound.class);
		audioManager.finishLoading();



		menu = new mainMenu(this);
		setScreen(menu);
	}

	@Override
	public void render () {
		super.render();

		//forsätt ladda assets
		audioManager.update();
	}
	
	@Override
	public void dispose () {
		myBatch.dispose();
	}
}
