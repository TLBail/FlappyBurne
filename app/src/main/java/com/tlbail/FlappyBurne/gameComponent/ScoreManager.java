package com.tlbail.FlappyBurne.gameComponent;

import android.content.Context;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.Model.Tube;
import com.tlbail.FlappyBurne.User.LocalDataLoader.UserPropertyFileLoader;
import com.tlbail.FlappyBurne.User.User;
import com.tlbail.FlappyBurne.User.UserPropertyLoader;

public class ScoreManager extends GameComponent {

    private int score;
    private TubeManager tubeManager;
    private Player player;
    private boolean isStopped;
    private int bestScore;
    private User user;
    private Context context;
    private static final String SCORE_KEY = "score";

    /**
     * class qui calcule le score en fonction de la position du joueur
     * stocke le meilleur score dans le stockage local
     * @param gameView
     */

    public ScoreManager(GameView gameView) {
        this(gameView, 0);
        context = gameView.getAppCompatActivity().getApplicationContext();

        //création d'un User qui permet de stocker des donné en local
        UserPropertyLoader userPropertyFileLoader = new UserPropertyFileLoader(context);
        user = new User("bob",userPropertyFileLoader);

        initBestScoreFromLocalStorage();

    }

    private void initBestScoreFromLocalStorage() {
        if(noScoreSaved()){
            bestScore = 0;
            user.setProperty(SCORE_KEY, String.valueOf(0));
        }else{
            //recupération du meilleur score du stockage local
            bestScore = Integer.valueOf(user.get(SCORE_KEY));
        }
    }

    private boolean noScoreSaved(){
        return user.get(SCORE_KEY) == null;
    }

    public ScoreManager(GameView gameView, int score){
        super(gameView);
        this.score = score;
        isStopped = false;

    }

    public int getScore() {
        return score;
    }

    @Override
    public void start() {
        tubeManager = (TubeManager) getGameView().getGameComponentByClass(TubeManager.class);
        player = (Player) getGameView().getGameComponentByClass(Player.class);
    }

    /**
     * a chaque frame on parcoure chaque tube dont on n'a pas compté le score
     * et si on a dépassé le tube on appelle addToScore
     */
    @Override
    public void update() {
        if (isStopped || tubeManager == null) return;
        for(Tube tube : tubeManager.getTubes()){
            if(!tube.isScoreHasBeenCounted() && tube.getX() < player.getX()) addToScore(tube);
        }
    }

    @Override
    public void stop() {
        isStopped = true;
    }


    /**
     * ajoute 1 au score
     * fait un son
     * update le meilleur score
     * @param tube
     */
    public void addToScore(Tube tube){
        score++;
        tube.countTheScoreOfTheTube();
        SoundManager soundManager = (SoundManager) getGameView().getGameComponentByClass(SoundManager.class);
        soundManager.onScoreAdded();
        if(score > bestScore){
            bestScore = score;
            user.setProperty(SCORE_KEY, String.valueOf(score));
        }
    }

    public int getBestScore() {
        context = getGameView().getAppCompatActivity().getApplicationContext();

        UserPropertyLoader userPropertyFileLoader = new UserPropertyFileLoader(context);
        user = new User("bob",userPropertyFileLoader);

        return Integer.valueOf(user.get(SCORE_KEY));
    }





}
