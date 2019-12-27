package com.hex_game.PathingStrategies;

import com.hex_game.Entities.Point;

public class QueueItem {
    private Point position;
    private int distanceSquared;
    public QueueItem parent;

    public QueueItem(Point point, QueueItem parent) {
        this.position = point;
        this.distanceSquared = 0;
        this.parent = parent;
    }

    public void setDistanceSquared(Point target){
        distanceSquared =(int) (Math.pow(target.getX()- position.getX(), 2) + Math.pow(target.getY() - position.getY(), 2));
    }

    public int getDistanceSquared(){
        return distanceSquared;
    }


    public Point getPosition(){
        return position;
    }

    public boolean equals(Object other){
        if(other instanceof QueueItem){
            QueueItem op = (QueueItem) other;
            return position.getX() == op.position.getX() && position.getY() == op.position.getY();
        }
        else {
            return false;
        }
    }
}