package com.example.catchthefrog.game;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.catchthefrog.databinding.GameOverDialogBinding;

public class GameOverDialog extends Dialog {

    public interface Listener{
        void onHomeClick();
        void onScoresClick(String name);
    }

    private Listener listener;

    public GameOverDialog(@NonNull Context context, Listener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameOverDialogBinding binding = GameOverDialogBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.btnHome.setOnClickListener(v -> listener.onHomeClick());

        binding.btnScores.setOnClickListener(v -> listener.onScoresClick(binding.nameTxtEd.getText().toString()));
    }

}
