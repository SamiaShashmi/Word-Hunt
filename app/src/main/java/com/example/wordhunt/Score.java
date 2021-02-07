package com.example.wordhunt;

/**
 * this is the base class for the score that we store
 *
 * @author Samia Islam, 180041237
 */
public class Score {
    private String name;
    private String score;

    public Score(){
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
