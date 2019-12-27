package com.hex_game.Entities;

import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import com.hex_game.Events.*;
import com.hex_game.Actions.*;


import java.util.List;


public class SchedulableEntity extends Entity{
    int actionPeriod;
    public SchedulableEntity(Point position, List<Image> spSet, boolean collision, int actionPeriod, WorldMap world, int owner){
        super(position, spSet, collision, world, owner);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod(){
        return actionPeriod;
    }

    public void scheduleActions(SchedulableEntity entity, EventSchedule eventSchedule, WorldMap world){
    }

    public Action createActivityAction(SchedulableEntity entity, WorldMap world){
        return new ActivityAction(entity, world, 0);
    }

    public void executeActivity(SchedulableEntity entity, WorldMap world, EventSchedule eventSchedule){
    }

}
