package com.tlbail.FlappyBurne.State;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.TextView;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.Graphics;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.GraphicsStartDecorator;
import com.tlbail.FlappyBurne.controller.StartController;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;

public class StartState implements State{

    private GameView gameView;
    private GraphicsStartDecorator graphicsStartDecorator;
    private TextView textView;
    private SoundManager soundManager;


    /**
     * Etat du patron d'état
     * etat du début avec le button démarer
     * @param gameView
     */
    public StartState(GameView gameView) {
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
        return true;
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
        StartController startController = new StartController(gameView);

    }

}
