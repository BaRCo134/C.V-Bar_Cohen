package com.example.catchthefrog.game;

import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.catchthefrog.R;

public class GameObjectFactory {
    private RectF screenRect;
    private Resources resources;

    public GameObjectFactory(RectF screenRect, Resources resources) {
        this.screenRect = screenRect;
        this.resources = resources;
    }

    public GameObject createFrog(int level) {

        float size = resources.getDimensionPixelSize(R.dimen.frogSize);

        double x = Math.random() * (screenRect.width() - size) + screenRect.left;
        double y = Math.random() * (screenRect.height() - size) + screenRect.top;//note that the image size is not calculated
        GameObject frog= new GameObject(GameObject.ObjectType.Frog);

        PointF position = new PointF((float)x,(float)y);
        frog.setPosition(position);
        frog.setLifeTime(50);
        frog.setLevel(level);
        frog.setSize(size);

        return frog;
    }

    public GameObject createBomb(int level) {

        float size = resources.getDimensionPixelSize(R.dimen.bombSize);

        double x = Math.random() * (screenRect.width() - size) + screenRect.left;
        double y = Math.random() * (screenRect.height() - size) + screenRect.top;//note that the image size is not calculated
        GameObject bomb = new GameObject(GameObject.ObjectType.Bomb);

        PointF position = new PointF((float)x,(float)y);
        bomb.setPosition(position);
        bomb.setLifeTime(25);
        bomb.setLevel(level);
        bomb.setSize(size);

        return bomb;
    }
}
