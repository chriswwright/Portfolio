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
final class MinerNotFull extends MovingEntity
{

    public MinerNotFull(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

    public boolean
    moveToNotFull(MinerNotFull miner, WorldModel world,
                  Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(miner.position, target.position))
        {
            miner.resourceCount += 1;
            world.removeEntity(world, target);
            eventSchedule.unscheduleAllEvents(eventSchedule, target);

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
    nextPositionMiner(MinerNotFull entity, WorldModel world, Point destPos)
    {
		AStarPathingStrategy a = new AStarPathingStrategy();
        List<Point> result = a.computePath(entity.getPosition(), destPos, 
			PathingFunctions.canPassThrough(world),  PathingFunctions.potentialNeighbors, PathingFunctions.stepsFromTo);

		return result.get(0);
    }



    public void
    scheduleActions(MinerNotFull entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {

        eventSchedule.scheduleEvent(eventSchedule, entity,
            entity.createActivityAction(entity, world),
			entity.actionPeriod);
		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createAnimationAction(0), entity.getAnimationPeriod());
        
    }


    public void
    executeActivity(MinerNotFull entity, WorldModel world,
                                EventSchedule eventSchedule)
    {
        Ore notFullTarget = (Ore)world.findNearest(entity);

        if (notFullTarget == null ||
            !entity.moveToNotFull(entity, world, notFullTarget, eventSchedule) ||
            !transformNotFull(entity, world, eventSchedule))
        {
            eventSchedule.scheduleEvent(eventSchedule, entity,
                entity.createActivityAction(entity, world),
                entity.actionPeriod);
        }
    }


    public boolean
    transformNotFull(MinerNotFull entity, WorldModel world,
                     EventSchedule eventSchedule)
    {
        if (entity.resourceCount >= entity.resourceLimit)
        {
            MinerFull miner = Entity.createMinerFull(entity.resourceLimit,
                entity.position, entity.actionPeriod, entity.animationPeriod);

            world.removeEntity(world, entity);
            eventSchedule.unscheduleAllEvents(eventSchedule, entity);

            world.addEntity(world, miner);
            miner.scheduleActions(miner, eventSchedule, world);

            return true;
        }

        return false;
    }




}
