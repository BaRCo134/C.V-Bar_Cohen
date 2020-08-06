package com.example.catchthefrog.scores;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.catchthefrog.R;
import com.example.catchthefrog.databinding.ScoresBinding;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "ExtraName";
    public static final String EXTRA_SCORE = "ExtraScore";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScoresBinding binding = ScoresBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle(R.string.scores_title);

        final String name = getIntent().getStringExtra(EXTRA_NAME);
        final int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        ScoreItem scoreItem = new ScoreItem(name, score);

	    // 1 - Read old scores
	    String json = readFileOnInternalStorage(this, "scores");
        ArrayList<ScoreItem> scoresItems = new ArrayList<>();
        if(scoreItem != null)
            scoresItems.add(scoreItem);
        Gson gson = new Gson();
	    if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<ArrayList<ScoreItem>>(){}.getType();
            scoresItems = gson.fromJson(json, type);
	    }

	    // 2- Add new score
        if (name != null && !name.isEmpty()) {
            scoresItems.add(scoreItem);

            json = gson.toJson(scoresItems);
            writeFileOnInternalStorage(this, "scores", json);
        }	

        binding.scoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.scoresRecyclerView.setAdapter(new ScroesAdapter(scoresItems));
    }

    public void onHomeClicked(View view){   finish();}

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody) {
        File file = new File(mcoContext.getFilesDir(), "scoresDir");
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String readFileOnInternalStorage(Context mcoContext, String sFileName) {
        File file = new File(mcoContext.getFilesDir(), "scoresDir");
        if (!file.exists()) {
            return null;
        }

        String  body = null;
        try {
            File gpxfile = new File(file, sFileName);
            BufferedReader reader = new BufferedReader(new FileReader(gpxfile));
            body = reader.readLine();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return body;
    }
}
