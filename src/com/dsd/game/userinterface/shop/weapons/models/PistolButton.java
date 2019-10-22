package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.Game;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.Pistol;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.shop.weapons.views.PistolButtonView;
import java.awt.Graphics2D;

/**
 * This button is for buying either the weapon for a rifle, or buying more rifle
 * ammunition.
 *
 * @author Joshua
 */
public class PistolButton extends ShopButton {

    private final PistolButtonView pistolButtonView;

    private static final int X_OFFSET = 400;
    private static final int Y_OFFSET = 176;

    //  Price to purchase the gun.
    private static final int PISTOL_PRICE = 50;

    //  Price per magazine.
    private static final int PISTOL_AMMO_PRICE = 25;

    public PistolButton (Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, PistolButton.X_OFFSET,
                PistolButton.Y_OFFSET, PISTOL_PRICE);

        this.pistolButtonView = new PistolButtonView(this);
    }

    @Override
    public void tick () {
        super.tick();
        this.pistolButtonView.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        this.pistolButtonView.render(_g2);
    }

    @Override
    public void onMouseClick () {
        //  If we have enough money...
        if (this.getGame().getPlayer().getMoney() > this.getPrice()) {

            Gun _weapon = (Gun) this.getInventory().hasWeapon(WeaponType.PISTOL);

            //  If we don't have the weapon, add it to the user's inventory.
            if (_weapon == null) {

                this.getInventory().addWeapon(new Pistol(this.getGame(),
                        this.getGame().getPlayer(), this.getGame().getPlayer().getHandler()));
            }
            //  Otherwise, add to the ammunition.
            else {
                _weapon.setTotalAmmo(_weapon.getTotalAmmo() + _weapon.getMagazineCapacity());
            }
        }
    }

    @Override
    public void onMouseEnterHover () {
        super.onMouseEnterHover();
    }

    @Override
    public void onMouseExitHover () {
        super.onMouseExitHover();
    }
}
