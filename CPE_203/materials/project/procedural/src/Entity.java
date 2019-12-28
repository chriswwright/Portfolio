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
class Entity implements  Target 
{

    protected Point position;
    protected final List<Tile> tiles;
    protected int tileIndex;       // Index into tiles for animation
    protected final int resourceLimit;
    protected int resourceCount;
    protected final int actionPeriod;
    protected final int animationPeriod;

    public Entity(Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        
        this.position = position;
        this.tiles = tiles;
        this.tileIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }
    public static final Random rand = new Random();
    

    public Point getPosition()
        {
        return position;
        }
   
   public void setPosition(Point p)
        {
        this.position = p;
        }

    public Tile getCurrentTile(Entity entity)
    {
        return entity.tiles.get(entity.tileIndex);
    }


    public static Blacksmith createBlacksmith(Point position)
    {
        return new Blacksmith(position,
                          VirtualWorld.blacksmithTiles, 0, 0, 0, 0);
    }

    public static MinerFull
    createMinerFull(int resourceLimit, Point position,
                    int actionPeriod, int animationPeriod)
    {
        return new MinerFull(position,
                          VirtualWorld.minerFullTiles,
                          resourceLimit, resourceLimit, actionPeriod,
                          animationPeriod);
    }

    public static MinerNotFull
    createMinerNotFull(int resourceLimit, Point position, int actionPeriod,
                       int animationPeriod)
    {
        return new MinerNotFull(position,
                          VirtualWorld.minerTiles,
                          resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Obstacle
    createObstacle(Point position)
    {
        return new Obstacle(position,
                          VirtualWorld.obstacleTiles, 0, 0, 0, 0);
    }

    public static Fire
    createFire(Point position, int actionPeriod, int propagation)
    {
        return new Fire(position,
                          VirtualWorld.fireTiles, 0, 0, 2000, 500, propagation);
    }

    public static Meteor
    createMeteor(Point position, int actionPeriod)
    {
        return new Meteor(position,
                          VirtualWorld.meteorTiles, 0, 0, 5000, 500);
    }	
	
    public static Ore
    createOre(Point position, int actionPeriod)
    {
        return new Ore(position,
                          VirtualWorld.oreTiles, 0, 0, actionPeriod, 0);
    }

    public static OreBlob
    createOreBlob(Point position, int actionPeriod, int animationPeriod)
    {
        return new OreBlob(position,
                          VirtualWorld.blobTiles,
                          0, 0, actionPeriod, animationPeriod);
    }

    public static Quake createQuake(Point position)
    {
        return new Quake(position,
                          VirtualWorld.quakeTiles, 0, 0, 1100, 100);
    }

    public static Vein createVein(Point position, int actionPeriod)
    {
        return new Vein(position,
                          VirtualWorld.veinTiles, 0, 0, actionPeriod, 0);
    }

}
