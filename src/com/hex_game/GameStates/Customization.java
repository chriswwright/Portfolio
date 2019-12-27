package com.hex_game.GameStates;

import com.hex_game.Main.HexGame;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Customization extends BasicGameState {

   // Button Timers
    private long pastTime_top;
    private long pastTime2_top;
    private long pastTime_middle;
    private long pastTime2_middle;
    private long pastTime_bottom;
    private long pastTime2_bottom;

    // Background Panel
    private Image panel_img;
    private int panel_width;
    private int panel_height;
    private int panel_x;
    private int panel_y;

    // Left Arrow
    private Image arrow_left;
    private Image arrow_left_clicked;
    private int LArrow_width;
    private  int LArrow_height;
    private  int LArrow_x;
    private  int LArrow_y;

    // Right Arrow
    private   Image arrow_right;
    private  Image arrow_right_clicked;
    private  int RArrow_width;
    private   int RArrow_height;
    private   int RArrow_x;
    private   int RArrow_y;

    // Inputs
    private Input input;


    // Click Booleans
    private boolean LArrow_click_top;
    private boolean RArrow_click_top;
    private boolean LArrow_click_middle;
    private boolean RArrow_click_middle;
    private boolean LArrow_click_bottom;
    private boolean RArrow_click_bottom;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        // Button Timers
        pastTime_top = 0;
        pastTime2_top = 0;
        pastTime_middle = 0;
        pastTime2_middle = 0;
        pastTime_bottom = 0;
        pastTime2_bottom = 0;

        // Input Init
        input = gc.getInput();

        // Click Booleans
        LArrow_click_top = false;
        RArrow_click_top = false;
        LArrow_click_middle = false;
        RArrow_click_middle = false;
        LArrow_click_bottom = false;
        RArrow_click_bottom = false;

        // Panel Background
        panel_img = new Image("./res/menus/customization_bg.png");
        panel_img.setFilter(Image.FILTER_NEAREST);
        panel_width = (int) HexGame.SCALE*(panel_img.getWidth()*2);
        panel_height = (int) HexGame.SCALE*(panel_img.getHeight()*2);
        panel_x = (HexGame.WIDTH/2)-(panel_width/2);
        panel_y = (HexGame.HEIGHT/2)-(panel_height/2);

        // Left Arrow
        arrow_left = new Image("./res/menus/arrow_left.png");
        arrow_left.setFilter(Image.FILTER_NEAREST);
        arrow_left_clicked = new Image("./res/menus/arrow_left_activated.png");
        arrow_left_clicked.setFilter(Image.FILTER_NEAREST);
        LArrow_width = (int) HexGame.SCALE*(arrow_left.getWidth()/2);
        LArrow_height = (int) HexGame.SCALE*(arrow_left.getHeight()/2);
        LArrow_x = (HexGame.WIDTH/2)-(LArrow_width*3);
        LArrow_y = (HexGame.HEIGHT/2)-(LArrow_height/2);

        // Right Arrow
        arrow_right = new Image("./res/menus/arrow_right.png");
        arrow_right.setFilter(Image.FILTER_NEAREST);
        arrow_right_clicked = new Image("./res/menus/arrow_right_activated.png");
        arrow_right_clicked.setFilter(Image.FILTER_NEAREST);
        RArrow_width = (int) HexGame.SCALE*(arrow_right.getWidth()/2);
        RArrow_height = (int) HexGame.SCALE*(arrow_right.getHeight()/2);
        RArrow_x = (HexGame.WIDTH/2)+(RArrow_width*2);
        RArrow_y = (HexGame.HEIGHT/2)-(RArrow_height/2);
    }

    // render-method for all the things happening on-screen
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){

        // Panel Render
        panel_img.draw(panel_x,panel_y,panel_width,panel_height);

        // Top Arrows
        if (!LArrow_click_top) {
            arrow_left.draw(LArrow_x,LArrow_y - (LArrow_x/4), LArrow_width,LArrow_height);
        } else {
            arrow_left_clicked.draw(LArrow_x,LArrow_y - LArrow_x/4,LArrow_width,LArrow_height);
        }

        if (!RArrow_click_top) {
            arrow_right.draw(RArrow_x,LArrow_y - LArrow_x/4,RArrow_width,RArrow_height);
        } else {
            arrow_right_clicked.draw(RArrow_x,LArrow_y - LArrow_x/4,RArrow_width,RArrow_height);
        }

        // Middle Arrows
        if (!LArrow_click_middle) {
            arrow_left.draw(LArrow_x,LArrow_y,LArrow_width,LArrow_height);
        } else {
            arrow_left_clicked.draw(LArrow_x,LArrow_y,LArrow_width,LArrow_height);
        }

        if (!RArrow_click_middle) {
            arrow_right.draw(RArrow_x,RArrow_y,RArrow_width,RArrow_height);
        } else {
            arrow_right_clicked.draw(RArrow_x,RArrow_y,RArrow_width,RArrow_height);
        }

        // Bottom Arrows
        if (!LArrow_click_bottom) {
            arrow_left.draw(LArrow_x,LArrow_y+LArrow_x/4,LArrow_width,LArrow_height);
        } else {
            arrow_left_clicked.draw(LArrow_x,LArrow_y+LArrow_x/4,LArrow_width,LArrow_height);
        }

        if (!RArrow_click_bottom) {
                arrow_right.draw(RArrow_x,LArrow_y+LArrow_x/4,RArrow_width,RArrow_height);
        } else {
            arrow_right_clicked.draw(RArrow_x,LArrow_y+LArrow_x/4,RArrow_width,RArrow_height);
        }

    }

    // update-method with all the magic happening in it
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        // Button Click Listeners

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

             if (mouseX >= LArrow_x && mouseX <= LArrow_x+LArrow_width && mouseY >= LArrow_y-LArrow_x/4 && mouseY <= LArrow_y-LArrow_x/4+LArrow_height) {
                System.out.println("Left Top Arrow Pressed");
                LArrow_click_top = true;
            }

            if (mouseX >= RArrow_x && mouseX <= RArrow_x+RArrow_width && mouseY >= LArrow_y-LArrow_x/4 && mouseY <= LArrow_y-LArrow_x/4+RArrow_height) {
                System.out.println("Right Top Arrow Pressed");
                RArrow_click_top = true;
            }

            if (mouseX >= LArrow_x && mouseX <= LArrow_x+LArrow_width && mouseY >= LArrow_y && mouseY <= LArrow_y+LArrow_height) {
                System.out.println("Left Middle Arrow Pressed");
                LArrow_click_middle = true;
            }

            if (mouseX >= RArrow_x && mouseX <= RArrow_x+RArrow_width && mouseY >= RArrow_y && mouseY <= RArrow_y+RArrow_height) {
                System.out.println("Right Middle Arrow Pressed");
                RArrow_click_middle = true;
            }


            if (mouseX >= LArrow_x && mouseX <= LArrow_x+LArrow_width && mouseY >= LArrow_y+LArrow_x/4 && mouseY <= LArrow_y+LArrow_x/4+LArrow_height) {
                System.out.println("Left Bottom Arrow Pressed");
                LArrow_click_bottom = true;
            }

            if (mouseX >= RArrow_x && mouseX <= RArrow_x+RArrow_width && mouseY >= LArrow_y+LArrow_x/4 && mouseY <= LArrow_y+LArrow_x/4+RArrow_height) {
                System.out.println("Right Bottom Arrow Pressed");
                RArrow_click_bottom = true;
            }


        } else {

            if (pastTime_top > 500) {
                LArrow_click_top = false;
                pastTime_top = 0;
            } else {
                pastTime_top += delta;
            }

            if (pastTime2_top > 500) {
                RArrow_click_top = false;
                pastTime2_top = 0;
            } else {
                pastTime2_top += delta;
            }

            if (pastTime_middle > 500) {
                LArrow_click_middle = false;
                pastTime_middle = 0;
            } else {
                pastTime_middle += delta;
            }

            if (pastTime2_middle > 500) {
                RArrow_click_middle = false;
                pastTime2_middle = 0;
            } else {
                pastTime2_middle += delta;
            }

            if (pastTime_bottom > 500) {
                LArrow_click_bottom = false;
                pastTime_bottom = 0;
            } else {
                pastTime_bottom += delta;
            }

            if (pastTime2_bottom > 500) {
                RArrow_click_bottom = false;
                pastTime2_bottom = 0;
            } else {
                pastTime2_bottom += delta;
            }



        }



    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return HexGame.GAME;

    }
}
