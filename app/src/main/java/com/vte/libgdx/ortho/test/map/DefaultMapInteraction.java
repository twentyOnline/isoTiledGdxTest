package com.vte.libgdx.ortho.test.map;

/**
 * Created by vincent on 05/01/2017.
 */

public class DefaultMapInteraction implements IMapInteraction {
    protected IMapInteraction.Type mType;
    protected float mX, mY;

    public DefaultMapInteraction(float aX, float aY, Type aType) {
        mX = aX;
        mY = aY;
        mType = aType;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public IMapInteraction.Type getInteractionType() {
        return mType;
    }

}