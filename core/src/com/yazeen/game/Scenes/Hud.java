package com.yazeen.game.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Yzn on 2017-03-04.
 */

public class Hud implements Disposable {

    //viewTools
    public Stage myStage;
    private Viewport viewport;
    private OrthographicCamera hudCam =new OrthographicCamera();

    //Time&Score
    private Integer worldTimer;
    private static Integer score;

    private float timeCount;

    //Labels
    Label coutDownLabel;
    static Label  scoreLabel;
    static Label player_HP;
    Label timeLabel;
    Label scoreStr;
    Label player_HP_str;


    public Hud(SpriteBatch mySB ,int hp)
    {
        //initiering
        worldTimer = 0;
        score= 0;
        viewport = new ScreenViewport(hudCam);
        myStage = new Stage(viewport,mySB);
        Table hudTable = new Table();

        //Hämtar bitmapFont i assets
        BitmapFont myFont = new BitmapFont((Gdx.files.internal("font.fnt")));

        //table-style
        hudTable.top(); //add it to the top
        hudTable.setFillParent(true); //Make table as the size of myStage

        coutDownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(myFont, Color.RED));
        scoreLabel = new Label(String.format("%02d",score), new Label.LabelStyle(myFont, Color.RED));
        player_HP = new Label(String.format("%02d",hp), new Label.LabelStyle(myFont, Color.GREEN));
        timeLabel = new Label("Time:", new Label.LabelStyle(myFont, Color.WHITE));
        scoreStr = new Label("Coins:", new Label.LabelStyle(myFont, Color.WHITE));
        player_HP_str = new Label("Health:", new Label.LabelStyle(myFont, Color.WHITE));

        //Placerar labels i stage
        hudTable.left().padLeft(10);
        hudTable.setScale(100,100);
        hudTable.add(timeLabel);
        hudTable.add(coutDownLabel).padLeft(10).width(100);
        hudTable.add();
        hudTable.add(scoreStr);
        hudTable.add(scoreLabel).padLeft(10).width(100);
        hudTable.add();
        hudTable.add(player_HP_str);
        hudTable.add(player_HP).padLeft(10).width(100);
        myStage.addActor(hudTable);

    }

    public void update(float dt)
    {
        timeCount +=dt;
        if(timeCount >= 1)
        {
            worldTimer++;
            coutDownLabel.setText(String.format("%03d", worldTimer));
            timeCount= 0;
        }
    }
    public static void addScore(int value)
    {
        score += value;
        scoreLabel.setText(String.format("%02d",score));
    }
    public int getScore()
    {
        return score;
    }
    public static void updateHealth(int hp)
    {
        player_HP.setText(String.format("%02d",hp));
    }

    @Override
    public void dispose() {
        myStage.dispose();
    }

}
