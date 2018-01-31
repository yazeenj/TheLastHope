package com.yazeen.game.Build;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.yazeen.game.Screens.playScreen;
import com.yazeen.game.TheLastHope;

/**
 *
 * Created by Yzn on 2017-03-09.
 *
 */

public abstract class liveObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle rect;
    protected Body body;
    protected Fixture liveFixture;

    public liveObject(playScreen screen, Rectangle rect)
    {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.rect = rect;

        //box2d
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / TheLastHope.GPPM, (rect.getY() + rect.getHeight() / 2) / TheLastHope.GPPM);
        body = world.createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2 / TheLastHope.GPPM, rect.getHeight() / 2 / TheLastHope.GPPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = TheLastHope.OBSTACLE_BIT;
        liveFixture = body.createFixture(fdef);

    }
    public abstract void onCollide();

    //Hämtar cellen från backgrund bilden i tiledmap så att den tar coin plats
    public TiledMapTileLayer.Cell getCell()
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * TheLastHope.GPPM /16),(int)(body.getPosition().y * TheLastHope.GPPM /16));
    }
    //hanterar bit-kollision
    public void setFilter(short Bit)
    {
        Filter filter = new Filter();
        filter.categoryBits = Bit;
        liveFixture.setFilterData(filter);
    }
}
