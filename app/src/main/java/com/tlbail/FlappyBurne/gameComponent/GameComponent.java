package com.tlbail.FlappyBurne.gameComponent;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.FlappyBurne.Activity.GameView;

public abstract class GameComponent {

    private GameView gameView;


    /**
     * Game component patern
     * @param gameView
     */

    public GameComponent(GameView gameView){
        this.gameView = gameView;
        gameView.subscribeGameComponent(this);
    }


    public GameView getGameView() {
        return gameView;
    }

    public AppCompatActivity getAppCompatActivity(){
        return gameView.getAppCompatActivity();
    }

    /**
     * méthode start appelé just avant la première frame
     */
    public  void start(){

    }

    public void stop(){

    }

    /**
     * méthode update appelé a chaque frame
     */
    public  void update(){

    }

    /**
     * méthode appelé a chaque changement d'écran
     * @param x
     * @param y
     */
    public  void onScreenChange(int screenWidth, int screenHeight){

    }

    public void resume(){

    }
}
