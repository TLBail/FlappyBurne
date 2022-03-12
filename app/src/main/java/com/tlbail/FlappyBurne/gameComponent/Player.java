package com.tlbail.FlappyBurne.gameComponent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.Model.Skin;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.User.LocalDataLoader.UserPropertyFileLoader;
import com.tlbail.FlappyBurne.User.User;

public class Player extends GameComponent {

    private float x;
    private float y;
    private int mViewHeight;
    private double yspeed = 0.6f;
    private float yVelocity;
    private Bitmap playerBitmapFr1;
    private Bitmap playerBitmapFr2;
    private Bitmap playerBitmapFr3;
    private int width;
    private int height;
    private Bitmap previousBitmap;
    private int timeFromLastBitmapChange;
    private float rotationAngle;
    private boolean isStop;
    private Skin skin;
    private static final float MOVEUPFORCE = 18;


    /**
     * class joueur qui contient tout les info sur le joueur s'occupe
     * du déplacement du joueur avec la gravité le saut la mort
     * @param gameView
     * @param x
     * @param y
     */

    public Player(GameView gameView, int x, int y) {
        super(gameView);
        this.x = x;
        this.y = y;
        yVelocity = 0;
        timeFromLastBitmapChange = 0;
        final User user = new User("bob", new UserPropertyFileLoader(gameView.getAppCompatActivity()));
        skin = Skin.valueOf(user.get(Skin.SKINKEY));

        loadBitmap();

        width = playerBitmapFr1.getWidth();
        height = playerBitmapFr1.getHeight();
        rotationAngle = 0;
        
        
    }

    private void loadBitmap() {
        playerBitmapFr1 = BitmapFactory.decodeResource( getAppCompatActivity().getResources(), skin.getFramesDrawables()[0]);
        playerBitmapFr1 = Bitmap.createScaledBitmap(playerBitmapFr1, 120, 120, false);

        playerBitmapFr2 = BitmapFactory.decodeResource( getAppCompatActivity().getResources(), skin.getFramesDrawables()[1]);
        playerBitmapFr2 = Bitmap.createScaledBitmap(playerBitmapFr2, 120, 120, false);

        playerBitmapFr3 = BitmapFactory.decodeResource( getAppCompatActivity().getResources(), skin.getFramesDrawables()[2]);
        playerBitmapFr3 = Bitmap.createScaledBitmap(playerBitmapFr3, 120, 120, false);


    }

    @Override
    public void start() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getAppCompatActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mViewHeight = displayMetrics.widthPixels;
        isStop = false;
    }

    /**
     * appelé a chaque frame
     * on applique de la gravité
     * si il touche le sol on le stop
     * on le déplace suivant sa vélocité
     *
     */
    public void update(){
        if(isStop) return;
        yVelocity += yspeed * getGameView().getDeltaTime();
        if(playerIsEatingTheFloor()){
            yVelocity = 0;
        }else{
            rotationAngle = yVelocity;
        }
        y += yVelocity * getGameView().getDeltaTime();
    }

    @Override
    public void stop() {
        super.stop();
        isStop = true;
    }

    @Override
    public void onScreenChange(int screenWidth, int screenHeight) {
        mViewHeight = (int) (screenHeight - height * 0.6);
    }

    public boolean playerIsEatingTheFloor(){
        return y  > mViewHeight;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void moveUp() {
        if(isStop || y < 0) return;
        yVelocity = -MOVEUPFORCE;
    }

    public Bitmap getPlayerBitmap(){
        if(yVelocity < 0){
            if(timeFromLastBitmapChange < 4){
                timeFromLastBitmapChange++;
                return previousBitmap;
            }
            timeFromLastBitmapChange = 0;
            if(previousBitmap == playerBitmapFr2){
                previousBitmap = playerBitmapFr1;
                return playerBitmapFr1;
            }else{
                previousBitmap = playerBitmapFr2;
                return playerBitmapFr2;
            }
        }
        if(yVelocity > 0){
            previousBitmap = playerBitmapFr3;
            return playerBitmapFr3;
        }

        return playerBitmapFr2;
    }

    public int getRotationAngle() {
        return (int) rotationAngle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
