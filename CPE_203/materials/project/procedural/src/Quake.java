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
final class Quake extends AnimatableEntity
{


    public Quake(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

    public void
    scheduleActions(Quake entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {
		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createActivityAction(entity, world),
			entity.actionPeriod);
		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createAnimationAction(10),
			entity.getAnimationPeriod());
    }


    public void
    executeActivity(Quake entity, WorldModel world,
                         EventSchedule eventSchedule)
    {
        eventSchedule.unscheduleAllEvents(eventSchedule, entity);
        world.removeEntity(world, entity);
    }

}
