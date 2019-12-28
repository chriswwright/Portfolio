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
final class Vein extends SchedulableEntity
{


    public Vein(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

    

    public void
    scheduleActions(Vein entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {
		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createActivityAction(entity, world),
			entity.actionPeriod);

    }

    public void
    executeActivity(Vein entity, WorldModel world,
                        EventSchedule eventSchedule)
    {
        Point openPt = world.findOpenAround(world, entity.position);

        if (openPt != null) {
            Ore ore = Entity.createOre(openPt, 20000 + rand.nextInt(10000));
            world.addEntity(world, ore);
            ore.scheduleActions(ore, eventSchedule, world);
        }

        eventSchedule.scheduleEvent(eventSchedule, entity,
            entity.createActivityAction(entity, world),
            entity.actionPeriod);
    }


}
