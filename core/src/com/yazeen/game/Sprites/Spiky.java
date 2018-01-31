package com.yazeen.game.Sprites;

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

public class Spiky extends  Enemy{

    private float stateTime;
    private Animation spikyAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDead;
    private boolean dead;


    public Spiky(playScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("spiky"), i * 120, 0, 120, 120));
        spikyAnimation = new Animation(0.4f, frames);
        setToDead = false;
        dead = false;
        stateTime = 0;
        setBounds(0, 0, 25 / TheLastHope.GPPM, 25 / TheLastHope.GPPM);
    }

    public void update (float dt)
    {
        stateTime += dt;
        if (setToDead && !dead) {
            world.destroyBody(b2dbody);
            dead = true;
            stateTime = 0;
        }
        else if (!dead)
        {
            setPosition(b2dbody.getPosition().x - getWidth() / 2, b2dbody.getPosition().y - getHeight() / 3);
            setRegion((TextureRegion) spikyAnimation.getKeyFrame(stateTime, true));
        }

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
        fdef.restitution = 0.5f;
        b2dbody.createFixture(fdef).setUserData(this);

    }

    public void hitByPlayer()
    {
        setToDead = true;
        Hud.addScore(50);
    }

    @Override
    public void draw (Batch myBatch)
    {
        if (!dead || stateTime <1)
            super.draw(myBatch);
    }

}
