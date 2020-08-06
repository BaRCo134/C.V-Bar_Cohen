package com.example.catchthefrog.game;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;

import com.example.catchthefrog.MainActivity;
import com.example.catchthefrog.R;
import com.example.catchthefrog.databinding.TheGameActivityBinding;
import com.example.catchthefrog.databinding.TheGameActivityBindingImpl;

import java.util.ArrayList;

public class GameExecutor {
    private ArrayList<GameObject> gameObjects= new ArrayList<>();
    private int level = 0;
    private int time = 0;
    private RectF screenRect;
    private GameObjectFactory factory;
    private int scoreCount = 0;
    private int life = 3;
    private boolean newLevel = false;

    public GameExecutor(RectF screenRect, Resources resources) {
        this.screenRect = screenRect;
        this.factory = new GameObjectFactory(screenRect, resources);
    }

    public void onTick (){
        ArrayList newGameObjects = new ArrayList();
        newLevel = false;
        if(time % 100 == 0) {
            level++;
            newLevel = true;
        } else if(time % 100 < 10){
            newLevel = true;
        }

        for (GameObject gameObject : gameObjects) {
            if((gameObject.getLifeTime()>0) && (!gameObject.isClicked())) {
                gameObject.setLifeTime(gameObject.getLifeTime() - 1);
                newGameObjects.add(gameObject);
            }
            else {
                if (gameObject.isClicked()) {
                    switch (gameObject.getType()) {
                        case Frog:
                            scoreCount++;
                            TheGameActivity.frogClicked();
                            break;
                        case Bomb:
                            life--;
                            TheGameActivity.bombClicked();
                            break;
                    }
                }
                else {
                    switch (gameObject.getType()){
                        case Frog:
                            life--;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        gameObjects = newGameObjects;
        time++;
        if (shouldCreateNewFrog()) {
            GameObject frog = factory.createFrog(level);
            gameObjects.add(frog);
        }
        if (shouldCreateNewBomb()) {
            GameObject bomb = factory.createBomb(level);
            gameObjects.add(bomb);
        }
    }
    private boolean shouldCreateNewFrog() {
        switch (level){
            case 1:
                return time % 50 == 0;
            case 2:
                return time % 25 == 0;
            case 3:
                return time % 24 == 0;
            case 4:
                return time % 22 == 0;
            case 5:
                return time % 20 == 0;
            default:
                return time % 15 == 0;
        }
    }
    private boolean shouldCreateNewBomb() {
        switch (level){
            case 1:
                return time % 100 == 0;
            case 2:
                return time % 50 == 0;
            case 3:
                return time % 25 == 0;
            case 4:
                return time % 20 == 0;
            case 5:
                return time % 10 == 0;
            default:
                return time % 5 == 0;
        }
    }
    public int getScoreCount() {
        return scoreCount;
    }
    public boolean isGameOver(){
        return life <= 0;
    }

    public int getLife() {
        return life;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public boolean isNewLevel() {
        return newLevel;
    }

    public int getLevel() {
        return level;
    }

}
