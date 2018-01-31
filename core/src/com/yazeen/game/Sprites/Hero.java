package com.yazeen.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
 * Created by Yzn on 2017-03-06.
 */

public class Hero extends Sprite {
    //hp
    private int player_Health;

    //box2d objekt
    private World world;
    private Body b2dbody;

    //Animationer
    private  TextureRegion heroIdle;
    private Animation heroRun;
    private Animation heroJump;
    private float stateTimer;
    private enum State { FALLING, JUMPING, STANDING, RUNNING };
    private State currentState;
    private State previousState;

    //kollar till höger
    private boolean facingRight;

    //Död
    private boolean isDead;

    private Array<Bullet> Bullets;
    private playScreen screen;

    public Hero(playScreen screen)
    {
        //setting world
        this.screen = screen;
        this.world = screen.getWorld();
        player_Health = 100;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        facingRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i= 1; i<6;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("newTre1"), i*84,0,85,128 ));
        heroRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i= 2; i<4;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("newTre1"), i*84,0,85,128 ));
        heroJump = new Animation(0.1f, frames);
        frames.clear();

        //creating the hero
        createHero();

        //Setting sprite
        heroIdle = new TextureRegion(screen.getAtlas().findRegion("newTre1"), 0, 0, 85, 128);
        setBounds(0, 0, 32 / TheLastHope.GPPM, 32 / TheLastHope.GPPM);
        Bullets = new Array<Bullet>();

        //setRegion(heroIdle);
    }
    public void update(float dt)
    {
        setPosition(b2dbody.getPosition().x - getWidth() / 2, b2dbody.getPosition().y - getHeight() / 3);
        setRegion(getFrame(dt));

        for(Bullet  ball : Bullets) {
            ball.update(dt);
            if(ball.isDestroyed())
                Bullets.removeValue(ball, true);
        }
    }



    public TextureRegion getFrame(float dt)
    {
        currentState = getState();
        TextureRegion region;

        switch(currentState){
            case JUMPING:
                region = (TextureRegion) heroJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) heroRun.getKeyFrame(stateTimer,true);
                break;
            default:
                region = heroIdle;
                break;
        }

        //om spelaren går till vänster, ändra sprite
        if((b2dbody.getLinearVelocity().x < 0 || !facingRight) && !region.isFlipX()){
            region.flip(true, false);
            facingRight = false;
        }

        //om spelaren går till höger, ändra sprite
        else if((b2dbody.getLinearVelocity().x > 0 || facingRight) && region.isFlipX()){
            region.flip(true, false);
            facingRight = true;
        }

        //om current state är lika med previous state, öka stateTimer
        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        //updatera previous
        previousState = currentState;

        //returar den slutliga framen
        return region;
    }

    public State getState()
    {
        //
        if((b2dbody.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2dbody.getLinearVelocity().y < 0 && previousState == State.JUMPING) )
            return State.JUMPING;
        else if (b2dbody.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2dbody.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2dbody.applyLinearImpulse(new Vector2(0, 3f), b2dbody.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void createHero()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(5, 32  / TheLastHope.GPPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2dbody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7/TheLastHope.GPPM);

        //categoryBits till spelare
        fdef.filter.categoryBits = TheLastHope.PLAYER_BIT;

        //vad spelaren kan kolidera med
        fdef.filter.maskBits = TheLastHope.DEFAULT_BIT | TheLastHope.COIN_BIT | TheLastHope.OBSTACLE_BIT | TheLastHope.ENEMY_BIT;

        fdef.shape= shape;
        b2dbody.createFixture(fdef).setUserData(this);
    }
    public void takeDamage(int hp)
    {
        TheLastHope.audioManager.get("audio/hurt.wav", Sound.class).play();
        player_Health -= hp;
        if (player_Health <=0 ) {
            isDead = true;
        }
    }

    public void fire(){
        TheLastHope.audioManager.get("audio/bullet.wav", Sound.class).play();
        Bullets.add(new Bullet(screen, b2dbody.getPosition().x, b2dbody.getPosition().y, facingRight ? true : false));
    }

    public int getPlayer_Health()
    {
        return player_Health;
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void draw(Batch myBatch)
    {
        super.draw(myBatch);
        for(Bullet ball : Bullets)
            ball.draw(myBatch);
    }
    public Body getB2dbody()
    {
        return this.b2dbody;
    }

}
