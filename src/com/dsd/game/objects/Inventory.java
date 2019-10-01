package com.dsd.game.objects;

import com.dsd.game.objects.weapons.Weapon;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.Game;
import com.dsd.game.PlayerState;
import com.dsd.game.WeaponSelection;
import com.dsd.game.objects.weapons.Knife;
import com.dsd.game.objects.weapons.Pistol;
import com.dsd.game.objects.weapons.Rifle;
import com.dsd.game.userinterface.InventoryView;
import com.revivedstandards.handlers.StandardCollisionHandler;
import java.util.List;
import java.util.ArrayList;

/**
 * Model representing the player's current inventory.
 *
 * @author Joshua
 */
public class Inventory {

    private final Game game;
    private final Player player;
    private final InventoryView view;
    private final List<Weapon> weapons;

    private int currentWeapon = 0;
    private boolean hasGun;

    public Inventory (Game _game, Player _player, StandardCollisionHandler _sch) {
        this.game = _game;
        this.player = _player;
        this.weapons = new ArrayList<>();

        //  This will change with time (to a subclass of Weapon).
        this.weapons.add(new Knife(_player));
        this.weapons.add(new Pistol(_game, _player, _sch));
        this.weapons.add(new Rifle(_game, _player, _sch));
        this.view = new InventoryView(this.game, this);
    }

    /**
     * Either increments or decrements the weapon pointer, depending on what
     * button the user pressed.
     *
     * @param choice
     */
    public void changeWeapon (WeaponSelection choice) {
        this.currentWeapon += choice.getChange();
        this.clampWeapon();
        this.updateAnimation();
        this.hasGun = this.weapons.get(this.currentWeapon) instanceof Gun;
    }

    /**
     * Clamps the pointer for the current weapon to values in the arraylist.
     */
    private void clampWeapon () {
        if (this.currentWeapon < 0) {
            this.currentWeapon = this.weapons.size() - 1;
        }
        else if (this.currentWeapon >= this.weapons.size()) {
            this.currentWeapon = 0;
        }
    }

    /**
     * Updates the animation set by the player determined by which weapon they
     * have.
     */
    private void updateAnimation () {
        if (this.player.getPlayerState() == PlayerState.WALKING || this.player.getPlayerState() == PlayerState.STANDING) {
            this.player.setAnimation(this.weapons.get(currentWeapon).getWalkFrames());
        }
        else if (this.player.getPlayerState() == PlayerState.ATTACKING) {
            this.player.setAnimation(this.weapons.get(currentWeapon).getAttackFrames());
        }

        this.player.setAttackAnimator(this.weapons.get(currentWeapon).getAttackFrames());
    }

//============================= GETTERS ===================================//
    public Weapon getCurrentWeapon () {
        return weapons.get(this.currentWeapon);
    }

    public Gun getGun () {
        Gun gun = null;

        try {
            gun = (Gun) this.getCurrentWeapon();
        }
        catch (ClassCastException ex) {
            System.err.println("Could not cast weapon to gun!");
            return null;
        }
        return gun;
    }

    public InventoryView getView () {
        return this.view;
    }

    public boolean hasGun () {
        return this.hasGun;
    }

//============================= SETTERS ===================================//
}