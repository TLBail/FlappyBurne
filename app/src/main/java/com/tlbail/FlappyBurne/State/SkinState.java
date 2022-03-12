package com.tlbail.FlappyBurne.State;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.controller.SkinController;
import com.tlbail.FlappyBurne.controller.StartController;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.Graphics;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.GraphicsStartDecorator;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;

public class SkinState implements State {

    private GameView gameView;
    private GraphicsStartDecorator graphicsStartDecorator;
    private SoundManager soundManager;



    public SkinState(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void createComponent() {
        Graphics graphics = new Graphics(gameView);
        graphicsStartDecorator = new GraphicsStartDecorator(graphics); // on utilise le decorateur pour juste afficher le fond
        soundManager = new SoundManager(gameView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //ui is handled by other class
        return false;
    }

    @Override
    public void drawCanvas(Canvas canvas) {
        graphicsStartDecorator.drawCanvas(canvas);
    }

    @Override
    public void start() {
        setUI();
    }

    private void setUI() {
        SkinController skinkController = new SkinController(gameView);
    }
}
