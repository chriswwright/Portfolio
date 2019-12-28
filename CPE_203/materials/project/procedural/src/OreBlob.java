import java.util.LinkedList;
import java.util.ArrayList;
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
final class OreBlob extends MovingEntity
{


    public OreBlob(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }
	
    public boolean
    moveToOreBlob(OreBlob blob, WorldModel world,
                Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(blob.position, target.position))
        {
            world.removeEntity(world, target);
            eventSchedule.unscheduleAllEvents(eventSchedule, target);
            return true;
        }
        else
        {
            Point nextPos = blob.nextPositionOreBlob(blob, world, target.position);

            if (!blob.position.equals(nextPos))
            {
                Entity occupant = world.getOccupant(world, nextPos);
                if (occupant != null)
                {
                    eventSchedule.unscheduleAllEvents(eventSchedule, occupant);
                }

                world.moveEntity(world, blob, nextPos);
            }
            return false;
        }
    }


    public Point
    nextPositionOreBlob(OreBlob entity, WorldModel world, Point destPos)
    {
		AStarPathingStrategy a = new AStarPathingStrategy();
        List<Point> result = a.computePath(entity.getPosition(), destPos, 
			PathingFunctions.canPassThrough(world),  PathingFunctions.potentialNeighbors, PathingFunctions.stepsFromTo);

		return result.get(0);
   }


    public void
    scheduleActions(OreBlob entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {
            eventSchedule.scheduleEvent(eventSchedule, entity,
                entity.createActivityAction(entity, world),
                entity.actionPeriod);
            eventSchedule.scheduleEvent(eventSchedule, entity, entity.createAnimationAction(0),
                entity.getAnimationPeriod());

    }


    public void
    executeActivity(OreBlob entity, WorldModel world,
                           EventSchedule eventSchedule)			   
    {
        Vein blobTarget = (Vein)world.findNearest(entity);
        long nextPeriod = entity.actionPeriod;
		
        if (blobTarget != null)
        {
            Point tgtPos = blobTarget.position;

            if (entity.moveToOreBlob(entity, world, blobTarget, eventSchedule))
            {
                Quake quake = Entity.createQuake(tgtPos);
                world.addEntity(world, quake);
                nextPeriod += entity.actionPeriod;
                quake.scheduleActions(quake, eventSchedule, world);
            }
        }
		
        eventSchedule.scheduleEvent(eventSchedule, entity,
            entity.createActivityAction(entity, world),
            nextPeriod);
    }


}
