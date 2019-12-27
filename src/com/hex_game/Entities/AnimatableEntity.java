package com.hex_game.Entities;

import com.hex_game.Events.EventSchedule;
import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import com.hex_game.Actions.*;
import java.util.List;

public class AnimatableEntity extends SchedulableEntity{

    private int animationPeriod;

    public AnimatableEntity(Point position, List<Image> spSet, boolean collision, int actionPeriod, int animationPeriod, WorldMap world, int owner){
        super(position, spSet, collision, actionPeriod, world, owner);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
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

    public void setSpriteList(List<Image> spSet, int spriteIndex){
        this.spriteIndex = spriteIndex;
        this.spriteList = spSet;
    }
}

