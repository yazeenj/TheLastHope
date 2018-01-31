package com.yazeen.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.yazeen.game.Screens.playScreen;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-21.
 */

public class Bullet extends Sprite {
    playScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    Body b2dbody;

    public Bullet(playScreen screen, float x, float y, boolean fireRight)
    {
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();

        //Hämtar 2 bilder från region bullet från den packade filen
        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("bullet"), i * 75, 0, 75, 125));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        setBounds(x, y, 15 / TheLastHope.GPPM, 15 / TheLastHope.GPPM);
        defineBullet();
    }
    //skapar och definerar box2d shape
    public void defineBullet()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /TheLastHope.GPPM : getX() - 12 /TheLastHope.GPPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dbody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / TheLastHope.GPPM);
        fdef.filter.categoryBits = TheLastHope.BULLET_BIT;
        fdef.filter.maskBits = TheLastHope.DEFAULT_BIT | TheLastHope.ENEMY_BIT | TheLastHope.OBSTACLE_BIT;
        fdef.shape = shape;

        fdef.friction = 0;
        b2dbody.setGravityScale(1);
        b2dbody.createFixture(fdef).setUserData(this);
        b2dbody.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 1f));
    }
    public void update(float dt){
        stateTime += dt;
        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2dbody.getPosition().x - getWidth() / 5 , b2dbody.getPosition().y - getHeight() / 5);

        //förstör skottet efter 1 sekund
        if((stateTime > 1 || setToDestroy) && !destroyed) {
            world.destroyBody(b2dbody);
            destroyed = true;
        }
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
