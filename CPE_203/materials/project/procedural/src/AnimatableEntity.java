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
class AnimatableEntity extends SchedulableEntity implements Animatable
{

    public AnimatableEntity(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
		super(position,
            tiles, resourceLimit, resourceCount,
            actionPeriod, animationPeriod);
    }

    public int getAnimationPeriod()
    {
        return this.animationPeriod;
    }
    
	public Action createAnimationAction(int repeatCount)
    {
        return new AnimationAction(this, repeatCount);
    }

	    public void nextImage()
    {
        this.tileIndex = (this.tileIndex + 1) % this.tiles.size();
    }

}
