package com.tlbail.FlappyBurne.gameComponent.Graphisme;

import android.graphics.Canvas;

public class GraphicsStartDecorator implements Graphical {

    private Graphics graphics;

    public GraphicsStartDecorator(Graphics graphics) {
        this.graphics = graphics;
    }

    /**
     * class qui redefinie graphics pour qu'elle dessine juste le sols et le fond
     * @param canvas
     */

    @Override
    public void drawCanvas(Canvas canvas) {
        graphics.drawBackground(canvas);
        graphics.drawGround(canvas);
    }
}
