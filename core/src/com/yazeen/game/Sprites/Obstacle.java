package com.yazeen.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.yazeen.game.Build.liveObject;
import com.yazeen.game.Screens.playScreen;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-09.
 */

public class Obstacle extends liveObject {
    public Obstacle(playScreen screen, Rectangle rect)
    {
        super(screen,rect);
        liveFixture.setUserData(this);
        setFilter(TheLastHope.OBSTACLE_BIT);
    }

    @Override
    public void onCollide() {
        Gdx.app.log("Obstacle Collide detected","");
        TheLastHope.audioManager.get("audio/run.wav", Sound.class).play();

    }
}
