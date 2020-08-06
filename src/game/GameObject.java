package com.example.catchthefrog.game;

import android.graphics.PointF;

public class GameObject {
    private PointF position;
    private PointF vector;
    private int lifeTime;
    private int level;
    private float size;
    private boolean isClicked;

    public GameObject(ObjectType type) {
        this.type = type;
    }

    private ObjectType type;

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setClick() {
        isClicked = true;
    }

    public boolean isClicked() {
        return isClicked;
    }

    enum ObjectType {Frog, Bomb};

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public PointF getVector() {
        return vector;
    }

    public void setVector(PointF vector) {
        this.vector = vector;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }
}
