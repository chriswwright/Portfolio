package com.hex_game.Stats;

public class EntityStats {
    private int healthPoints;
    private int magicPoints;
    private int strength;
    private int speed;
    private int intelligence;
    private int defense;
    private int moveCount;
    public EntityStats(int healthPoints, int magicPoints, int strength, int speed, int intelligence, int defense, int moveCount){
        this.healthPoints = healthPoints;
        this.magicPoints = magicPoints;
        this.strength = strength;
        this.speed = speed;
        this.intelligence = intelligence;
        this.defense = defense;
        this.moveCount = moveCount;
    }

    public int getHealthPoints(){
        return healthPoints;
    }

    public int getMagicPoints(){
        return magicPoints;
    }

    public int getStrength(){
        return strength;
    }

    public int getSpeed(){
        return speed;
    }

    public int getIntelligence(){
        return intelligence;
    }

    public int getDefense(){
        return defense;
    }

    public int getMoveCount(){return moveCount;}

    public void setMoveCount(int count){moveCount = count;}

}
