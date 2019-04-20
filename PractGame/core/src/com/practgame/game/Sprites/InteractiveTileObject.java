package com.practgame.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.PractGame;
import com.practgame.game.Screens.PlayScreen;

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;


    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds, boolean isSensor){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody; // static bodies require less computing power
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / PractGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / PractGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / PractGame.PPM, bounds.getHeight() / 2 / PractGame.PPM);
        fdef.shape = shape;
        fdef.isSensor = isSensor; // if true, object is "transparent"
       fixture =  body.createFixture(fdef);
    }
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

}
