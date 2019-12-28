import java.util.LinkedList;
import java.util.List;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.Tile;
import java.util.Random;
import java.util.Map;


/**
 * An entity in our virtual world.  An entity occupies a square
 * on the grid.  It might move around, and interact with other
 * entities in the world.
 */
final class MinerFull extends MovingEntity
{

    public MinerFull(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }
   
    
    public boolean
    moveToFull(MinerFull miner, WorldModel world,
               Blacksmith target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(miner.position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = miner.nextPositionMiner(miner, world, target.position);

            if (!miner.position.equals(nextPos))
            {
                world.moveEntity(world, miner, nextPos);
            }
            return false;
        }
    }


    public Point
    nextPositionMiner(MinerFull entity, WorldModel world, Point destPos)
    {
		AStarPathingStrategy a = new AStarPathingStrategy();
        List<Point> result = a.computePath(entity.getPosition(), destPos, 
			PathingFunctions.canPassThrough(world),  PathingFunctions.potentialNeighbors, PathingFunctions.stepsFromTo);

		return result.get(0);
    }



    public void
    scheduleActions(MinerFull entity,  EventSchedule eventSchedule,
                    WorldModel world)
	{
            eventSchedule.scheduleEvent(eventSchedule, entity,
                entity.createActivityAction(entity, world),
                entity.actionPeriod);
            eventSchedule.scheduleEvent(eventSchedule, entity, entity.createAnimationAction(0),
                entity.getAnimationPeriod());

    }

    public void
    executeActivity(MinerFull entity, WorldModel world,
                             EventSchedule eventSchedule)
    {
        Blacksmith fullTarget
            = (Blacksmith) world.findNearest(entity);
        if (fullTarget != null  &&
            entity.moveToFull(entity, world, fullTarget, eventSchedule))
        {
            transformFull((MinerFull)entity, world, eventSchedule);
        }
        else
        {
            eventSchedule.scheduleEvent(eventSchedule, entity,
                entity.createActivityAction(entity, world),
                entity.actionPeriod);
        }
    }


    public void
    transformFull(MinerFull entity, WorldModel world, EventSchedule eventSchedule)
    {
        MinerNotFull miner = Entity.createMinerNotFull(entity.resourceLimit,
            entity.position, entity.actionPeriod, entity.animationPeriod);

        world.removeEntity(world, entity);
        eventSchedule.unscheduleAllEvents(eventSchedule, entity);

        world.addEntity(world, miner);
        miner.scheduleActions(miner, eventSchedule, world);
    }

}
