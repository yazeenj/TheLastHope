package com.yazeen.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.yazeen.game.Scenes.Hud;
import com.yazeen.game.Screens.playScreen;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-21.
 */

public class Green extends  Enemy {
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDead;
    private boolean dead;

    public Green(playScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("green"), i * 100, 0, 100, 120));
        walkAnimation = new Animation(0.4f, frames);
        setToDead = false;
        dead = false;
        stateTime = 0;
        setBounds(0, 0, 32 / TheLastHope.GPPM, 32 / TheLastHope.GPPM); //sptire-storlek
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dbody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7/TheLastHope.GPPM);

        //categoryBits till spelare
        fdef.filter.categoryBits = TheLastHope.ENEMY_BIT;

        //vad spelaren kan kolidera med
        fdef.filter.maskBits = TheLastHope.DEFAULT_BIT | TheLastHope.OBSTACLE_BIT | TheLastHope.PLAYER_BIT | TheLastHope.BULLET_BIT;

        fdef.shape= shape;
        b2dbody.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt)
    {
        stateTime += dt;
        if (setToDead && !dead) {
            world.destroyBody(b2dbody);
            dead = true;
            stateTime = 0;
        }
        else if (!dead)
        {
            b2dbody.setLinearVelocity(velocity);
            setPosition(b2dbody.getPosition().x - getWidth() / 2, b2dbody.getPosition().y - getHeight() / 3);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
    }
    public void hitByPlayer()
    {
        setToDead = true;
        Hud.addScore(50);
    }

    public void draw (Batch myBatch)
    {
        if (!dead || stateTime <1)
            super.draw(myBatch);
    }


}
