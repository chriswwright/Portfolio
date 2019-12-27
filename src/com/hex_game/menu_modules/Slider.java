package com.hex_game.menu_modules;

import com.hex_game.Main.HexGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Slider {

    Image slider;
    int slider_width;
    int slider_height;

    Image slider_block;
    int slider_block_width;
    int slider_block_height;



    public Slider(int x, int y) throws SlickException {


        slider = new Image("./res/menus/slider.png");
        slider_width = (int) HexGame.SCALE*(slider.getWidth()/2);
        slider_height = (int) HexGame.SCALE*(slider.getHeight()/2);

        slider_block = new Image("./res/menus/slider_square.png");
        slider_block_width = (int) HexGame.SCALE*(slider_block.getWidth()/4);
        slider_block_height = (int) HexGame.SCALE*(slider_block.getHeight()/4);

        slider.draw(x,y,slider_width,slider_height);
        slider_block.draw(x,y,slider_block_width,slider_block_height);

    }

}
