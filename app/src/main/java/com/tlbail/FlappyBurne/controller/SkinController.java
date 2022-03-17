package com.tlbail.FlappyBurne.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.Model.AdManager;
import com.tlbail.FlappyBurne.Model.Skin;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.State.StartState;
import com.tlbail.FlappyBurne.User.LocalDataLoader.UserPropertyFileLoader;
import com.tlbail.FlappyBurne.User.User;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;

public class SkinController {


    private GameView gameView;
    private ConstraintLayout subMenu;
    private AppCompatActivity appCompatActivity;

    private ImageView backgroundImage;
    private ImageView bigBurne;
    private ImageButton upperLeftBurne;
    private ImageButton upperRightBurne;
    private ImageButton bottomLeftBurne;
    private ImageButton bottomRightBurne;
    private ImageButton tacosBurneImageButton;
    private ImageButton redBurneImageButton;
    private ImageView okButton;
    private Skin skin;

    /**
     * controller du début setup les button et les image
     * @param gameView
     */
    public SkinController(GameView gameView) {
        this.gameView = gameView;
        this.appCompatActivity = gameView.getAppCompatActivity();
        final SoundManager soundManager = (SoundManager) gameView.getGameComponentByClass(SoundManager.class);
        final User user = new User("bob", new UserPropertyFileLoader(appCompatActivity));
        skin = Skin.valueOf(user.get(Skin.SKINKEY));
        createUI();
        bindUI();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdManager adManager = new AdManager(gameView.getAppCompatActivity());
                soundManager.playMenuSound();
                subMenu.removeAllViews();
                gameView.switchState(new StartState(gameView));
            }
        });

        upperLeftBurne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                bigBurne.setImageResource(R.drawable.fbs51);
                user.setProperty(Skin.SKINKEY, Skin.CLASSIC.name());
            }
        });
        upperRightBurne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                bigBurne.setImageResource(R.drawable.gfbs51);
                user.setProperty(Skin.SKINKEY, Skin.GOLD.name());

            }
        });

        bottomRightBurne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                bigBurne.setImageResource(R.drawable.cfbs51);
                user.setProperty(Skin.SKINKEY, Skin.CYAN.name());

            }
        });
        bottomLeftBurne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                bigBurne.setImageResource(R.drawable.bfbs51);
                user.setProperty(Skin.SKINKEY, Skin.BLUE.name());

            }
        });

        redBurneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                bigBurne.setImageResource(R.drawable.rfbs01);
                user.setProperty(Skin.SKINKEY, Skin.RED.name());
            }
        });

        tacosBurneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                bigBurne.setImageResource(R.drawable.tfbs51);
                user.setProperty(Skin.SKINKEY, Skin.TACOS.name());
            }
        });

    }


    private void createUI() {
        subMenu = appCompatActivity.findViewById(R.id.subMenu);
        LayoutInflater inflator = (LayoutInflater) appCompatActivity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.skinlayout, subMenu);
    }

    /**
     * fait le lien entre le fichier xml et start controller pour récupérer les objet
     * et charge les images
     */
    private void bindUI(){
        backgroundImage = appCompatActivity.findViewById(R.id.backgroundImage);
        backgroundImage.setImageResource(R.drawable.bigbackground);
        bigBurne = appCompatActivity.findViewById(R.id.mainBigBurne);
        bigBurne.setImageResource(skin.getHeadDrawable());
        upperLeftBurne = appCompatActivity.findViewById(R.id.UpperleftBurne);
        upperLeftBurne.setImageResource(R.drawable.fbs51);
        upperRightBurne = appCompatActivity.findViewById(R.id.upperRightburne);
        upperRightBurne.setImageResource(R.drawable.gfbs51);
        bottomLeftBurne = appCompatActivity.findViewById(R.id.bottomLeftBurne);
        bottomLeftBurne.setImageResource(R.drawable.bfbs51);
        bottomRightBurne = appCompatActivity.findViewById(R.id.bottomRightBurne);
        bottomRightBurne.setImageResource(R.drawable.cfbs51);
        okButton = appCompatActivity.findViewById(R.id.Okbutton);
        okButton.setImageResource(R.drawable.okbutton);
        redBurneImageButton = appCompatActivity.findViewById(R.id.redBurnImageButton);
        redBurneImageButton.setImageResource(R.drawable.rfbs01);
        tacosBurneImageButton = appCompatActivity.findViewById(R.id.tacosBurneImageButton);
        tacosBurneImageButton.setImageResource(R.drawable.tfbs51);
    }

}
