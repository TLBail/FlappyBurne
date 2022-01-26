package com.tlbail.FlappyBurne.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.State.StartState;
import com.tlbail.FlappyBurne.State.State;
import com.tlbail.FlappyBurne.gameComponent.GameComponent;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {
    private boolean mRunning;
    private Thread mGameThread = null;
    private Path mPath;

    private Context mContext;

    private SurfaceHolder mSurfaceHolder;

    private List<GameComponent> gameComponentList;
    private AppCompatActivity appCompatActivity;

    private int width;
    private int height;

    private State state;
    private int groundHeight;

    private long lastTimeStamp;
    private double deltaTime;


    public GameView(AppCompatActivity appCompatActivity) {
        this(appCompatActivity, null);
    }


    public GameView(AppCompatActivity appCompatActivity, AttributeSet attrs) {
        super(appCompatActivity.getApplicationContext(), attrs);
        this.appCompatActivity = appCompatActivity;
        //init des variable
        mContext = appCompatActivity.getApplicationContext();
        mSurfaceHolder = getHolder();
        mPath = new Path();

        state = new StartState(this); // set l'état de base


        setGroundHeight();

        gameComponentList = new ArrayList<>();

        state.createComponent();
    }

    //on charge la ground height pour delimiter la taille de l'écran
    private void setGroundHeight() {
        Bitmap groundBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.ground);
        groundHeight = groundBitmap.getHeight();

    }

    // permet de changer d'état voir le patern patron d'état
    public void switchState(State state){
        pause();

        this.state = state;
        gameComponentList.clear();
        state.createComponent();
        start();
        for(GameComponent gameComponent: gameComponentList) gameComponent.onScreenChange(width, height);

        resume();

    }

    // appeler une fois que tu est charger
    public void start(){
        state.start();
        for(GameComponent gameComponent: gameComponentList) gameComponent.start();
    }


    /**
     * Appelé à a chaque changement de taille de l'écran
     * très utile car au début taille de l'écran vaut 0
     *
     * @param w Current width of view.
     * @param h Current height of view.
     * @param oldw Previous width of view.
     * @param oldh Previous height of view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h - groundHeight;

        for(GameComponent gameComponent: gameComponentList) gameComponent.onScreenChange(w, h);
    }


    /**
     * Called by MainActivity.onPause() to stop the thread.
     */
    public void pause() {
        mRunning = false;
        try {
            // Stop the thread == rejoin the main thread.
            mGameThread.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Called by MainActivity.onResume() to start a thread.
     */
    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
        for(GameComponent gameComponent: gameComponentList) gameComponent.resume();

    }

    // appelé a chaque onStop previent tout les GameComponent
    public void stop(){
        for(GameComponent gameComponent : gameComponentList) gameComponent.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return state.onTouchEvent(event);
    }


    /**
     * Run dans un thread séparer
     * update les valeur de tout les gameComponent
     * dessine tout les gameComponent
     *
     * calcule le temp depuis la dernière update pour avoir une vitesse de jeu pareil malgré
     * un changement de fps
     */
    public void run() {
        Canvas canvas;
        while (mRunning) {
            // If we can obtain a valid drawing surface...
            if (mSurfaceHolder.getSurface().isValid()) {
                lastTimeStamp = System.nanoTime();
                update();
                canvas = mSurfaceHolder.lockCanvas();
                canvas.save();


                state.drawCanvas(canvas);

                canvas.clipPath(mPath, Region.Op.DIFFERENCE);
                mPath.rewind();
                canvas.restore();
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                deltaTime = System.nanoTime() - lastTimeStamp;
            }
        }
        stop();
    }

    //donne le temps entre chaque frame pour avoir une vitesse des objets constant
    public double getDeltaTime() {
        return deltaTime * 0.0000001;
    }

    // a chaque frame
    private void update(){
        for(GameComponent gameComponent : gameComponentList) gameComponent.update();
    }

    public void subscribeGameComponent(GameComponent gameComponent){
        gameComponentList.add(gameComponent);
    }


    public void unsubscribeGameComponent(GameComponent gameComponent){
        gameComponentList.remove(gameComponent);
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }


    public GameComponent getGameComponentByClass(Class componentClass){
        for(GameComponent gameComponent : gameComponentList){
            if(gameComponent.getClass() ==  componentClass) return gameComponent;
        }
        return null;
    }
}
