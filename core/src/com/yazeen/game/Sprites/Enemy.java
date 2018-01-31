package com.yazeen.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.yazeen.game.Screens.playScreen;

/**
 * Created by Yzn on 2017-03-21.
 */

public abstract class Enemy extends Sprite {
    protected World world;
    protected playScreen screen;
    public Body b2dbody;
    public Vector2 velocity;
    public Enemy(playScreen screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(1,0);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitByPlayer();

    //När fiende koliderar med obstacle
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}
