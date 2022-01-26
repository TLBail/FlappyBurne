package com.tlbail.FlappyBurne.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.tlbail.FlappyBurne.R;

public class AchivementManager implements OnCompleteListener<GoogleSignInAccount> {


    private static AchivementManager instance = new AchivementManager();

    public static AchivementManager getInstance() {
        return instance;
    }

    private static final int RC_SIGN_IN = 9001;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_UNUSED = 5001;
    private static final String TAG = "AchievementManager";


    private AchievementsClient mAchievementsClient;
    private AppCompatActivity appCompatActivity;
    private GoogleSignInClient mGoogleSignInClient;


    /**
     * class sur le patern Singleton pour débloquer les achievement partout dans le code
     */

    private AchivementManager() {
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        mGoogleSignInClient = GoogleSignIn.getClient(appCompatActivity,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());

    }

    public void signInSilently() {
        mGoogleSignInClient.silentSignIn().addOnCompleteListener(appCompatActivity,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInSilently(): success");
                            onConnected(task.getResult());
                        } else {
                            Log.d(TAG, "signInSilently(): failure", task.getException());
                            onDisconnected();
                        }
                    }
                });
    }

    @Override
    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
        if (task.isSuccessful()) {
            // The signed in account is stored in the task's result.
            GoogleSignInAccount signedInAccount = task.getResult();
            if(signedInAccount != null && signedInAccount.getAccount() != null)
                Log.i("log", signedInAccount.getAccount().name);
        } else {
            startSignInIntent();
            if(task.getException().toString() != null)
                Log.e("AchievementManager", task.getException().toString());
        }

    }


    private void startSignInIntent() {
        appCompatActivity.startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }


    public void unlockAchievementByScore(int score){
        if(mAchievementsClient == null) return;
        mAchievementsClient.unlock(appCompatActivity.getString(R.string.achievement_gg_noobs));
        if(score < 0)
            mAchievementsClient.unlock(appCompatActivity.getString(R.string.achievement_beater));
        if(score < 10) return;
        mAchievementsClient.unlock(appCompatActivity.getString(R.string.achievement_noobie));
        if(score < 20) return;
        mAchievementsClient.unlock(appCompatActivity.getString(R.string.achievement_casual_gamer));
        if(score < 50) return;
        mAchievementsClient.unlock(appCompatActivity.getString(R.string.achievement_rookie));
        if(score < 100) return;
        mAchievementsClient.unlock(appCompatActivity.getString(R.string.achievement_pro_gamer));

    }



    public void showAchievements() {
        System.out.println("salute");
        Toast.makeText(appCompatActivity, "ouverture des succès...", Toast.LENGTH_LONG).show();
        if(mAchievementsClient == null) return;
        mAchievementsClient.getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        appCompatActivity.startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(appCompatActivity, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * permet de recuperer les info d'auth une fois l'utilisateur connecté
     *
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                onConnected(account);
            } catch (ApiException apiException) {
                String message = apiException.getMessage();
                if (message == null || message.isEmpty()) {
                    message = "error de type error voir error pour résoudre error dans le cas de error error merci de contacte error";
                }

                onDisconnected();

                new AlertDialog.Builder(appCompatActivity)
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
            }
        }
    }


    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "onConnected(): connected to Google APIs");

        mAchievementsClient = Games.getAchievementsClient(appCompatActivity, googleSignInAccount);
    }

    private void onDisconnected() {
        Log.d(TAG, "onDisconnected()");

        mAchievementsClient = null;
    }

}