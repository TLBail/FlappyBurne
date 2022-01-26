package com.tlbail.FlappyBurne.State;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface State {

    public void createComponent();

    public boolean onTouchEvent(MotionEvent event);

    public void drawCanvas(Canvas canvas);


    public void start();
}
