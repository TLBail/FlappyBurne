package com.tlbail.FlappyBurne.Model;

import com.tlbail.FlappyBurne.Activity.GameView;
import com.tlbail.FlappyBurne.gameComponent.GameComponent;
import com.tlbail.FlappyBurne.gameComponent.Graphisme.Graphics;
import com.tlbail.FlappyBurne.gameComponent.Player;
import com.tlbail.FlappyBurne.gameComponent.TubeManager;

public class GameLogic extends GameComponent {

    private Player player;
    private TubeManager tubeManager;
    private Graphics graphics;

    /**
     * class qui fait les calcul complexe de colision
     * @param gameView
     */
    public GameLogic(GameView gameView) {
        super(gameView);
        this.player = (Player) gameView.getGameComponentByClass(Player.class);
        this.tubeManager = (TubeManager) gameView.getGameComponentByClass(TubeManager.class);
        this.graphics = (Graphics) gameView.getGameComponentByClass(Graphics.class);
    }


    public boolean playerOverlappingTubes() {
        for(Tube tube : tubeManager.getTubes()){
            if(tube.isActive()){
                if(playerOvarlapBottomTube(tube) || playerOverlappTopTube(tube)) return true;
            }
        }
        return false;
    }


    private boolean playerOvarlapBottomTube(Tube tube) {
        return player.getX() + player.getWidth() / 2 > tube.getX() && player.getX() < tube.getX() + graphics.getTubeBitmap().getWidth() &&
                player.getY()  + player.getHeight() / 2> tube.getY() && player.getY() < tube.getY() + graphics.getTubeBitmap().getHeight();
    }

    private boolean playerOverlappTopTube(Tube tube) {
        int posXTopTube = tube.getX();
        int posYTopTube = tube.getY() - graphics.getTubeBitmap().getHeight() - TubeManager.SPACE_BETWEEN_TUBES_Y;
        return player.getX()  + player.getWidth() / 2 > posXTopTube && player.getX() < posXTopTube + graphics.getTubeBitmap().getWidth() &&
                player.getY() - player.getHeight() / 2   < posYTopTube + graphics.getTubeBitmap().getHeight();

    }


}
