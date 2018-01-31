package com.yazeen.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-06.
 */

public class Controller {

    private Viewport viewport;
    private Stage stage;
    boolean leftPressed;
    boolean rightPressed;
    boolean jumpPressed;
    boolean attackPressed;
    private OrthographicCamera controllerCam;

    private static final int buttonSize = 60;

    public Controller(TheLastHope game)
    {
        //initiering
        this.controllerCam = new OrthographicCamera();
        this.viewport = new FitViewport(800,480, controllerCam);
        stage = new Stage(viewport, game.myBatch);
        Table moveTable = new Table();
        Image leftImg = new Image(new Texture("left.png"));
        Image rightImg = new Image(new Texture("right.png"));
        Image jumpImg = new Image(new Texture("jump.png"));
        Image attackImg = new Image(new Texture("attack.png"));

        //receive input events
        Gdx.input.setInputProcessor(stage);


        moveTable.left().bottom().padBottom(10);

        //set buttons size
        leftImg.setSize(buttonSize,buttonSize);
        rightImg.setSize(buttonSize,buttonSize);
        jumpImg.setSize(buttonSize,buttonSize);
        attackImg.setSize(buttonSize,buttonSize);

        //Listener
        leftImg.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        rightImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        jumpImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = false;
            }
        });

        attackImg.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = false;
            }
        });


        //Placerar imgs i stage
        moveTable.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        moveTable.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padLeft(50);
        moveTable.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight()).padLeft(450);
        moveTable.add(jumpImg).size(jumpImg.getWidth(), jumpImg.getHeight()).padLeft(50);
        stage.addActor(moveTable);



    }

    public void draw()
    {
        stage.draw();
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isJumpPressed() {
        return jumpPressed;
    }

    public boolean isAttackPressed() {
        return attackPressed;
    }

    public void resize(int width, int height)
    {
        viewport.update(width,height);
    }

}
