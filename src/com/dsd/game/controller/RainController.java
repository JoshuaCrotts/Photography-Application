package com.dsd.game.controller;

import com.dsd.game.objects.RainDrop;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;

/**
 * RainController will spawn different rain (blue) particles if it is raining in
 * the location provided by the user.
 */
public class RainController implements Renderable, Updatable {

    //
    //  Reference variables
    //
    private final StandardParticleHandler sph;
    private final StandardGame sg;
    private final StandardCamera sc;

    //  Serves as a debugging feature
    private static final boolean toggleDownfall = false;

    //
    //  If it is raining, this boolean is toggled true.
    //
    private final boolean isRaining;

    //
    //  Defines the range in which rain can spawn for the user
    //
    private static final int X_BORDER = 600;
    private static final int Y_BORDER = 400;

    public RainController (StandardGame _sg, StandardCamera _sc, String _weather) {
        this.sg = _sg;
        this.sc = _sc;
        this.sph = new StandardParticleHandler(5000);
        
        // Be sure to always set the SPH camera or it'll throw a NPE
        this.sph.setCamera(this.sc);

        this.isRaining = _weather.contains("rain") | RainController.toggleDownfall;
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isRaining()) {
            this.sph.render(_g2);
        }
    }

    @Override
    public void tick () {

        if (this.isRaining()) {
            // Generates the min/max points for the rain to spawn
            int xGenMin = (int) (this.sc.getX() - RainController.X_BORDER);
            int xGenMax = (int) (this.sc.getX() + RainController.X_BORDER);
            int yGenMin = (int) (this.sc.getY() - RainController.Y_BORDER);
            int yGenMax = (int) (this.sc.getY() + RainController.Y_BORDER / 4);

            int xPos = StdOps.rand(xGenMin, xGenMax);
            int yPos = StdOps.rand(yGenMin, yGenMax);

            this.sph.addEntity(new RainDrop(xPos, yPos, -Math.PI * 1.5, Math.random() * 5,
                                           (int) (this.sc.getY() + this.sc.getVph() * 2)));

            this.sph.tick();
        }
    }

//========================== SETTERS =======================/
    public boolean isRaining () {
        return this.isRaining;
    }
}
