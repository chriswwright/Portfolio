package com.hex_game.Actions;

import com.hex_game.Entities.AnimatableEntity;
import com.hex_game.Events.*;

public class AnimationAction implements Action{

    private final AnimatableEntity entity;
    private final int repeatCount;

    public AnimationAction(AnimatableEntity entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }


    public void executeAction(EventSchedule eventSchedule){
        this.entity.nextImage();
        if(this.repeatCount != 1){
            eventSchedule.scheduleEvent(eventSchedule, this.entity, this.entity.createAnimationAction(Math.max(this.repeatCount-1, 0)), this.entity.getAnimationPeriod());

        }



    }

}
