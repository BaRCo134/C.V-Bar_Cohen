package com.example.catchthefrog.scores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catchthefrog.databinding.ScoreItemLayoutBinding;

import java.util.ArrayList;

class ScroesAdapter extends RecyclerView.Adapter<ScroesAdapter.ScoreViewHolder> {
    private ArrayList<ScoreItem> scoresItems;

    public ArrayList<ScoreItem> getScoresItems() {
        return scoresItems;
    }

    public void setScoresItems(ScoreItem scoresItem) {
        this.scoresItems.add(scoresItem);
    }


    public ScroesAdapter(ArrayList<ScoreItem> scoresItems) {

        this.scoresItems = scoresItems;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScoreItemLayoutBinding binding = ScoreItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        ScoreViewHolder viewHolder = new ScoreViewHolder(binding.getRoot(), binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreItem scoreItem = scoresItems.get(position);
        holder.binding.scoreItemName.setText(scoreItem.getName());
        holder.binding.scoreItemScore.setText("" + scoreItem.getScore());
    }

    @Override
    public int getItemCount() {
        return scoresItems.size();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {

        private ScoreItemLayoutBinding binding;

        public ScoreViewHolder(@NonNull View itemView, ScoreItemLayoutBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
