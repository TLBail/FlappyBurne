package com.tlbail.FlappyBurne.Model;

import com.tlbail.FlappyBurne.R;

public enum Skin {

    CLASSIC(new int[]{R.drawable.fbs01, R.drawable.fbs02, R.drawable.fbs03}, R.drawable.fbs51),
    GOLD(new int[]{R.drawable.gfbs01,R.drawable.gfbs02,R.drawable.gfbs03}, R.drawable.gfbs51),
    BLUE(new int[]{R.drawable.bfbs01,R.drawable.bfbs02,R.drawable.bfbs03}, R.drawable.bfbs51),
    RED(new int[]{R.drawable.rfbs01,R.drawable.rfbs02,R.drawable.rfbs03}, 0),
    CYAN(new int[]{R.drawable.cfbs01,R.drawable.cfbs02,R.drawable.cfbs03}, R.drawable.cfbs51);

    public static final String SKINKEY = "skin";

    private int[] framesDrawables;
    private int headDrawable;

    private Skin(int[] values, int head){
        this.framesDrawables = values;
        this.headDrawable = head;
    }

    public int[] getFramesDrawables() {
        return framesDrawables;
    }

    public int getHeadDrawable() {
        return headDrawable;
    }
}
