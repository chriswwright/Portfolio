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
final class Meteor extends AnimatableEntity
{


    public Meteor(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

    public void
    scheduleActions(Meteor entity,  EventSchedule eventSchedule,
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
    executeActivity(Meteor entity, WorldModel world,
                         EventSchedule eventSchedule)
    {
		for (int i = 0; i <= 8; i++){
        Point openPt = world.findOpenAroundFire(world, entity.position);

        if (openPt != null && (world.background[openPt.getY()][openPt.getX()] == VirtualWorld.grass)) {
            Fire fire = Entity.createFire(openPt, 0, 1);
            world.addEntity(world, fire);
			world.background[openPt.getY()][openPt.getX()] = VirtualWorld.fire;
            fire.scheduleActions(fire, eventSchedule, world);
        }}

        eventSchedule.scheduleEvent(eventSchedule, entity,
            entity.createActivityAction(entity, world),
            entity.actionPeriod);
    }

}
