package com.hex_game.PathingStrategies;

import com.hex_game.Comparators.QueueComparator;
import com.hex_game.Entities.Point;
import com.hex_game.GameStates.WorldMap;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class AStarPathingStrategy implements PathingStrategy{

    @Override
    public List<Point> computePath(Point startPosition, Point endPosition, boolean collision, WorldMap world) {
        QueueComparator comparator  = new QueueComparator();
        List<QueueItem> closed = new ArrayList<>();
        PriorityQueue<QueueItem> open = new PriorityQueue<>(10, comparator);
        QueueItem head = new QueueItem(startPosition, null);
        head.setDistanceSquared(endPosition);
        open.add(head);
            while(open.size() != 0){
            List<Point> positionList = new ArrayList<>();
                QueueItem currentItem = open.poll();
            Point position = currentItem.getPosition();
            boolean even = position.getY()%2 == 0;
            positionList.add(new Point(position.getX(), position.getY()-1));
            positionList.add(new Point(position.getX()+1, position.getY()));
            positionList.add(new Point(position.getX()-1, position.getY()));
            positionList.add(new Point(position.getX(), position.getY()+1));

            if(even){
                positionList.add(new Point(position.getX()+1, position.getY()-1));
                positionList.add(new Point(position.getX()+1, position.getY()+1));
            }else{
                positionList.add(new Point(position.getX() - 1, position.getY() - 1));
                positionList.add(new Point(position.getX() - 1, position.getY() + 1));
            }
            for(Point pointToAdd : positionList){
                QueueItem itemToAdd = new QueueItem(pointToAdd, currentItem);
                itemToAdd.setDistanceSquared(endPosition);
                if(pointToAdd.equals(endPosition)){
                    return calculatePath(itemToAdd);}
                else if(!world.isOccupied(pointToAdd) && !open.contains(itemToAdd) && !closed.contains(itemToAdd) && world.withinBounds(pointToAdd)){
                    open.add(itemToAdd);

                }
            }

            closed.add(currentItem);

        }

        return null;
    }

    public List<Point> calculatePath(QueueItem item){
        List<Point> path = new ArrayList<>();
        QueueItem current = item;
        while(current != null){
            path.add(current.getPosition());
            current = current.parent;
        }
        Collections.reverse(path);
        return path;

    }

}
