package com.hex_game.Actions;
import com.hex_game.Entities.SchedulableEntity;
import com.hex_game.Events.*;
import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.state.BasicGameState;

final public class ActivityAction implements Action {
    private final SchedulableEntity entity;
    private final WorldMap world;
    private final int repeatCount;      // A repeat count of 0 means to repeat forever

    public ActivityAction(SchedulableEntity entity, WorldMap world,
                          int repeatCount) {
        this.entity = entity;
        this.world = world;
        this.repeatCount = repeatCount;
    }


    public void executeAction(EventSchedule eventSchedule) {

        entity.executeActivity(entity, this.world, eventSchedule);
    }
}