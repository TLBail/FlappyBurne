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
import com.tlbail.FlappyBurne.Model.Skin;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.State.SkinState;
import com.tlbail.FlappyBurne.State.TutoState;
import com.tlbail.FlappyBurne.User.LocalDataLoader.UserPropertyFileLoader;
import com.tlbail.FlappyBurne.User.User;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;

public class StartController {

    private GameView gameView;
    private TextView title;
    private ImageButton startImageButton;
    private ImageButton scoreImageButton;
    private ImageButton cupImageButton;
    private ConstraintLayout subMenu;
    private AppCompatActivity appCompatActivity;
    private AchivementManager achivementManager;
    private ImageView imageViewLEMANS;
    private ImageButton burnImagebutton;
    private Skin skin;
    /**
     * controller du début setup les button et les image
     * @param gameView
     */
    public StartController(GameView gameView) {
        this.gameView = gameView;
        this.appCompatActivity = gameView.getAppCompatActivity();
        final SoundManager soundManager = (SoundManager) gameView.getGameComponentByClass(SoundManager.class);
        achivementManager = AchivementManager.getInstance();
        final User user = new User("bob", new UserPropertyFileLoader(appCompatActivity));
        skin = Skin.valueOf(user.get(Skin.SKINKEY));

        createUI();

        bindUI();
        title.setText(R.string.app_name);

        burnImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.playMenuSound();
                subMenu.removeAllViews();
                gameView.switchState(new SkinState(gameView));
            }
        });

        startImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundManager.playMenuSound();
                subMenu.removeAllViews();
                gameView.switchState(new TutoState(gameView));
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


        cupImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                achivementManager.showAchievements();
            }
        });

        achivementManager.signInSilently();

    }


    private void createUI() {
        subMenu = appCompatActivity.findViewById(R.id.subMenu);
        LayoutInflater inflator = (LayoutInflater) appCompatActivity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.start_layout, subMenu);


    }

    /**
     * fait le lien entre le fichier xml et start controller pour récupérer les objet
     * et charge les images
     */
    private void bindUI(){
        title = gameView.getAppCompatActivity().findViewById(R.id.scoreTextView);
        startImageButton = appCompatActivity.findViewById(R.id.startImageButton);
        scoreImageButton = appCompatActivity.findViewById(R.id.scoreImageButton);
        cupImageButton = appCompatActivity.findViewById(R.id.CupimageButton);
        cupImageButton.setImageResource(R.drawable.cup);
        imageViewLEMANS = appCompatActivity.findViewById(R.id.imageViewLEMANS);
        imageViewLEMANS.setImageResource(R.drawable.logo_iut);
        burnImagebutton = appCompatActivity.findViewById(R.id.BigBurneBigImageButton);
        burnImagebutton.setImageResource(skin.getHeadDrawable());

    }



}
