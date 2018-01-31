package com.yazeen.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.yazeen.game.Build.liveObject;
import com.yazeen.game.Scenes.Hud;
import com.yazeen.game.Screens.playScreen;
import com.yazeen.game.TheLastHope;


/**
 * Created by Yzn on 2017-03-06.
 */

public class Coin extends liveObject {
    public Coin(playScreen screen, Rectangle rect)
    {
        super(screen,rect);
        liveFixture.setUserData(this);
        setFilter(TheLastHope.COIN_BIT); //vad den har för bit
    }

    public void onCollide()
    {
        Gdx.app.log("Coin Collide detected","");
        Hud.addScore(10); //lägger till
        getCell().setTile(null);
        setFilter(TheLastHope.DESTROYED_BIT); //byta bit till DESTROYED_BIT
        TheLastHope.audioManager.get("audio/coin.wav", Sound.class).play();
    }
}
