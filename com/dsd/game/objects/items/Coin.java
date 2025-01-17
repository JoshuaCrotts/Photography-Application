package com.dsd.game.objects.items;

import com.dsd.game.core.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.powerups.Powerup;
import com.dsd.game.objects.powerups.PowerupType;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class represents the coin object; there are three values a coin can
 * take; small, medium, and large. The three parameters passed in the
 * constructor (the last three) designate the rarity of these.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 12/3/19
 */
public class Coin extends StandardGameObject implements Powerup {

    //  Handler for the coins.
    private final StandardCollisionHandler parentContainer;
    private final Player player;
    //  Frames of animation for the coins.
    private static final BufferedImage[] coinOneFrames;
    private static final BufferedImage[] coinTwoFrames;
    private static final BufferedImage[] coinThreeFrames;
    //  Randomness for the scatter of the coin. This the value at which the coins can scatter.
    private final double SCATTER_RANGE = 0.99;
    //  Variables for changing the speed of the coins as they disperse.
    private static final double VEL_LOWER_BOUND = 0.5;
    private static final double VEL_UPPER_BOUND = 1.5;
    private final int COIN_FPS = 5;
    private int value = 0;
    //  Max number of sound effects for coins.
    private static final int MAX_COIN_SFX = 1;
    //  Variables for generating a coin from the monsters.
    private static final int MIN_GEN_VALUE = 0;
    private static final int MAX_GEN_VALUE = 100;
    //  Coin values.
    private final int SMALL_COIN_VALUE = 1;
    private final int MED_COIN_VALUE = 5;
    private final int LARGE_COIN_VALUE = 10;
    
    /**
     * The _small, _medium, and _large parameters should be sequential, and go
     * up to 1.0. Essentially, _small = 0.7, _mid = 0.9, _large = 1.0 can be
     * your coin rarity, in that 70% of the time, a small coin will drop. 20% of
     * the time, a medium coin will drop (0.9-0.7). 10% for the large.
     *
     * @param _game
     * @param _x
     * @param _y
     * @param _small
     * @param _medium
     * @param _large
     * @param _sch
     */
    public Coin(Game _game, int _x, int _y, double _small, double _medium, double _large, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Coin);
        this.parentContainer = _sch;
        this.player = _game.getPlayer();
        this.generateCoinType(_small, _medium, _large);
        this.setVelX(StdOps.randBounds(-Coin.VEL_UPPER_BOUND, -Coin.VEL_LOWER_BOUND,
                Coin.VEL_LOWER_BOUND, Coin.VEL_UPPER_BOUND));
        this.setVelY(StdOps.randBounds(-Coin.VEL_UPPER_BOUND, -Coin.VEL_LOWER_BOUND,
                Coin.VEL_LOWER_BOUND, Coin.VEL_UPPER_BOUND));
    }

    @Override
    public void tick() {
        if (this.isAlive() && this.getAnimationController() != null) {
            this.getAnimationController().tick();
            this.slowVelocities();
            this.updatePosition();
        } else {
            this.parentContainer.removeEntity(this);
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(_g2);
        }
    }

    @Override
    public void activate() {
        this.player.setMoney(this.player.getMoney() + this.getValue());
        this.playCoinSFX();
    }

    /**
     * Plays a random coin collection sfx.
     */
    private void playCoinSFX() {
        StandardAudioController.play("src/resources/audio/sfx/coin0.wav", StandardAudioType.SFX);
    }

    /**
     * Slows the velocity of the coins gradually.
     */
    private void slowVelocities() {
        this.setVelX(this.getVelX() * this.SCATTER_RANGE);
        this.setVelY(this.getVelY() * this.SCATTER_RANGE);
    }

    /**
     * Generates a coin type depending on the probability of the coins.
     *
     * @param _small
     * @param _medium
     * @param _large
     */
    private void generateCoinType(double _small, double _medium, double _large) {
        int coin = StdOps.rand(Coin.MIN_GEN_VALUE, Coin.MAX_GEN_VALUE);
        if (coin < _small * Coin.MAX_GEN_VALUE) {
            this.setAnimation(new StandardAnimatorController(this, Coin.coinOneFrames, this.COIN_FPS));
            this.value = this.SMALL_COIN_VALUE;
        } else if (coin < _medium * Coin.MAX_GEN_VALUE) {
            this.setAnimation(new StandardAnimatorController(this, Coin.coinTwoFrames, this.COIN_FPS));
            this.value = this.MED_COIN_VALUE;
        } else if (coin < _large * Coin.MAX_GEN_VALUE) {
            this.setAnimation(new StandardAnimatorController(this, Coin.coinThreeFrames, this.COIN_FPS));
            this.value = this.LARGE_COIN_VALUE;
        }
    }

//================================= GETTERS ==================================
    public int getValue() {
        return this.value;
    }

    @Override
    public PowerupType getType() {
        return PowerupType.COIN;
    }
    //  Static initialization values.
    static {
        coinOneFrames = Utilities.loadFrames("src/resources/img/items/coin/small", 4);
        coinTwoFrames = Utilities.loadFrames("src/resources/img/items/coin/medium", 4);
        coinThreeFrames = Utilities.loadFrames("src/resources/img/items/coin/large", 4);
    }
    
}
