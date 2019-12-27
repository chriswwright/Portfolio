package com.hex_game.Entities;

final public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean equals(Object other){
        if(other instanceof Point){
            Point op = (Point) other;
            return x == op.x && y == op.y;
        }
        else {
            return false;
        }
    }

    public static int distanceSquared(Point p1, Point p2){
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

        return (deltaX * deltaX) + (deltaY * deltaY);
    }

    public static boolean adjacent(Point p1, Point p2){
        return distanceSquared(p1, p2) < 4;
    }


}
