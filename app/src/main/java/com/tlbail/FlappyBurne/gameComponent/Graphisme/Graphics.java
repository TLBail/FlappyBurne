package com.tlbail.FlappyBurne.gameComponent.Graphisme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.widget.TextView;

import com.tlbail.FlappyBurne.Model.Score;
import com.tlbail.FlappyBurne.gameComponent.CapteurManager;
import com.tlbail.FlappyBurne.gameComponent.OnReadableLigth;
import com.tlbail.FlappyBurne.gameComponent.GameComponent;
import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.gameComponent.Player;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.gameComponent.ScoreManager;
import com.tlbail.FlappyBurne.Model.Tube;
import com.tlbail.FlappyBurne.gameComponent.TubeManager;

public class Graphics extends GameComponent {

    private static final int GROUNDSPEED =  -6 ;
    private static final int CITYSPEED = -1;
    private static final float SKYSPEED = -0.3f;
    private Bitmap tubeReverseBitmap;
    private Bitmap backgroundBitmap;
    private Bitmap backgroundDayBitmap;
    private Bitmap backgroundNightBitmap;
    private Bitmap cityBitmap;
    private Bitmap tubeBitmap;
    private Bitmap skyBitmap;
    private Bitmap groundBitmap;
    private Paint mPaint;
    private int screenWidth;
    private int screenHeight;
    private TextView scoreTextView;
    private boolean isStopped;
    private int xGround;
    private int xCity;
    private float xSky;

    private Bitmap goldenTubeBitmap;
    private Bitmap goldenTubeReverseBitmap;

    /**
     * class qui s'occupe de déssiner tout les image sur le canvas
     * @param gameView
     */

    public Graphics(GameView gameView){
        super(gameView);

        CapteurManager capteurManager = new CapteurManager(getGameView());
        capteurManager.addListener(new OnReadableLigth() {
            @Override
            public void onReadableLight(float lux) {
                if(lux > 20){
                    backgroundBitmap = backgroundDayBitmap;
                }else{
                    backgroundBitmap = backgroundNightBitmap;
                }
            }
        });
    }

    @Override
    public void start() {
        isStopped = false;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        scoreTextView = getAppCompatActivity().findViewById(R.id.scoreTextView);
        loadBitmap();



    }

