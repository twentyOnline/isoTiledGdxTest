package com.vte.libgdx.ortho.test.box2d;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.vte.libgdx.ortho.test.MyGame;
import com.vte.libgdx.ortho.test.entity.EntityEngine;
import com.vte.libgdx.ortho.test.entity.ICollisionHandler;
import com.vte.libgdx.ortho.test.entity.components.CollisionComponent;

/**
 * Created by vincent on 07/07/2016.
 */

public class MapBodyManager implements ICollisionHandler {


    static private MapBodyManager s_instance;

    public static MapBodyManager getInstance() {
        return s_instance;
    }

    public static synchronized MapBodyManager createInstance(Map map) {
        s_instance = new MapBodyManager(map);
        return s_instance;
    }

    private Array<Polygon> mBodiesZindex = new Array<Polygon>();
    private Array<Polygon> mBodiesCollision = new Array<Polygon>();

    private Array<CollisionComponent> mCollisions = new Array<CollisionComponent>();


    public MapBodyManager(Map map) {
        mBodiesZindex = buildShapes(map, "zindex");
        mBodiesCollision = buildShapes(map, "collision");

    }

    public Array<Polygon> getBodiesZindex() {
        return mBodiesZindex;
    }

    public Array<Polygon> getBodiesCollision() {
        return mBodiesCollision;
    }

    public Array<Polygon> buildShapes(Map map, String layerName) {
        MapObjects objects = map.getLayers().get(layerName).getObjects();

        Array<Polygon> bodies = new Array<Polygon>();

        for (MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Polygon polygon = null;

            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                polygon = new Polygon(new float[]{0, 0, rect.getWidth() * MyGame.SCALE_FACTOR, 0, rect.getWidth() * MyGame.SCALE_FACTOR, rect.getHeight() * MyGame.SCALE_FACTOR, 0, rect.getHeight() * MyGame.SCALE_FACTOR});
                polygon.setPosition(rect.x * MyGame.SCALE_FACTOR, rect.y * MyGame.SCALE_FACTOR);
            } else if (object instanceof PolygonMapObject) {
                float[] mapVertices = ((PolygonMapObject) object).getPolygon().getTransformedVertices();
                float[] vertices = new float[mapVertices.length - 2];
                for (int i = 0; i < vertices.length; ++i) {
                    vertices[i] = mapVertices[i] * MyGame.SCALE_FACTOR;
                }
                polygon = new Polygon(vertices);
            }/* else if (object instanceof PolylineMapObject) {
                shape = ShapeFactory.getPolyline((PolylineMapObject) object);
            } else if (object instanceof CircleMapObject) {
                shape = ShapeFactory.getCircle((CircleMapObject) object);
            }*/ else {
                continue;
            }

            if (polygon != null) {
                bodies.add(polygon);
                Entity entity = new Entity();
                entity.add(new CollisionComponent(polygon, this));
                EntityEngine.getInstance().addEntity(entity);
            }

        }
        return bodies;
    }

    @Override
    public void onCollisionStart(CollisionComponent aEntity) {

    }

    @Override
    public void onCollisionStop(CollisionComponent aEntity) {

    }

    @Override
    public Array<CollisionComponent> getCollisions() {
        return mCollisions;
    }
}