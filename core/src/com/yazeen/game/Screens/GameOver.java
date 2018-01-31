package com.yazeen.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yazeen.game.TheLastHope;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Yzn on 2017-03-09.
 */

public class GameOver implements Screen {

    private Viewport viewport;
    private OrthographicCamera gameOverCam;
    private Stage gameOverStage;
    private Texture gameOverBackGround;
    private TheLastHope game;
    private playScreen play;
    private Integer highScore;



    public GameOver(TheLastHope game)
    {
        this.game = game;
        this.play = new playScreen(game);
        this.gameOverCam = new OrthographicCamera();
        viewport = new FitViewport(800, 400, gameOverCam);
        gameOverStage = new Stage(viewport, game.myBatch);
        this.gameOverBackGround = new Texture("blood.png");
        readScore();
        Table menuTable = new Table();

        //Hämtar bitmapFont i assets
        BitmapFont myFont = new BitmapFont((Gdx.files.internal("font.fnt")));


        //table-style
        menuTable.center();
        menuTable.setFillParent(true);

        Label gameNameLab = new Label("GameOver", new Label.LabelStyle(myFont, Color.WHITE));
        Label Highscore = new Label("HighestScore: ", new Label.LabelStyle(myFont, Color.WHITE));
        Label highScoreLab = new Label(String.format("%02d",highScore), new Label.LabelStyle(myFont, Color.GREEN));

        menuTable.add(gameNameLab).expandX().padTop(10f);
        menuTable.row();
        menuTable.add(Highscore);
        menuTable.row();
        menuTable.add(highScoreLab);
        gameOverStage.addActor(menuTable);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            game.setScreen(new playScreen((TheLastHope) game));
            dispose();
        }
        //screenClear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //rita
        game.myBatch.begin();
        game.myBatch.draw(gameOverBackGround,0,0);
        game.myBatch.end();
        gameOverStage.draw();
    }

    public void readScore() {

      /*  try {
            Scanner in = new Scanner(new File("C:/Users/Yzn/Desktop/highscore.txt"));
             this.highScore = in.nextInt();
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        FileHandle file = Gdx.files.internal("highscore.txt");
        this.highScore = Integer.parseInt(file.readString());
        Gdx.app.log("Read from File","");
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameOverStage.dispose();
    }
}
