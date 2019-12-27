package com.hex_game.Entities;

import com.hex_game.Actions.Action;
import com.hex_game.Actions.AnimationAction;
import com.hex_game.Events.EventSchedule;
import com.hex_game.GameStates.WorldMap;
import com.hex_game.Stats.EntityStats;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovableEntity extends AnimatableEntity {
    private int maxMoveCount;
    private int direction;
    public Point target;
    public Point previous;
    private boolean moving;
    public List<Point> path;
    public List<Point> patha;
    public EntityStats stats;

    public MovableEntity(Point position, List<Image> spSet, boolean collision, int actionPeriod, int animationPeriod, WorldMap world, int owner, EntityStats stats) {
        super(position, spSet, collision, actionPeriod, animationPeriod, world, owner);
        this.direction = 0B0000; //up,left,down,right;
        this.moving = false;
        this.maxMoveCount = stats.getMoveCount();
        this.target = null;
        this.previous = position;
        this.path = null;
        this.patha = null;
        this.stats = stats;
    }


    public void scheduleActions(SchedulableEntity entity, EventSchedule eventSchedule, WorldMap world){
        eventSchedule.scheduleEvent(eventSchedule, entity, entity.createActivityAction(entity, world), entity.getActionPeriod());
    }

    public void scheduleAnimation(AnimatableEntity entity, EventSchedule eventSchedule, WorldMap world){
        eventSchedule.scheduleEvent(eventSchedule, entity, entity.createAnimationAction(0), entity.getActionPeriod());
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new AnimationAction(this, repeatCount);
    }

    public void move_to_next_position(Point new_position){
        this.position = new_position;
    }

    public int findDirection(Point nextPosition){
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

        if(position.getY()%2 != 0){
            if(positionList.get(0).equals(nextPosition)){
                return 0B1001;
            }
            else if(positionList.get(1).equals(nextPosition)){
                return 0B0001;
            }
            else if(positionList.get(2).equals(nextPosition)){
                return 0B0100;
            }
            else if(positionList.get(3).equals(nextPosition)){
                return 0B0011;
            }
            else if(positionList.get(4).equals(nextPosition)){
                return 0B1100;
            }
            else if(positionList.get(5).equals(nextPosition)){
                return 0B0110;
            }
            else{return 0B0000;}
        }else{
            if(positionList.get(0).equals(nextPosition)){
                return 0B1100;
            }
            else if(positionList.get(1).equals(nextPosition)){
                return 0B0001;
            }
            else if(positionList.get(2).equals(nextPosition)){
                return 0B0100;
            }
            else if(positionList.get(3).equals(nextPosition)){
                return 0B0110;
            }
            else if(positionList.get(4).equals(nextPosition)){
                return 0B1001;
            }
            else if(positionList.get(5).equals(nextPosition)){
                return 0B0011;
            }
            else{return 0B0000;}
        }

    }

    public int getDirection(){
        return direction;
    }

    public Point get_next_position() {

        List<Point> positionList = new ArrayList<>();
          if(position.getY()% 2 ==0){
              positionList.add(new Point(position.getX() + 1, position.getY() - 1));
              positionList.add(new Point(position.getX()+1, position.getY()+ 1));
              positionList.add(new Point(position.getX(), position.getY()+1));
              positionList.add(new Point(position.getX(), position.getY()-1));
          }
          else{
              positionList.add(new Point(position.getX(), position.getY()-1));
              positionList.add(new Point(position.getX(), position.getY()+1));
              positionList.add(new Point(position.getX()-1, position.getY()+1));
              positionList.add(new Point(position.getX()-1, position.getY()-1));
          }
         positionList.add(new Point(position.getX() + 1, position.getY()));
         positionList.add(new Point(position.getX() - 1, position.getY()));
         Collections.shuffle(positionList);
         for (Point nextPosition : positionList) {
            boolean occupied = world.isOccupied(nextPosition);
            boolean inBounds = world.withinBounds(nextPosition);
            if (!occupied && inBounds) {
                return nextPosition;
            }
        }
        return position;
    }
    public void setTarget(Point point){
        target = point;
    }

    public void clearPrevious(){
        previous = new Point(-1,-1);
    }

    public boolean isMoving(){
        return this.moving;
    }

    public int getMoveCount(){
        return stats.getMoveCount();
    }

    public void setMoveCount(int count){
        stats.setMoveCount(count);
    }

    public void setMoving(boolean set){
        this.moving = set;
    }
    public void resetMoveCount(){
        stats.setMoveCount(maxMoveCount);
    }

    public void clearPath(){
        path = null;
    }


    @Override
    public void executeActivity(SchedulableEntity entity, WorldMap world, EventSchedule eventSchedule) {
        if(patha == null) {
            Point nextPosition = get_next_position();
            direction = findDirection(nextPosition);
            if (!nextPosition.equals(position)) {
                moving = true;
            } else {
                moving = false;
            }
            previous = position;
            move_to_next_position(nextPosition);
        }

    }
}
