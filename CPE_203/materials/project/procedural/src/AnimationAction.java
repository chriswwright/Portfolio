import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.Size;
/**
 * An action data structure records information about
 * an action that is to be performed on an entity.  It 
 * is attached to an Event data structure.
 */

final class AnimationAction implements Action
{
   
    private final Animatable entity;
    private final int repeatCount;	// A repeat count of 0 means to repeat forever

    public AnimationAction(Animatable entity, int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }



    public void executeAction(Action action, EventSchedule eventSchedule)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1)
        {
            eventSchedule.scheduleEvent(eventSchedule, this.entity,
                this.entity.createAnimationAction(Math.max(this.repeatCount - 1, 0)),
                this.entity.getAnimationPeriod());
       }
    }
	
    public static Tile makeTile(java.awt.Color color)
    {
	    return new SolidColorTile(color, '.');
	}
	
}
