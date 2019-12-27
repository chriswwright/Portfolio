package com.hex_game.Entities.Controled_Entities;

import com.hex_game.Entities.MovableEntity;
import com.hex_game.Entities.Point;
import com.hex_game.GameStates.WorldMap;
import com.hex_game.PathingStrategies.AStarPathingStrategy;
import com.hex_game.Stats.EntityStats;
import org.newdawn.slick.Image;

import java.util.List;

public class ControlledTest extends MovableEntity {

    public ControlledTest(Point position, List<Image> spSet, boolean collision, int actionPeriod, int animationPeriod, WorldMap world, int owner, EntityStats stats){
        super(position, spSet, collision, actionPeriod, animationPeriod, world, owner, stats);
    }


    @Override
    public Point get_next_position(){
        if(target == null || target.equals(position)){
            return position;
        }
        if(path == null || path.size()== 0){
        AStarPathingStrategy pathingStrategy = new AStarPathingStrategy();
        path = pathingStrategy.computePath(position, target, true, world);
        if(path == null){
            return position;
        }
        path.remove(0);
        Point retValue = path.get(0);
        path.remove(0);
        return retValue;}
        Point retValue = path.get(0);
        path.remove(0);
        return retValue;
        /*
        else{
            Point nearest = null;
            int length = -1;
            List<Point> positionList = new ArrayList<>();
            positionList.add(new Point(position.getX(), position.getY()-1));
            positionList.add(new Point(position.getX()+1, position.getY()));
            positionList.add(new Point(position.getX()-1, position.getY()));
            positionList.add(new Point(position.getX(), position.getY()+1));
            if(position.getY()%2 != 0) {
                positionList.add(new Point(position.getX() - 1, position.getY() - 1));
                positionList.add(new Point(position.getX() - 1, position.getY() + 1));
            }else{
                positionList.add(new Point(position.getX()+1, position.getY()-1));
                positionList.add(new Point(position.getX()+1, position.getY()+1));
            }
            for(Point pointToTest : positionList){
                if(!world.isOccupied(pointToTest)){
                    if(length == -1 && !pointToTest.equals(previous)){
                        length = Point.distanceSquared(pointToTest, target);
                        nearest = pointToTest;
                    }
                    else if(Point.distanceSquared(pointToTest, target) < length && !pointToTest.equals(previous)){
                        length = Point.distanceSquared(pointToTest,target);
                        nearest = pointToTest;
                    }

                }

            }
            if(nearest == null){
                return position;
            }
            return nearest;
        }*/
    }


}