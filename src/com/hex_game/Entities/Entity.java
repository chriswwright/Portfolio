package com.hex_game.Entities;

import com.hex_game.GameStates.WorldMap;
import com.hex_game.PathingStrategies.QueueItem;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import java.util.List;

public class Entity implements Target{
    protected Point position;
    protected boolean collision;
    public List<Image> spriteList;
    public int spriteIndex;
    public int owner;
    public WorldMap world;
    //add inventory object of items
    //add stats object


    public Entity(Point position,
                  List<Image> spSet, boolean collision, WorldMap world , int owner) {
        this.world = world;
        this.position = position;
        this.collision = collision;
        this.spriteIndex = 0;
        this.spriteList = spSet;
        this.owner = owner;
    }

    public Point getPosition(){
        return position;
    }

    public int getOwner() {
        return owner;
    }

    public boolean equals(Object other){
        if(other instanceof Entity){
            Entity op = (Entity) other;
            return position.getX() == op.position.getX() && position.getY() == op.position.getY();
        }
        else {
            return false;
        }
    }

    public Image getImage(){
        return spriteList.get(spriteIndex);
    }

    public boolean getCollision() {return collision;}

}