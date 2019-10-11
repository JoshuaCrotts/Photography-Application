package com.dsd.game.database;

import com.dsd.game.AccountStatus;
import com.dsd.game.Game;
import javax.swing.JOptionPane;

/**
 * This class, very similar to the TranslatorAPI class, acts as the intermediary
 * between the persistent database and the game itself. The game will contact
 * the translator with information to save, and vice versa for when information
 * needs to be loaded (which will only be once).
 *
 * Information to be saved: - Account info (if applicable): email, password,
 * name - Player inventory - Current level/wave
 *
 * This implementation allows for new Player objects AND levels to be directly
 * instantiated into the code after loading them in from this translator. After
 * parsing through the file, we will load in the previous game's weapons,
 * player's health, etc. into a new game. The background of the wave/level/stage
 * is also saved.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class TranslatorDatabase {

    private final Game game;
    private static PersistentDatabase database;

    public TranslatorDatabase (Game _game) {
        this.game = _game;
        TranslatorDatabase.database = new PersistentDatabase();
    }

    /**
     * Save the Game state, the level state, the player's health, money, current
     * inventory.
     */
    public void save () {
        TranslatorDatabase.database.save();
    }

    /**
     * Load the game state, level state, player health, money and current
     * inventory
     */
    public void load () {
        TranslatorDatabase.database.load();
    }

    public static AccountStatus authenticateUser (String _email, String _password) {
        if (database.connect("users")) {
            return database.userAuthenticated(_email, _password);
        }

        throw new IllegalStateException("Could not connect to db!");
    }

    public static AccountStatus addUser (String _email, String _password) {
        if (database.connect("users")) {
            return database.addUser(_email, _password);
        }

        throw new IllegalStateException("Could not connect to db!");
    }
}