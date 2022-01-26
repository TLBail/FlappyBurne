package com.tlbail.FlappyBurne.State;

import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.controller.TutoController;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.Graphics;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.GraphicsStartDecorator;
import com.tlbail.FlappyBurne.gameComponent.ScoreManager;
import com.tlbail.FlappyBurne.gameComponent.SoundManager;

public class TutoState implements State{



    private GameView gameView;
    private GraphicsStartDecorator graphicsStartDecorator;
    private SoundManager soundManager;
    private ScoreManager scoreManager;

    /**
     * Etat du patron d'Etat$
     * affiche le tuto
     * @param gameView
     */
    public TutoState(GameView gameView) {
        this.gameView = gameView;
    }


    @Override
    public void createComponent() {
        Graphics graphics = new Graphics(gameView);
        graphicsStartDecorator = new GraphicsStartDecorator(graphics);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //"ui is handled by other class"
        ConstraintLayout subMenu = gameView.getAppCompatActivity().findViewById(R.id.subMenu);
        subMenu.removeAllViews();
        gameView.switchState(new GamingState(gameView));


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
        TutoController tutoController = new TutoController(gameView);

    }


}
