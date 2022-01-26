package com.tlbail.FlappyBurne.Model;

import com.tlbail.FlappyBurne.gameComponent.TubeManager;

public class Tube {

    //private static final float SPEED = -8.5f;
    private static final double SPEED =  -6;


    private float x;
    private float y;
    private TubeManager tubeManager;
    private boolean isActive;
    private boolean scoreHasBeenCounted;

    /**
     * POJO Tube avec le déplacement tu tube a chaque frame
     * @param tubeManager
     * @param x
     * @param y
     * @param isReverse
     */

    public Tube(TubeManager tubeManager, int x, int y, boolean isReverse) {
        this.x = x;
        this.y = y;
        this.tubeManager = tubeManager;
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public void moveTube(){
        x += SPEED * tubeManager.getDeltaTime() ;
        if(tubeIsNotVisible()){
            tubeManager.desactiveThisTube(this);
        }
    }


    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    private boolean tubeIsNotVisible() {
        return getX() < -500;
    }

    public void reload(int x, int y) {
        setActive(true);
        this.x = x;
        this.y = y;
        scoreHasBeenCounted = false;
    }

    public void countTheScoreOfTheTube(){
        scoreHasBeenCounted = true;
    }

    public boolean isScoreHasBeenCounted() {
        return scoreHasBeenCounted;
    }
}
