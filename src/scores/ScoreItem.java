package com.example.catchthefrog.scores;

public class ScoreItem {
    private String name;
    private int score;

    public ScoreItem(){}
    public ScoreItem(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


}
