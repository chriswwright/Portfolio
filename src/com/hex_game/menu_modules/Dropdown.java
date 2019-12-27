package com.hex_game.menu_modules;

import com.hex_game.Main.HexGame;
import org.newdawn.slick.*;

public class Dropdown {





    public Dropdown(int x, int y, GameContainer gc) throws SlickException {

        boolean isopen = false;

        Graphics g = new Graphics();

        int width;
        int height;

        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        Image dropdown = new Image("./res/menus/dropdown_top.png");
        dropdown.setFilter(Image.FILTER_NEAREST);

        width = (int) HexGame.SCALE*(dropdown.getWidth());
        height = (int) HexGame.SCALE*(dropdown.getHeight()/2);

        dropdown.draw(x,y,width,height);


        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)) {
                isopen = true;
            }
        }

        if (isopen == true) {
            g.setColor(Color.darkGray);
            g.fillRect(x, (y * 2) - (height * 2) - (int) HexGame.SCALE * 5, width, height * 8);
        }

    }

}
