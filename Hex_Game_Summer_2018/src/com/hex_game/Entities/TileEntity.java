package com.hex_game.Entities;

import com.hex_game.Actions.Action;
import com.hex_game.Actions.AnimationAction;
import com.hex_game.Events.EventSchedule;
import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.Image;

import java.util.List;

public class TileEntity extends AnimatableEntity{

    private int animationPeriod;
    private int height;

    public TileEntity(Point position, List<Image> spSet, boolean collision, int actionPeriod, int animationPeriod, WorldMap world, int owner, int height){
        super(position, spSet, collision, actionPeriod, animationPeriod, world, owner);
        this.animationPeriod = animationPeriod;
        this.height = height;
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new AnimationAction(this, repeatCount);
    }

    public void nextImage()
    {
        this.spriteIndex = (this.spriteIndex + 1) % this.spriteList.size();
    }

    public void scheduleActions(SchedulableEntity entity, EventSchedule eventSchedule, WorldMap world){
        eventSchedule.scheduleEvent(eventSchedule, entity, createAnimationAction(0), this.animationPeriod);
    }
}


