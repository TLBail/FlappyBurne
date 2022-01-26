package com.tlbail.FlappyBurne.Model;

public class Score {

    private int score;
    private String pseudo;
    private String id;

    /**
     * POJO score
     */
    public Score(){
        this("", -1);
    }

    public Score(String pseudo, int score) {
        this.score = score;
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getScore() {
        return score;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
