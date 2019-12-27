package com.hex_game.GameStates;

import com.hex_game.Main.HexGame;
//import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu extends BasicGameState {

    private Image mouse_img;
    private Image start_img;
    private Image start_activated_img;
    private Image options_img;
    private Image options_activated_img;
    private Image exit_img;
    private Image exit_activated_img;
    private Image title_img;

    private Music menuMusic;

    private int mouse_x;
    private int mouse_y;
    private int mouse_width;
    private int mouse_height;

    private int title_x;
    private int title_y;
    private int title_width;
    private int title_height;

    private int start_x;
    private int start_y;
    private int start_width;
    private int start_height;

    private int options_width;
    private int options_height;
    private int options_x;
    private int options_y;

    private int exit_width;
    private int exit_height;
    private int exit_x;
    private int exit_y;

    private boolean start_hover;
    private boolean options_hover;
    private boolean exit_hover;

    private Input input;


    // Initialization Method
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        //Menu Music
        menuMusic = new Music("./res/Music/menu.ogg");

        // Input Listener
        input = gc.getInput();
        Mouse.setGrabbed(true);

        // Menu Buttons
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //Mouse
        mouse_img = new Image("./res/menus/mouse.png");
        mouse_img.setFilter(Image.FILTER_NEAREST);

        mouse_width = (int) HexGame.SCALE*(mouse_img.getWidth()/2);
        mouse_height = (int) HexGame.SCALE*(mouse_img.getHeight()/2);

        mouse_x = mouseX;
        mouse_y = mouseY;

        // Start Button
        start_img = new Image("./res/menus/Start_Button.png");
        start_img.setFilter(Image.FILTER_NEAREST);

        start_width = (int) HexGame.SCALE*(start_img.getWidth()*2);
        start_height = (int) HexGame.SCALE*(start_img.getHeight()*2);

        start_x = (HexGame.WIDTH/2)-(start_width/2);
        start_y = (HexGame.HEIGHT/2);
        start_hover = false;


        // Start Button Activated
        start_activated_img = new Image("./res/menus/Start_Button_Active.png");
        start_activated_img.setFilter(Image.FILTER_NEAREST);

        // Options Button
        options_img = new Image("./res/menus/Options_Button.png");
        options_img.setFilter(Image.FILTER_NEAREST);
        options_hover = false;

        options_width = (int) HexGame.SCALE*(start_img.getWidth()*2);
        options_height = (int) HexGame.SCALE*(start_img.getHeight()*2);

        options_x = (HexGame.WIDTH/2)-(options_width/2);
        options_y = (HexGame.HEIGHT/2)+(options_height);

        // Options Button Activated
        options_activated_img = new Image("./res/menus/Options_Button_Active.png");
        options_activated_img.setFilter(Image.FILTER_NEAREST);

        // Exit Button
        exit_img = new Image("./res/menus/Exit_Button.png");
        exit_img.setFilter(Image.FILTER_NEAREST);
        exit_hover = false;

        exit_width = (int) HexGame.SCALE*(exit_img.getWidth()*2);
        exit_height = (int) HexGame.SCALE*(exit_img.getHeight()*2);

        exit_x = (HexGame.WIDTH/2)-(exit_width/2);
        exit_y = (HexGame.HEIGHT/2)+(exit_height*2);

        // Exit Button Activated
        exit_activated_img = new Image("./res/menus/Exit_Button_Active.png");
        exit_activated_img.setFilter(Image.FILTER_NEAREST);

        // Title Image
        title_img = new Image("./res/menus/NTitle.png");
        title_img.setFilter(Image.FILTER_NEAREST);

        title_width = (int) HexGame.SCALE*(title_img.getWidth()*2);
        title_height = (int) HexGame.SCALE*(title_img.getHeight()*2);

        title_x = (HexGame.WIDTH/2)-(title_width/2);
        title_y = (HexGame.HEIGHT/2)-(title_height);




    }

    // Rendering Method
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        g.setBackground(Color.decode("#AFEEEE"));
        title_img.draw(title_x, title_y, title_width, title_height);


        mouse_img.draw(mouse_x, mouse_y, mouse_width, mouse_height);

        if (!start_hover) {
            start_img.draw(start_x, start_y, start_width, start_height);
        } else {
            start_activated_img.draw(start_x,start_y,start_width,start_height);
        }

        if (!options_hover) {
            options_img.draw(options_x, options_y, options_width, options_height);
        } else {
            options_activated_img.draw(options_x,options_y,options_width,options_height);
        }

        if (!exit_hover) {
            exit_img.draw(exit_x, exit_y, exit_width, exit_height);
        } else {
            exit_activated_img.draw(exit_x,exit_y,exit_width,exit_height);
        }

    }

    // Update Method
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {


        if(!menuMusic.playing())
            menuMusic.loop();

        // Menu Buttons
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //Mouse

        mouse_x = mouseX;
        mouse_y = mouseY;

        // Start Button
        if (mouseX >= start_x && mouseX <= start_x+start_width && mouseY >= start_y && mouseY <= start_y+start_height) {
            start_hover = true;

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                System.out.println("Start Pressed");
                menuMusic.stop();
                sbg.enterState(HexGame.WorldMap, new FadeOutTransition(), new FadeInTransition());

            }
        } else {
            start_hover = false;
        }
        //Settings
        if (mouseX >= options_x && mouseX <= options_x+options_width && mouseY >= options_y && mouseY <= options_y+options_height) {

            options_hover = true;

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                System.out.println("Options Pressed");
                sbg.enterState(HexGame.MAINSETTINGS, new FadeOutTransition(), new FadeInTransition());

            }

        } else {
            options_hover = false;
        }

        if (mouseX >= exit_x && mouseX <= exit_x+options_width && mouseY >= exit_y && mouseY <= exit_y+exit_height) {

            exit_hover = true;

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                System.out.println("Exit Pressed");
                System.exit(0);

            }

        } else {
            exit_hover = false;
        }

        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            menuMusic.stop();
            System.exit(0);
        }


    }


    // ID Return
    @Override
    public int getID() {
        return HexGame.MAINMENU;
    }

}
