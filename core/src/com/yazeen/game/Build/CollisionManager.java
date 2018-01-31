package com.yazeen.game.Build;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.yazeen.game.Sprites.Enemy;
import com.yazeen.game.Sprites.Hero;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-09.
 */

public class CollisionManager implements ContactListener {

    //Blir kallad när 2 box2d objekt kolliderar
    @Override
    public void beginContact(Contact contact) {

        //Två fixtures som kolliderar(vet inte om vilken som är vad)
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //Note: bit1+bit2 = cDef
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef)
        {
            //Om player kolliderar med coin
            case TheLastHope.PLAYER_BIT | TheLastHope.COIN_BIT:
                if (fixA.getFilterData().categoryBits == TheLastHope.PLAYER_BIT )
                    ((liveObject) fixB.getUserData()).onCollide();
                else
                    ((liveObject) fixA.getUserData()).onCollide();
                break;
            //Om player kolliderar med fiende
            case TheLastHope.PLAYER_BIT | TheLastHope.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == TheLastHope.PLAYER_BIT )
                    ((Hero) fixA.getUserData()).takeDamage(20);
                else
                    ((Hero) fixB.getUserData()).takeDamage(20);
                break;
            //Om fiende kolliderar med obstacle
            case TheLastHope.ENEMY_BIT | TheLastHope.OBSTACLE_BIT:
                if (fixA.getFilterData().categoryBits == TheLastHope.ENEMY_BIT )
                    ((Enemy) fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true,false);
                break;
            
            //Om skott kolliderar med fiende
            case TheLastHope.BULLET_BIT | TheLastHope.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == TheLastHope.ENEMY_BIT )
                    ((Enemy) fixA.getUserData()).hitByPlayer();
                else
                    ((Enemy) fixB.getUserData()).hitByPlayer();
                break;
        }

        /*
        //Gammalt sätt
        //om player i fixA sätt det lika med fixA
            Fixture player = "player".equals(fixA.getUserData()) ? fixA : fixB;
            //om player i fixA sätt object i fixB
            Fixture object = player == fixA ? fixB : fixA;

            //Om objektet är en sub-objekt till liveObject
       if(object.getUserData() instanceof liveObject)
        {
            //kalla funktionen onCollide.
        }*/
    }

    @Override
    //When 2 fixtures stop collideing
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
