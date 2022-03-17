package com.tlbail.FlappyBurne.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdManager {

    private static final String adId = "ca-app-pub-8453388567575948/8101669575";
    private static final String adTestId = "ca-app-pub-3940256099942544/1033173712";

    private static final String TAG = "AdManager";
    private InterstitialAd mInterstitialAd;
    private AppCompatActivity appCompatActivity;


    /**
     * permet de charger / afficher des pub
     * @param appCompatActivity
     */

    public AdManager(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        load();
    }

    
    //Charge la pub
    private void load(){

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(appCompatActivity, adId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        setFullScreen();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Log.e(TAG, error);
                        mInterstitialAd = null;
                    }


                });
    }

    // permet de mettre la pub full Screen
    private void setFullScreen(){
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
            }
        });
        showAd();

    }

    // une fois la pub charger permet
    private void showAd(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(appCompatActivity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

}
