package com.yazeen.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

/**
 * Created by Yzn on 2017-03-06.
 */

public class mainMenu implements Screen {

    private Viewport viewport;
    private OrthographicCamera menuCam;
    private Stage mainMenuStage;
    private Texture backGround;
    private TheLastHope game;
    private playScreen play;

    Label gameNameLab;
    Label playGameLab;

    public mainMenu(TheLastHope game) {

        //initiering
        this.game = game;
        this.play = new playScreen(game);
        this.menuCam= new OrthographicCamera();
        viewport = new FitViewport(800, 400, menuCam);
        mainMenuStage = new Stage(viewport, game.myBatch);
        this.backGround = new Texture("bk.jpg");
        Table menuTable = new Table();

        //Hämtar bitmapFont i assets
        BitmapFont myFont = new BitmapFont((Gdx.files.internal("font.fnt")));

        menuTable.setFillParent(true);

        gameNameLab = new Label("TheLastHope", new Label.LabelStyle(myFont, Color.WHITE));
        playGameLab = new Label("Play", new Label.LabelStyle(myFont, Color.WHITE));

        //Placerar labels i stage
        menuTable.add(gameNameLab);
        menuTable.row();
        menuTable.add(playGameLab);
        mainMenuStage.addActor(menuTable);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            game.setScreen(play);
            dispose();
        }
        //screenClear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //rita
        game.myBatch.begin();
        game.myBatch.draw(backGround,0,0);
        game.myBatch.end();
        mainMenuStage.draw();
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
        mainMenuStage.dispose();
    }

}