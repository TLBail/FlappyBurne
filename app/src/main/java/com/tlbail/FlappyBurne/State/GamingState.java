package com.tlbail.FlappyBurne.State;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.tlbail.FlappyBurne.Model.GameLogic;
import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.Graphics;
import com.tlbail.FlappyBurne.gameComponent.Player;
import com.tlbail.FlappyBurne.gameComponent.ScoreManager;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;
import com.tlbail.FlappyBurne.gameComponent.TubeManager;

public class GamingState implements State{

    private GameView gameView;
    private Graphics graphics;
    private boolean isDead;
    private SoundManager soundManager;
    private boolean deathAnimationHaveBeenPlayed;
    private Player player;
    private GameLogic gameLogic;

    /**
     * Etat du patron d'état
     * etat en jeu
     * @param gameView
     */
    public GamingState(GameView gameView)
    {
        this.gameView = gameView;
        this.isDead = false;
        this.deathAnimationHaveBeenPlayed = false;
    }


    @Override
    public void createComponent() {
        player = new Player(gameView,100, 0);
        new TubeManager(gameView);
        graphics = new Graphics(gameView);
        gameLogic = new GameLogic(gameView);
        new ScoreManager(gameView);
        soundManager = new SoundManager(gameView);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isDead){
            gameView.switchState(new GameOverState(gameView));
        }else{
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                Player player = (Player) gameView.getGameComponentByClass(Player.class);
                if(player == null) return true;
                player.moveUp();
                soundManager.onPlayerMoveUpPlaySound();
            }
        }
        return true;

    }

    @Override
    public void drawCanvas(Canvas canvas) {
        if(graphics == null) return;
        if(isDead && !deathAnimationHaveBeenPlayed){
            playDeathAnimation(canvas);
            return;
        }
        graphics.drawCanvas(canvas);
        if(isDiing()) isDead = true;

    }

    private boolean isDiing(){
        return gameLogic.playerOverlappingTubes() || player.playerIsEatingTheFloor();
    }



    @Override
    public void start() {
        System.out.println("starting the gaming");
    }

    private void playDeathAnimation(Canvas canvas){
        deathAnimationHaveBeenPlayed = true;
        Paint paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint2);
        soundManager.deathSound();
        gameView.stop();

    }

}
