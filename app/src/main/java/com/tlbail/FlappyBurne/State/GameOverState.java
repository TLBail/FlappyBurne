package com.tlbail.FlappyBurne.State;

import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.Activity.SignInActivity;
import com.tlbail.FlappyBurne.Model.AdManager;
import com.tlbail.FlappyBurne.Model.ScoreDataBase;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.Graphics;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.GraphicsStartDecorator;
import com.tlbail.FlappyBurne.controller.GameOverController;
import com.tlbail.FlappyBurne.gameComponent.ScoreManager;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameOverState implements State{


    private static final String TAG = "GameOverState";
    private GameView gameView;
    private GraphicsStartDecorator graphicsStartDecorator;
    private SoundManager soundManager;
    private ScoreManager scoreManager;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String pseudo;


    /**
     * Etat du patron d'état
     * quand il y a game over
     * @param gameView
     */
    public GameOverState(GameView gameView) {
        this.gameView = gameView;
    }


    @Override
    public void createComponent() {
        Graphics graphics = new Graphics(gameView);
        graphicsStartDecorator = new GraphicsStartDecorator(graphics);
        soundManager = new SoundManager(gameView);
        scoreManager = new ScoreManager(gameView, getScoreFromTextView());

        //si le score est le meilleur score on l'enregistre
        if(scoreManager.getScore() == scoreManager.getBestScore()){
            setupfireBaseAuth();
            if(firebaseUser != null){
                ScoreDataBase scoreDataBase = new ScoreDataBase(pseudo);
                scoreDataBase.AddScore(scoreManager.getScore());
            }else{
                Toast.makeText(gameView.getAppCompatActivity(), "score non ajouter à firebase connecte toi pour l'ajouter !", Toast.LENGTH_LONG).show();
            }
        }


        displayAd();
    }

    private void displayAd() {
        int random = (int) (Math.random() * scoreManager.getScore());
        if(random <= 1){
            AdManager adManager = new AdManager(gameView.getAppCompatActivity());
        }
    }


    private void setupfireBaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            pseudo = firebaseUser.getDisplayName();

        }
    }

    private int getScoreFromTextView(){
        TextView textView = gameView.getAppCompatActivity().findViewById(R.id.scoreTextView);
        return Integer.valueOf(textView.getText().toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //"ui is handled by other class"
        return true;
    }

    @Override
    public void drawCanvas(Canvas canvas) {
        graphicsStartDecorator.drawCanvas(canvas);
    }

    @Override
    public void start() {
        setUI();

    }

    private void setUI() {
        GameOverController gameOverController = new GameOverController(gameView);
    }


}