    private void loadBitmap() {

        backgroundBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.background);
        backgroundDayBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.background);
        backgroundNightBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.backgroundnight);


        tubeBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.tube);
        tubeBitmap = Bitmap.createScaledBitmap(tubeBitmap, (int) (tubeBitmap.getWidth() * 1.2), (int)(tubeBitmap.getHeight() * 1.2), false);
        tubeReverseBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.tube2);
        tubeReverseBitmap = Bitmap.createScaledBitmap(tubeReverseBitmap, (int) (tubeReverseBitmap.getWidth() * 1.2), (int)(tubeReverseBitmap.getHeight() * 1.2), false);

        goldenTubeBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.goldentube);
        goldenTubeBitmap = Bitmap.createScaledBitmap(goldenTubeBitmap, (int) (goldenTubeBitmap.getWidth() * 1.2), (int)(goldenTubeBitmap.getHeight() * 1.2), false);
        goldenTubeReverseBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.goldentube2);
        goldenTubeReverseBitmap = Bitmap.createScaledBitmap(goldenTubeReverseBitmap, (int) (goldenTubeReverseBitmap.getWidth() * 1.2), (int)(goldenTubeReverseBitmap.getHeight() * 1.2), false);



        groundBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.ground);

        cityBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.city);

        skyBitmap = BitmapFactory.decodeResource(getAppCompatActivity().getResources(), R.drawable.sky);

    }



    private void drawTubes(Canvas canvas) {
        TubeManager tubeManager = (TubeManager) getGameView().getGameComponentByClass(TubeManager.class);
        for (Tube tube : tubeManager.getTubes()) {
                if(tube.isGolden()){
                    canvas.drawBitmap(goldenTubeBitmap, tube.getX(), tube.getY(), mPaint);
                    int posXTopTube = tube.getX();
                    int posYTopTube = tube.getY() - goldenTubeReverseBitmap.getHeight() - TubeManager.SPACE_BETWEEN_TUBES_Y;
                    canvas.drawBitmap(goldenTubeReverseBitmap, posXTopTube , posYTopTube, mPaint);
                }else{
                    canvas.drawBitmap(tubeBitmap, tube.getX(), tube.getY(), mPaint);
                    int posXTopTube = tube.getX();
                    int posYTopTube = tube.getY() - tubeReverseBitmap.getHeight() - TubeManager.SPACE_BETWEEN_TUBES_Y;
                    canvas.drawBitmap(tubeReverseBitmap, posXTopTube , posYTopTube, mPaint);
                }
        }

    }


    // lorsque que la taille de l'écran change on recscale tout les bitmap
    @Override
    public void onScreenChange(int screenWidth, int screenHeight) {
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, screenWidth, screenHeight, false);
        backgroundDayBitmap = Bitmap.createScaledBitmap(backgroundDayBitmap, screenWidth, screenHeight, false);
        backgroundNightBitmap = Bitmap.createScaledBitmap(backgroundNightBitmap, screenWidth, screenHeight, false);
        groundBitmap = Bitmap.createScaledBitmap(groundBitmap, screenWidth, groundBitmap.getHeight(), false);
        cityBitmap = Bitmap.createScaledBitmap(cityBitmap, screenWidth * 3, 300, false);
        skyBitmap = Bitmap.createScaledBitmap(skyBitmap, screenWidth, skyBitmap.getHeight(), false);
        tubeBitmap = Bitmap.createScaledBitmap(tubeBitmap, tubeBitmap.getWidth(), screenHeight, false);
        tubeReverseBitmap = Bitmap.createScaledBitmap(tubeReverseBitmap, tubeReverseBitmap.getWidth(), screenHeight,false);
        goldenTubeBitmap = Bitmap.createScaledBitmap(goldenTubeBitmap, tubeBitmap.getWidth(), screenHeight, false);
        goldenTubeReverseBitmap = Bitmap.createScaledBitmap(goldenTubeReverseBitmap, tubeReverseBitmap.getWidth(), screenHeight,false);

        mPaint.setTextSize(screenHeight / 10);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }


    public void drawCanvas(Canvas canvas) {
        if(canvas == null) return;

        drawBackground(canvas);
            drawTubes(canvas);
            drawGround(canvas);
            drawPlayer(canvas);
            drawScore(canvas);
    }

    @Override
    public void stop() {
        isStopped = true;

    }


    protected void drawBackground(Canvas canvas){
        canvas.drawBitmap(backgroundBitmap, 0, 0, mPaint);
        drawCity(canvas);
        drawSky(canvas);
        //canvas.drawBitmap(skyBitmap, 0, (float) (screenHeight * 0.65 - skyBitmap.getHeight()),mPaint);

    }

    //dessine la ville et la fait défilé si elle arrivé au bout on la remet tout a droite
    protected void drawSky(Canvas canvas){
        System.out.println("xSky = " + xSky);
        if(!isStopped) xSky += SKYSPEED * getGameView().getDeltaTime();
        if(xSky < -skyBitmap.getWidth()) xSky = 0;
        float skyY =  (float) (screenHeight * 0.65 - skyBitmap.getHeight());
        canvas.drawBitmap(skyBitmap, xSky,skyY, mPaint);
        canvas.drawBitmap(skyBitmap, xSky + skyBitmap.getWidth() - 5, skyY, mPaint);
    }


    //dessine la ville et la fait défilé si elle arrivé au bout on la remet tout a droite
    protected void drawCity(Canvas canvas){
        System.out.println("xCity = " + xCity);
        if(!isStopped) xCity += CITYSPEED * getGameView().getDeltaTime();
        if(xCity < -cityBitmap.getWidth()) xCity = 0;
        float cityY =  (float) (screenHeight * 0.65 - cityBitmap.getHeight());
        canvas.drawBitmap(cityBitmap, xCity,cityY, mPaint);
        canvas.drawBitmap(cityBitmap, xCity + cityBitmap.getWidth() - 5, cityY, mPaint);
    }


    // désinne le sole avec
    protected void drawGround(Canvas canvas){
        if(!isStopped) xGround += GROUNDSPEED * getGameView().getDeltaTime();
        if(xGround < -groundBitmap.getWidth()) xGround = 0;
        canvas.drawBitmap(groundBitmap, xGround, screenHeight , mPaint);
        canvas.drawBitmap(groundBitmap, xGround + groundBitmap.getWidth() - 5, screenHeight, mPaint);

    }

    private void drawScore(Canvas canvas) {
        ScoreManager scoreManager = (ScoreManager) getGameView().getGameComponentByClass(ScoreManager.class);
        getAppCompatActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText(String.valueOf(scoreManager.getScore()));
            }
        });
    }

    private synchronized void drawPlayer(Canvas canvas) {
        Player player = (Player) getGameView().getGameComponentByClass(Player.class);
        Matrix matrix = new Matrix();
        matrix.postTranslate(-player.getWidth()/2, -player.getHeight()/2);
        matrix.postRotate(player.getRotationAngle());
        matrix.postTranslate(player.getX(), player.getY());
        canvas.drawBitmap(player.getPlayerBitmap(), matrix, new Paint( Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG ));

    }


    public Bitmap getTubeBitmap() {
        return tubeBitmap;
    }
}
