package com.tlbail.FlappyBurne.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.R;

public class TutoController {


    private GameView gameView;
    private ConstraintLayout subMenu;
    private AppCompatActivity appCompatActivity;


    /**
     * controller qui charge les image du tuto
     * @param gameView
     */
    public TutoController(GameView gameView) {
        this.gameView = gameView;
        this.appCompatActivity = gameView.getAppCompatActivity();
        createUI();
        bindUI();

    }

    private void createUI() {
        subMenu = appCompatActivity.findViewById(R.id.subMenu);
        LayoutInflater inflator = (LayoutInflater) appCompatActivity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.tutolayout, subMenu);

    }


    private void bindUI(){
        ImageView getReady = appCompatActivity.findViewById(R.id.getReadyImageView);
        getReady.setImageResource(R.drawable.getready);
        ImageView shadow = appCompatActivity.findViewById(R.id.shadowImageView);
        shadow.setImageResource(R.drawable.shadow);
        ImageView arrow = appCompatActivity.findViewById(R.id.arrowImageView);
        arrow.setImageResource(R.drawable.arrow);
        ImageView hand = appCompatActivity.findViewById(R.id.handImageView);
        hand.setImageResource(R.drawable.hand);
        ImageView tapLeft = appCompatActivity.findViewById(R.id.tapLeftImageView);
        tapLeft.setImageResource(R.drawable.tapleft);
        ImageView tapRight = appCompatActivity.findViewById(R.id.tapRightImageView);
        tapRight.setImageResource(R.drawable.tapright);


    }


}
