package com.example.catchthefrog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.catchthefrog.game.TheGameActivity;
import com.example.catchthefrog.scores.ScoresActivity;


public class MainActivity extends AppCompatActivity {

    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            music = MediaPlayer.create(MainActivity.this, R.raw.dont_worry_music);
            music.setLooping(true);
            music.start();
    }

    public void onPlayClicked(View view) {
        Intent playIntent= new Intent(this, TheGameActivity.class);
        startActivity(playIntent);
    }

    public void onScoresClicked(View view){
        Intent scoresIntent = new Intent(this, ScoresActivity.class);
        startActivity(scoresIntent);
    }

    public void onMusicClicked(View view){
        if(music.isPlaying()){
            music.pause();
            Toast.makeText(this, R.string.music_off, Toast.LENGTH_LONG).show();
        }
        else{
            music.start();
            Toast.makeText(this, R.string.music_on, Toast.LENGTH_LONG).show();
        }
    }

    public void onHowToPlayClicked(View view){
        ImageView imageView = findViewById(R.id.howToPlayImgV);
        Button button = findViewById(R.id.howToBtn);
        if(imageView.getVisibility() == View.INVISIBLE) {
            imageView.setVisibility(View.VISIBLE);
            button.setText(R.string.how_to_hide);
        }
        else {
            imageView.setVisibility(View.INVISIBLE);
            button.setText(R.string.how_to_play_btn);
        }


    }
}
