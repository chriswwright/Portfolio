import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Tile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Data structures that hold the model of our virtual world.
 * It consists of a grid.  Each point on the grid is occupied
 * by a background tile, and, optionally, an Entity.
 */
final class WorldModel
{
    public final Size size;
    public Tile background[][];
    private final Entity occupant[][];
    private final Set<Entity> entities;

    public Set<Entity> getEntities()
    {
        return entities;
    }    

    public WorldModel(Size gridSize)
    {
		this.size = gridSize;
        this.background = new Tile[gridSize.height][gridSize.width];
        this.occupant = new Entity[gridSize.height][gridSize.width];
        this.entities = new HashSet<Entity>();
    }


    public void setBackground(int Y, int X, Tile t)
    {
        this.background[Y][X] = t;
    }

    public void paint(WorldModel model, AnimationFrame frame, java.awt.Color color, Tile grass) {
        for (int y = 0; y < model.size.height; y++) {
            for (int x = 0; x < model.size.width; x++) {
				
                frame.addTile(x, y, model.background[y][x]);
                
                if(model.background[y][x] == grass){
						
                        frame.addTile(x, y, model.background[y][x]);
                        frame.addTile(x, y, AnimationAction.makeTile(color));
                    }
                
                Entity occupant = model.occupant[y][x];
                if (occupant != null) {
                    Tile tile = occupant.getCurrentTile(occupant);
                    frame.addTile(x, y, tile);
                }
            }
        }
    }

    public boolean withinBounds(WorldModel world, Point pos)
    {
        return pos.getY() >= 0 && pos.getY() < world.size.height &&
            pos.getX() >= 0 && pos.getX() < world.size.width;
    }


    public boolean isOccupied(WorldModel world, Point pos)
    {
        return this.withinBounds(world, pos) && world.getOccupantCell(world, pos) != null;
    }


    public Point findOpenAround(WorldModel world, Point pos)
    {
		ArrayList<Point> poslist = new ArrayList<Point>();
        poslist.add(new Point(pos.getX() - 1, pos.getY() - 1));
		poslist.add(new Point(pos.getX(), pos.getY()-1));
		poslist.add(new Point(pos.getX()+1, pos.getY()-1));
		poslist.add(new Point(pos.getX()+1, pos.getY()));
		poslist.add(new Point(pos.getX()+1, pos.getY()+1));
		poslist.add(new Point(pos.getX(), pos.getY()+1));
		poslist.add(new Point(pos.getX()-1, pos.getY()+1));
		poslist.add(new Point(pos.getX()-1, pos.getY()));
		for (Point posn : poslist){
                if (world.withinBounds(world, posn) &&
                    !world.isOccupied(world, posn) && !posn.equals(new Point(-1,-1)))
                {
                    return posn;
                }
            }
        

        return null;
    }

    public Point findOpenAroundFire(WorldModel world, Point pos)
    {
		ArrayList<Point> poslist = new ArrayList<Point>();
        poslist.add(new Point(pos.getX() - 1, pos.getY() - 1));
		poslist.add(new Point(pos.getX(), pos.getY()-1));
		poslist.add(new Point(pos.getX()+1, pos.getY()-1));
		poslist.add(new Point(pos.getX()+1, pos.getY()));
		poslist.add(new Point(pos.getX()+1, pos.getY()+1));
		poslist.add(new Point(pos.getX(), pos.getY()+1));
		poslist.add(new Point(pos.getX()-1, pos.getY()+1));
		poslist.add(new Point(pos.getX()-1, pos.getY()));
		for (Point posn : poslist){
                if (world.withinBounds(world, posn) &&
                    !world.isOccupied(world, posn) && !posn.equals(new Point(-1,-1)) && background[posn.getY()][posn.getX()] == VirtualWorld.grass)
                {
                    return posn;
                }
            }
        

        return null;
    }

    public Entity getOccupantCell(WorldModel world, Point pos)
    {
        return world.occupant[pos.getY()][pos.getX()];
    }

    public Entity getOccupant(WorldModel world, Point pos)
    {
        if (world.isOccupied(world, pos)) {
            return world.getOccupantCell(world, pos);
        } else {
            return null;
        }
    }

    public void   
    setOccupantCell(WorldModel world, Point pos, Entity entity)
    {
        world.occupant[pos.getY()][pos.getX()] = entity;
    }

	
	    public Entity findNearest(Entity ent)
    {
        List<Entity> ofType = new LinkedList<>();
		if (ent instanceof MinerFull){
			for (Entity entity : this.entities)
			{
				if (entity instanceof Blacksmith)
				{
					ofType.add(entity);
				}
			}
			Entity ret = this.nearestEntity(ofType, ent.getPosition());
			return (Blacksmith) ret;}
		else if (ent instanceof OreBlob){
			for (Entity entity : this.entities)
			{
				if (entity instanceof Vein)
				{
					ofType.add(entity);
				}
			}
			Entity ret = this.nearestEntity(ofType, ent.getPosition());
			return (Vein) ret;}
		else if (ent instanceof MinerNotFull){
			for (Entity entity : this.entities)
			{
				if (entity instanceof Ore)
				{
					ofType.add(entity);
				}
			}
			Entity ret = this.nearestEntity(ofType, ent.getPosition());
			return (Ore) ret;}
			Entity ret = this.nearestEntity(ofType, ent.getPosition());
			return (Ore) ret;
	}


    public Entity nearestEntity(List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return null;
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = Point.distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities)
            {
                int otherDistance = Point.distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return nearest;
        }
    }


    public void addEntity(WorldModel world, Entity entity)
    {
        if (world.withinBounds(world, entity.getPosition()))
        {
            world.setOccupantCell(world, entity.getPosition(), entity);
            world.entities.add(entity);
        }
    }

    public void removeEntity(WorldModel world, Entity entity)
    {
        world.removeEntityAt(world, entity.getPosition());
    }



    public void removeEntityAt(WorldModel world, Point pos)
    {
        if (world.withinBounds(world, pos)
            && world.getOccupantCell(world, pos) != null)
        {
            Entity entity = world.getOccupantCell(world, pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.setPosition(new Point(-1, -1));
            world.entities.remove(entity);
            world.setOccupantCell(world, pos, null);
        }
    }

    public void moveEntity(WorldModel world, MovingEntity entity, Point pos)
    {
        Point oldPos = entity.getPosition();
        if (world.withinBounds(world, pos) && !pos.equals(oldPos))
        {
            world.setOccupantCell(world, oldPos, null);
            world.removeEntityAt(world, pos);
            world.setOccupantCell(world, pos, entity);
            entity.setPosition(pos);
        }
    }



}
