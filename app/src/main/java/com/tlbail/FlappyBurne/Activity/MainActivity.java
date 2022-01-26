package com.tlbail.FlappyBurne.Activity;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tlbail.FlappyBurne.Model.AchivementManager;
import com.tlbail.FlappyBurne.Model.ScoreDataBase;
import com.tlbail.FlappyBurne.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity {

    private GameView mGameView;

    //Point d'entré de l'application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setUpGoogleAchivement();

        setupGameView();

        loadAds();

    }


    private void setUpGoogleAchivement() {
        AchivementManager.getInstance().setAppCompatActivity(this);
    }

    private void setupGameView(){
        //inflate game view
        FrameLayout mainLayout = new FrameLayout(this);
        mainLayout.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);

        mainLayout.addView(createGameView());
        mainLayout.addView(createWidgetView());


        setContentView(mainLayout);

        mGameView.start();

    }


    private void loadAds() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

    }



    private View createGameView(){
        mGameView = new GameView(this);
        return mGameView;
    }

    private View createWidgetView(){
        LayoutInflater inflator = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflator.inflate(R.layout.main_layout, null);
    }

    /**
     * Pauses game when activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

    /**
     * Resumes game when activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        AchivementManager achivementManager = AchivementManager.getInstance();
        achivementManager.onActivityResult(requestCode, resultCode, intent);

    }

}