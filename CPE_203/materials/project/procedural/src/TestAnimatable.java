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


class TestAnimatable implements Animatable, Target
{
    private int animationPeriod;
	public int called;
	
	public TestAnimatable(int animationPeriod, int called){
	    this.animationPeriod = animationPeriod; 
		this.called = called;
	}
	
	public int getAnimationPeriod(){
		return this.animationPeriod;
	}

	public void nextImage (){
		
	}
    public Action createAnimationAction(int repeatCount){
		this.called += 1;
		return new AnimationAction(this, repeatCount);	   
	}

}
