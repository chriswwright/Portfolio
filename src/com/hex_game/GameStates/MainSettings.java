
package com.hex_game.GameStates;
import org.newdawn.slick.*;
import com.hex_game.Main.HexGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainSettings extends BasicGameState {


    private Input input;

    //drop down variables
    private Image drop_down_img;
    private Image drop_down_activated_img;
    private int drop_width;
    private int drop_height;
    private int drop_x;
    private int drop_y;
    private Boolean drop_hover = false;
    private Boolean drop_down_activated=false;

    //resolution variables
    private Image Main_res1;
    private Image Main_res2;
    private Image Main_res3;
    private Image Main_res1_activated;
    private Image Main_res2_activated;
    private Image Main_res3_activated;
    private int drop_down_main;
    private int res_width;
    private int res_height;
    private int Main_res1_x;
    private int Main_res1_y;
    private int Main_res2_x;
    private int Main_res2_y;
    private int Main_res3_x;
    private int Main_res3_y;
    private Boolean Main_res1_hover=false;
    private Boolean Main_res2_hover=false;
    private Boolean Main_res3_hover=false;

    //back button variables
    private Image Back_img;
    private Boolean Back_Hover;
    private int Back_width;
    private int Back_height;
    private int Back_x;
    private int Back_y;
    private Image Back_activated_img;


    //Mouse variables
    private Image mouse_img;
    private int mouse_x;
    private int mouse_y;
    private int mouse_width;
    private int mouse_height;


    //check variables
    private Image check_img;
    private Image check_activated_img;
    private int check_width;
    private int check_height;
    private int check_x;
    private int check_y;
    private boolean box_check;

    //fullscreen variables
    private Image FS_img;
    private int FS_height;
    private int FS_width;
    private int FS_x;
    private int FS_y;

    // init-method for initializing all resources
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        //input listener
        input= gc.getInput();

        //resolution initializations
        Main_res1= new Image("res/menus/dropdown_1920.png");
        res_height = (int) HexGame.SCALE*(Main_res1.getHeight()/16);
        res_width=(int) HexGame.SCALE*(Main_res1.getWidth()/16);
        Main_res1_x=(HexGame.WIDTH/2)-(drop_width*11/4)-168;
        Main_res1_y=(HexGame.HEIGHT/2)-(drop_height*4)-128;
        Main_res2_x=(HexGame.WIDTH/2)-(drop_width*11/4)-168;
        Main_res2_y=Main_res1_y+res_height;
        Main_res3_x=(HexGame.WIDTH/2)-(drop_width*11/4)-168;
        Main_res3_y=Main_res2_y+res_height;
        Main_res2= new Image("res/menus/dropdown_1280.png");
        Main_res3= new Image("res/menus/dropdown_800.png");

        //resolution activations
        Main_res1_activated= new Image("res/menus/dropdown_active_1920.png");
        Main_res2_activated= new Image("res/menus/dropdown_active_1280.png");
        Main_res3_activated= new Image("res/menus/dropdown_800_active.png");

        //Drop Down
        drop_down_img = new Image("./res/menus/Drop_down.png");
        drop_down_activated_img = new Image("./res/menus/Drop_down_activated.png");
        drop_height = (int) HexGame.SCALE*(drop_down_img.getHeight()/8);
        drop_width = (int) HexGame.SCALE*(drop_down_img.getWidth()/8);
        drop_x = (HexGame.WIDTH/2)+(drop_width*11/4);
        drop_y = (HexGame.HEIGHT/2)-(drop_height*4);

        //Mouse
        mouse_img = new Image("./res/menus/mouse.png");
        mouse_img.setFilter(Image.FILTER_NEAREST);
        mouse_width = (int) HexGame.SCALE*(mouse_img.getWidth()/2);
        mouse_height = (int) HexGame.SCALE*(mouse_img.getHeight()/2);
        mouse_x = 0;
        mouse_y = 0;

        //Back Button
        Back_img=new Image("res/menus/Back_Button.png");
        Back_img.setFilter(Image.FILTER_NEAREST);
        Back_Hover=false;
        Back_width= (int) HexGame.SCALE*(Back_img.getWidth()*2);
        Back_height=(int) HexGame.SCALE*(Back_img.getHeight()*2);
        Back_x=(HexGame.WIDTH/2)-(Back_width/2);
        Back_y=(HexGame.HEIGHT/2)+(Back_height*2);

        // Back Button Activated
        Back_activated_img = new Image("./res/menus/Back_Button_Active.png");
        Back_activated_img.setFilter(Image.FILTER_NEAREST);


        // Fullscreen
        FS_img = new Image("./res/menus/Fullscreen.png");
        FS_img.setFilter(Image.FILTER_NEAREST);
        FS_width = (int) HexGame.SCALE*(FS_img.getWidth()*2);
        FS_height = (int) HexGame.SCALE*(FS_img.getHeight()*2);
        FS_width = (int) HexGame.SCALE*(64);
        FS_height = (int) HexGame.SCALE*(32);
        FS_x = (HexGame.WIDTH/2)-(FS_width/2);
        FS_y = (HexGame.HEIGHT/2)-(FS_height*3);

        //check
        check_img = new Image("./res/menus/Check_Unactivated.png");
        check_img.setFilter(Image.FILTER_NEAREST);
        check_width = (int) HexGame.SCALE*(check_img.getWidth()/2);
        check_height = (int) HexGame.SCALE*(check_img.getHeight()/2);
        check_x = (HexGame.WIDTH/2)-(FS_width/2)-(check_img.getWidth());
        check_y = (HexGame.HEIGHT/2)-(FS_height*21/8);
        box_check = false;

        //check box activated
        check_activated_img = new Image("./res/menus/Check_Activated.png");
    }

    // render-method for all the things happening on-screen
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        //Resolution
        //drop_down shows the current resolution at the top based on what the user clicked in the drop down menu (mainly handled in update)
        if(drop_down_main==0){
            Main_res1.draw(Main_res1_x, Main_res1_y, res_width, res_height);
            if(Main_res1_hover) {
                Main_res1_activated.draw(Main_res1_x, Main_res1_y, res_width, res_height);
            }
            //when you press the arrow show the other options in the drop down menu
            if(drop_down_activated){
                //highlights when your mouse is over the choice in the drop down menu

                if(Main_res2_hover){
                    Main_res2_activated.draw(Main_res2_x, Main_res2_y, res_width, res_height);
                }else {
                    //otherwise shows it normally
                    Main_res2.draw(Main_res2_x, Main_res2_y, res_width, res_height);
                }
                if(Main_res3_hover){ //same as above
                    Main_res3_activated.draw(Main_res3_x, Main_res3_y, res_width, res_height);
                }else {
                    Main_res3.draw(Main_res3_x, Main_res3_y, res_width, res_height);
                    }
                }
        //same as above but with a different resolution choice
        }else if(drop_down_main==1) {
            Main_res2.draw(Main_res1_x, Main_res1_y, res_width, res_height);
            if(drop_down_activated){
                if(Main_res1_hover) {
                    Main_res1_activated.draw(Main_res2_x, Main_res2_y, res_width, res_height);
                }else{
                    Main_res1.draw(Main_res2_x, Main_res2_y, res_width, res_height);
                }
                if(Main_res3_hover){
                    Main_res3_activated.draw(Main_res3_x, Main_res3_y, res_width, res_height);
                }else {
                    Main_res3.draw(Main_res3_x, Main_res3_y, res_width, res_height);
                }
            }
            //same as above but with a different resolution choice
        }else if(drop_down_main==2){
            Main_res3.draw(Main_res1_x, Main_res1_y, res_width, res_height);
            if(drop_down_activated) {
                if(Main_res1_hover) {
                    Main_res1_activated.draw(Main_res2_x, Main_res2_y, res_width, res_height);
                }else {
                    Main_res1.draw(Main_res2_x, Main_res2_y, res_width, res_height);
                }
                if(Main_res2_hover) {
                    Main_res2_activated.draw(Main_res3_x, Main_res3_y, res_width, res_height);
                }else{
                    Main_res2.draw(Main_res3_x, Main_res3_y, res_width, res_height);
                }
            }

        }

        //sets the background
        g.setBackground(Color.decode("#AFEEEE"));

        //literally writes fullscreen but in png form
        FS_img.draw(FS_x, FS_y, FS_width, FS_height);

        //handles all the highlighting except for resolution
        if(!drop_hover) {
            drop_down_img.draw(drop_x, drop_y, drop_width, drop_height);
        } else {
            drop_down_activated_img.draw(drop_x, drop_y, drop_width, drop_height);
        }

        if(!box_check) {
            check_img.draw(check_x, check_y, check_width, check_height);
        } else {
            check_activated_img.draw(check_x, check_y, check_width, check_height);
        }
        if (!Back_Hover) {
            Back_img.draw(Back_x, Back_y, Back_width, Back_height);
        } else {
            Back_activated_img.draw(Back_x,Back_y,Back_width,Back_height);
        }

        //draws a special mouse
        mouse_img.draw(mouse_x, mouse_y, mouse_width, mouse_height);


    }

    // update-method with all the magic happening in it
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        //listening to the mouse
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        mouse_x = mouseX;
        mouse_y = mouseY;

        //Drop Hover
        if (mouseX >= drop_x && mouseX <= drop_x+drop_width && mouseY >= drop_y && mouseY <= drop_y+drop_height) {
            drop_hover = true;
            //when you press the drop down menu
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                drop_down_activated = !drop_down_activated;
            }
        }
        else {
            drop_hover = false;
        }

        //a cast so we can change the resolution
        AppGameContainer gd= (AppGameContainer) gc;


        //if we hover over the 1920 x 1080
        if(mouseX >= Main_res1_x && mouseX <= Main_res1_x+res_width && mouseY >= Main_res1_y && mouseY <= Main_res1_y+res_height && drop_down_activated){
            Main_res1_hover=true;
            //if we press the first button
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                drop_down_main=0;
                gd.setDisplayMode(1920, 1080, box_check);
            }
        }else{
            Main_res1_hover=false;
        }

        //if we hover over 1280 x 720
        if(mouseX >= Main_res2_x && mouseX <= Main_res2_x+res_width && mouseY >= Main_res2_y && mouseY <= Main_res2_y+res_height && drop_down_activated){
            Main_res2_hover=true;
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                drop_down_main=1;
                gd.setDisplayMode(1280, 720, box_check);
            }
        }else{
            Main_res2_hover=false;
        }

        //if we hover over 800 x 600
        if(mouseX >= Main_res3_x && mouseX <= Main_res3_x+res_width && mouseY >= Main_res3_y && mouseY <= Main_res3_y+res_height && drop_down_activated){
            Main_res3_hover=true;
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                drop_down_main=2;
                gd.setDisplayMode(800, 600, box_check);
            }
        }else{
            Main_res3_hover=false;
        }


        //Back Hover
        if (mouseX >= Back_x && mouseX <= Back_x+Back_width && mouseY >= Back_y && mouseY <= Back_y+Back_height) {

            Back_Hover = true;

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                System.out.println("Back Pressed");
                sbg.enterState(HexGame.MAINMENU, new FadeOutTransition(), new FadeInTransition());

            }

        } else {
            Back_Hover = false;
        }

        //Check box
        if(mouseX >= check_x && mouseX <= check_x+check_width && mouseY >= check_y && mouseY <= check_y+check_height) {
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                System.out.println("Check box pressed");
                if(!box_check) {
                    gc.setFullscreen(true);
                    box_check = true;
                } else {
                    gc.setFullscreen(false);
                    box_check = false;
                }
            }
        }

        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            System.exit(0);
        }

    }

    // Returning 'ID' from class 'MainMenu'
    @Override
    public int getID() {
        return HexGame.MAINSETTINGS;
    }

}

