package com.yazeen.game.Build;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.yazeen.game.Screens.playScreen;
import com.yazeen.game.Sprites.Coin;
import com.yazeen.game.Sprites.Green;
import com.yazeen.game.Sprites.Obstacle;
import com.yazeen.game.Sprites.Spiky;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-06.
 */

public class WorldCreator {
    //två arrayer för två olika fiende
    private Array<Spiky> spikys;
    private Array<Green> greens;


    public WorldCreator(playScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //importerar ground och sätter den till box som man kan kollidera med
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / TheLastHope.GPPM, (rect.getY() + rect.getHeight() / 2) / TheLastHope.GPPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / TheLastHope.GPPM, rect.getHeight() / 2 / TheLastHope.GPPM);

            fdef.shape = shape;
            body.createFixture(fdef);
            fdef.filter.categoryBits = TheLastHope.DEFAULT_BIT;
        }

        //importerar obstacles och sätter dem till box som man kan kollidera med
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Obstacle(screen, rect);
        }

        //importerar coins och sätter dem till box som man kan kollidera med
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(screen, rect);
        }

        //skapar alla spikys-fiende
        spikys = new Array<Spiky>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            spikys.add(new Spiky(screen,rect.getX()/TheLastHope.GPPM,rect.getY()/TheLastHope.GPPM));
        }

        //skapar alla greens-fiende
        greens = new Array<Green>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            greens.add(new Green(screen,rect.getX()/TheLastHope.GPPM,rect.getY()/TheLastHope.GPPM));
        }

    }
    public Array<Spiky> getSpikys() {
        return spikys;
    }
    public Array<Green> getGreens()
    {
        return greens;
    }
}
