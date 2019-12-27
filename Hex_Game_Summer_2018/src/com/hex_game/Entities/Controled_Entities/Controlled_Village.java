package com.hex_game.Entities.Controled_Entities;

import com.hex_game.Entities.Point;
import com.hex_game.Entities.SchedulableEntity;
import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.Image;

import java.util.List;

public class Controlled_Village extends SchedulableEntity {

    public Controlled_Village(Point position, List<Image> spSet, boolean collision, int actionPeriod, WorldMap world, int owner){
        super(position, spSet, collision, actionPeriod, world, owner);
    }

}
