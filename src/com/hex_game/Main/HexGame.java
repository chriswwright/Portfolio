package com.hex_game.Main;

import com.hex_game.GameStates.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class HexGame extends StateBasedGame {


    // Game State Identifiers
//    public static final int SPLASHSCREEN = 0;
    public static final int MAINMENU = 1;
    public static final int MAINSETTINGS = 2;
    public static final int GAME = 3;
    public static final int WorldMap = 4;
    public static final int BattleScreen = 5;

    //Properties
    // Default: 800
    public static final int WIDTH  = 1920;
    // Default: 600
    public static final int HEIGHT = 1080;
    public static final int FPS = 60;
    public static final double SCALE = 2.4;
    private static final double VERSION = 0.1;
    private static final String STAGE = "a";

    // Constructor
    private HexGame(String name) {
        super(name);
    }

    // Game State Initialization
    public void initStatesList(GameContainer gc) {

        this.addState(new MainMenu());
        this.addState(new MainSettings());
        this.addState(new Customization());
        this.addState(new WorldMap());
        this.addState(new BattleScreen());
    }

    public static void main(String[] args) {

        try {
            AppGameContainer app;
            app = new AppGameContainer(new HexGame("Hex Game Test Stage" + VERSION + STAGE));
            app.setDisplayMode(WIDTH,HEIGHT,false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

}
