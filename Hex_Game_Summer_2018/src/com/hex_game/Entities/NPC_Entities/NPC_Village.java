package com.hex_game.Entities.NPC_Entities;

import com.hex_game.Entities.Entity;
import com.hex_game.Entities.MovableEntity;
import com.hex_game.Entities.Point;
import com.hex_game.Entities.SchedulableEntity;
import com.hex_game.Events.EventSchedule;
import com.hex_game.GameStates.WorldMap;
import org.newdawn.slick.Image;

import java.util.List;

public class NPC_Village extends SchedulableEntity {
    public NPC_Village(Point position, List<Image> spSet, boolean collision, int actionPeriod, WorldMap world, int owner){
        super(position, spSet, collision, actionPeriod, world, owner);
    }


    public void scheduleActions(SchedulableEntity entity, EventSchedule eventSchedule, WorldMap world){
        eventSchedule.scheduleEvent(eventSchedule, entity, entity.createActivityAction(entity, world), entity.getActionPeriod());
    }

    public void executeActivity(SchedulableEntity entity, WorldMap world, EventSchedule schedule) {
        List<Point> openAround = world.findOpenAround(world, entity.getPosition());
        Point openPoint = null;
        if(openAround.size() != 0){
            openPoint = openAround.get(0);
        }



        if (openPoint != null) {
            TestEntity testEntity = new TestEntity(openPoint, world.testImages, true, world, 1);
            world.addEntity(testEntity);
        }

        //schedule.scheduleEvent(schedule, entity, entity.createActivityAction(entity, world), entity.getActionPeriod());
    }

}


