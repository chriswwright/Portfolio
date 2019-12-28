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
final class Ore extends SchedulableEntity
{
    

    public Ore(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

    public void
    scheduleActions(Ore entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {

		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createActivityAction(entity, world),
			entity.actionPeriod);

    }


    public void
    executeActivity(Ore entity, WorldModel world,
                       EventSchedule eventSchedule)
    {
        Point pos = entity.position;    // store current position before removing

        world.removeEntity(world, entity);
        eventSchedule.unscheduleAllEvents(eventSchedule, entity);

        OreBlob blob = Entity.createOreBlob(pos, entity.actionPeriod / 4,
                                    50 + rand.nextInt(100));

        world.addEntity(world, blob);
        blob.scheduleActions(blob, eventSchedule, world);
    }


}
