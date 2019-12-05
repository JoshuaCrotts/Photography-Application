package com.dsd.game.userinterface.model;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.labels.PasswordLabel;
import java.awt.Graphics2D;

/**
 * Text-field for the user to enter their password.
 *
 * @author Joshua
 */
public class PasswordTextFieldModel extends TextFieldModel {

    private final int BUTTON_X_OFFSET = 300;
    private final PasswordLabel passwordLabel;
    
    public static final int PASS_MIN_LENGTH = 8;

    public PasswordTextFieldModel(int _x, int _y, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, _game, _menuScreen);
        this.passwordLabel = new PasswordLabel(this, _menuScreen);
        super.getMenuScreen().addInteractor(this.passwordLabel);
        super.toggleStringVisible();
    }

    @Override
    public void tick() {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.tick();
        this.setX(Screen.gameHalfWidth - BUTTON_X_OFFSET);
        this.setY(Screen.gameHalfHeight);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.render(_g2);
    }

}