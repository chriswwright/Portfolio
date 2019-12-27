package com.hex_game.Entities.NPC_Entities;

import com.hex_game.Entities.Entity;
import com.hex_game.Entities.MovableEntity;
import com.hex_game.Entities.Point;
import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.Image;

import java.util.List;

public class TestEntity extends Entity {

    public TestEntity(Point position,
                      List<Image> spSet, boolean collision, WorldMap world, int owner){
        super(position, spSet, collision, world, owner);
    }
}
