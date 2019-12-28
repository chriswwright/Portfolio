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
final class Fire extends AnimatableEntity
{

	public int propagation;

    public Fire(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod, int propagation)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
		this.propagation = propagation;
    }

    public void
    scheduleActions(Fire entity,  EventSchedule eventSchedule,
                    WorldModel world)
    {
		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createActivityAction(entity, world),
			entity.actionPeriod);
		eventSchedule.scheduleEvent(eventSchedule, entity,
			entity.createAnimationAction(0),
			entity.getAnimationPeriod());
    }


    public void
    executeActivity(Fire entity, WorldModel world,
                         EventSchedule eventSchedule)
    {
		for (int i = 0; i <= 8; i++){
        Point openPt = world.findOpenAroundFire(world, entity.position);

        if (propagation != 0 && openPt != null && !entity.position.equals(new Point(-1,-1)) && (world.background[openPt.getY()][openPt.getX()] == VirtualWorld.grass)) {
            Fire fire = Entity.createFire(openPt, 0, propagation - 1);
            world.addEntity(world, fire);
			world.background[openPt.getY()][openPt.getX()] = VirtualWorld.fire;
            fire.scheduleActions(fire, eventSchedule, world);
        }
		else{
			eventSchedule.unscheduleAllEvents(eventSchedule, entity);
			world.removeEntity(world, entity);
		}
		}

        eventSchedule.scheduleEvent(eventSchedule, entity,
            entity.createActivityAction(entity, world),
            entity.actionPeriod);
    }

}
