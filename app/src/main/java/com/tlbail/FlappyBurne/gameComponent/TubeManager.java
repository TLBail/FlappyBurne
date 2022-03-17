package com.tlbail.FlappyBurne.gameComponent;

import android.util.DisplayMetrics;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.Model.Tube;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TubeManager extends GameComponent {

    List<Tube> tubes;


    private int screenHeight;
    private int screenWidth;

    private static final int SPACE_BETWEEN_TUBES = 650;
    private static final int NUMBER_MIN_OF_TUBE_ACTIVE = 5;
    private static final int MULTIPLIYERFORSTARTTEMPO = 3; // permet de mettre les tubes plus ou moins loin au début
    public  static final int SPACE_BETWEEN_TUBES_Y = 450; // permet de mettre les tubes plus ou moins loin au début

    private boolean isStoped;


    /**
     * s'occupe de tout les tube
     * sa les charge les décharge avec du object pool patern
     *
     *
     * @param gameView
     */
    public TubeManager(GameView gameView) {
        super(gameView);
        tubes = new ArrayList<>();
    }


    @Override
    public void start() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getAppCompatActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth= displayMetrics.heightPixels;
        screenHeight = displayMetrics.widthPixels;
        for (int i = MULTIPLIYERFORSTARTTEMPO; i <= MULTIPLIYERFORSTARTTEMPO +  NUMBER_MIN_OF_TUBE_ACTIVE; i++) {
            tubes.add(new Tube(this, (int) SPACE_BETWEEN_TUBES * i, getRandomYpos() * 2, true));
        }

    }

    @Override
    public void stop() {
        super.stop();
        isStoped = true;
    }

    /**
     * update la postion de chaque tube  a chaque frame
     * si il manque des tube sa en rajoute
     */
    @Override
    public void update() {

        if(isStoped) return;
        for (Tube tube: tubes ) {
            if(tube.isActive())  tube.moveTube();
        }

        if(getCountOfTubeActive() < NUMBER_MIN_OF_TUBE_ACTIVE){
            Tube tube = createTube();
        }

    }

    @Override
    public void onScreenChange(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public double getDeltaTime(){
        return getGameView().getDeltaTime();
    }

    public List<Tube> getTubes() {
        return tubes;
    }

    public void desactiveThisTube(Tube tube) {
        tube.setActive(false);
    }

    private int  getCountOfTubeActive(){
        int sum = 0;
        for (Tube tube: tubes ) {
            if(tube.isActive()) sum++;
        }
        return sum;

    }

    /**
     * sa essaye de trouver un tube pas active
     * si pas de tube activé => nouvelle objet tube
     * sinon on reload les propriété du tube
     * voir object pool partern
     * @return
     */
    private Tube createTube() {
        Tube tube = getTubeNotActive();
        ScoreManager scoreManager = (ScoreManager) getGameView().getGameComponentByClass(ScoreManager.class);

        if(tube == null){
            tube = new Tube(this, getXpos(), getRandomYpos(), true);
            tubes.add(tube);

        }else{
            tube.reload(getXpos(), getRandomYpos());
        }
        if(scoreManager.getScore() >= scoreManager.getBestScore() - 4){
            System.out.println("gollllllden");
            tube.setGolden();
        }

        return tube;
    }


    private int getRandomYpos(){
        return new Random().nextInt(screenHeight / 4 ) +  screenHeight / 2;
    }


    private int getXpos(){
        int maxX = 0;
        for(Tube tube : tubes){
            if(tube.isActive() && tube.getX() > maxX) maxX = tube.getX();
        }
        return maxX + SPACE_BETWEEN_TUBES;
    }

    /**
     * voir object pool partern
     * @return
     */
    public Tube getTubeNotActive(){
        for (Tube tube: tubes  ) {
            if(!tube.isActive()) return tube;
        }
        return null;
    }


}
