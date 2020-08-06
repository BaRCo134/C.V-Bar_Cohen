package com.example.catchthefrog.game;

import android.content.Intent;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catchthefrog.MainActivity;
import com.example.catchthefrog.R;
import com.example.catchthefrog.databinding.TheGameActivityBinding;
import com.example.catchthefrog.scores.ScoresActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class TheGameActivity extends AppCompatActivity {
    private GameExecutor gameExecutor;
    private TheGameActivityBinding theGameActivityBinding;
    private ArrayList<GameObjectView> gameObjectViews = new ArrayList<>();
    static MediaPlayer frogSound;
    static MediaPlayer bombSound;
    private long timeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theGameActivityBinding = TheGameActivityBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        setContentView(theGameActivityBinding.getRoot());
        theGameActivityBinding.getRoot().post(this::startGame);
        frogSound = MediaPlayer.create(TheGameActivity.this, R.raw.frog_sound2);
        bombSound = MediaPlayer.create(TheGameActivity.this, R.raw.bomb_sound2);
        startGame();
    }
    @Override
    public void onBackPressed() {
        if(timeBack + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(this, "Click back twice to exit", Toast.LENGTH_SHORT).show();
        }
        timeBack = System.currentTimeMillis();
    }

    private void startGame() {
        float x = theGameActivityBinding.getRoot().getX();
        float y = theGameActivityBinding.getRoot().getY();
        int width = theGameActivityBinding.getRoot().getWidth();
        int height = theGameActivityBinding.getRoot().getHeight();
        RectF screenRect = new RectF(x, y, x + width, y + height);
        this.gameExecutor = new GameExecutor(screenRect, getResources());
        new GameTask().execute();
    }

    private class GameTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void [] objects) {

            gameExecutor.onTick();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            char [] life = new char[gameExecutor.getLife()];
            Arrays.fill(life, '❤');
            super.onPostExecute(aVoid);
            theGameActivityBinding.scoreTxtV.setText(getString(R.string.game_score_format, gameExecutor.getScoreCount()));
            theGameActivityBinding.lifeTxtV.setText(getString(R.string.game_life_format, String.copyValueOf(life)));
            if(gameExecutor.isNewLevel()) {
                theGameActivityBinding.levelsTxtV.setText("LEVEL " + gameExecutor.getLevel());
                theGameActivityBinding.levelsTxtV.setVisibility(View.VISIBLE);
            } else{
                theGameActivityBinding.levelsTxtV.setVisibility(View.INVISIBLE);
            }
            ArrayList newGameObjectViews = new ArrayList();

            for (GameObjectView view: gameObjectViews){

                if(gameExecutor.getGameObjects().contains(view.getGameObject()))  {
                    newGameObjectViews.add(view);
                }
                else{
                    ((ViewGroup)theGameActivityBinding.getRoot()).removeView(view);
                }
            }
            gameObjectViews = newGameObjectViews;

            if(gameExecutor.getGameObjects().size() > gameObjectViews.size()){
                for(int i = gameObjectViews.size(); i < gameExecutor.getGameObjects().size(); i++){
                    GameObjectView view = new GameObjectView(TheGameActivity.this);
                    view.setGameObject(gameExecutor.getGameObjects().get(i));
                    ((ViewGroup)theGameActivityBinding.getRoot()).addView(view);
                    gameObjectViews.add(view);
                }
            }
            for (GameObjectView gameObjectView : gameObjectViews) {
                gameObjectView.onUpdate();
            }

            if (gameExecutor.isGameOver()) {
                GameOverDialog gameOverDialog = new GameOverDialog(TheGameActivity.this, new GameDialogListener());
                gameOverDialog.show();
            }
            else
                new Handler().postDelayed(()->new GameTask().execute(), 100);
        }
    }

    public static void frogClicked(){
        frogSound.start();
        if(!frogSound.isPlaying())
            frogSound.release();
    }

    public static void bombClicked(){
        bombSound.start();
        if(!bombSound.isPlaying())
            bombSound.release();
    }

    private class GameDialogListener implements GameOverDialog.Listener {
        @Override
        public void onHomeClick() {
            finish();
        }

        @Override
        public void onScoresClick(String name) {
            Intent scoresIntent = new Intent(TheGameActivity.this, ScoresActivity.class);
            scoresIntent.putExtra(ScoresActivity.EXTRA_NAME, name);
            scoresIntent.putExtra(ScoresActivity.EXTRA_SCORE, gameExecutor.getScoreCount());
            startActivity(scoresIntent);
            finish();
        }
    }
}
