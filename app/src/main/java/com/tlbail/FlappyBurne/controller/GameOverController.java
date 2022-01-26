package com.tlbail.FlappyBurne.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tlbail.FlappyBurne.Model.AchivementManager;
import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.Activity.ScoresActivity;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.State.StartState;
import com.tlbail.FlappyBurne.gameComponent.ScoreManager;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;

public class GameOverController {

    private GameView gameView;
    private ImageButton startImageButton;
    private ImageButton scoreImageButton;
    private ConstraintLayout subMenu;
    private AppCompatActivity appCompatActivity;
    private SoundManager soundManager;

    private TextView bestScoreTextView;
    private TextView finalScoreTextView;
    private TextView scoreTextView;
    private ScoreManager scoreManager;
    private ImageView medalImageView;
    private ImageView gameOverImageView;
    private ImageView bacgroundScoreImageView;


    /**
     * gère l'interface de game over (lien vers les autre état / chargement de l'interface )
     * @param gameView
     */
    public GameOverController(GameView gameView) {
        this.gameView = gameView;
        this.appCompatActivity = gameView.getAppCompatActivity();
        soundManager = (SoundManager) gameView.getGameComponentByClass(SoundManager.class);
        scoreManager = (ScoreManager) gameView.getGameComponentByClass(ScoreManager.class);
        createUI();

        bindUI();

        finalScoreTextView.setText(String.valueOf(scoreManager.getScore()));
        bestScoreTextView.setText(String.valueOf(scoreManager.getBestScore()));

        AchivementManager achivementManager = AchivementManager.getInstance();
        achivementManager.unlockAchievementByScore(scoreManager.getScore());

        if(scoreManager.getScore() == scoreManager.getBestScore()){
            medalImageView.setImageResource(R.drawable.medalgold);
        }else if(scoreManager.getScore() > scoreManager.getBestScore() / 2){
            medalImageView.setImageResource(R.drawable.medalargent);
        }


        startImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundManager.playMenuSound();
                subMenu.removeAllViews();
                gameView.switchState(new StartState(gameView));
            }
        });


        scoreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundManager.playMenuSound();
                Intent intent = new Intent(gameView.getAppCompatActivity(), ScoresActivity.class);
                gameView.getAppCompatActivity().startActivity(intent);
            }
        });

    }

    private void createUI() {
        subMenu = appCompatActivity.findViewById(R.id.subMenu);
        LayoutInflater inflator = (LayoutInflater) appCompatActivity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.game_over_layout, subMenu);

    }


    private void bindUI(){
        startImageButton = appCompatActivity.findViewById(R.id.startImageButton2);
        scoreImageButton = appCompatActivity.findViewById(R.id.scoreImageButton2);
        finalScoreTextView = appCompatActivity.findViewById(R.id.finalscoreTextView);
        bestScoreTextView = appCompatActivity.findViewById(R.id.bestScoreTextView);
        scoreTextView = appCompatActivity.findViewById(R.id.scoreTextView);
        gameOverImageView = appCompatActivity.findViewById(R.id.gameOverImageView);
        gameOverImageView.setImageResource(R.drawable.gameover);
        medalImageView = appCompatActivity.findViewById(R.id.medalImageView);
        bacgroundScoreImageView = appCompatActivity.findViewById(R.id.backgroundScoreImageVIew);
        bacgroundScoreImageView.setImageResource(R.drawable.scorebacground);
    }


}
