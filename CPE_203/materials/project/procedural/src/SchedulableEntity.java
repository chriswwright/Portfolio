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
class SchedulableEntity extends Entity
{

    public SchedulableEntity(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
		super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

	    public void
    scheduleActions(OreBlob entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {

    }

    public Action createActivityAction(Entity entity, WorldModel world)
    {
        return new ActivityAction(entity, world, 0);
    }
	
    public void
    executeActivity(Vein entity, WorldModel world,
                        EventSchedule eventSchedule)
    {
	}

}
