import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import java.util.*;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Tile;


/**
 * A representation of a virtual world, containing various entities
 * that move around a grid.  The data structures representing the
 * current state of the virtual world are split out in a separate
 * model class, called WorldModel.
 */
public final class VirtualWorld
{
    private static final Size TILE_SIZE = new Size(32, 32);
    public static final Size WORLD_SIZE = new Size(40, 30);
    // Name, as decided by CSC 203 in Spring 2018:
    private static final String NAME = "Minecraft 2: Electric Boogaloo";
    private static final File IMAGE_DIR = new File("images");

    private static final String[] BACKGROUND = new String[] {
	"                   R                    ",
	"                    R                  R",
	" RR   RR   RR                           ",
	"R  R R  R R  R                          ",
	"   R R  R    R                          ",
	" RR  R  R  RR                           ",
	"R    R  R    R                          ",
	"R    R  R R  R                          ",
	"RRRR  RR   RR                           ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                    R                  R",
	"                   R                    ",
	"                    R                  R",
	"                   R                    ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                                        ",
	"                   R                    ",
	"                    R                   "
    };


    public static double timeScale;

	public static  List<Tile> blacksmithTiles1 = new ArrayList<Tile>();
    public static  List<Tile> blobTiles1 = new ArrayList<Tile>();
	public static  List<Tile> fireTiles1 = new ArrayList<Tile>();
    public static  List<Tile> meteorTiles1 = new ArrayList<Tile>();
    public static  List<Tile> minerTiles1 = new ArrayList<Tile>();
    public static  List<Tile> minerFullTiles1 = new ArrayList<Tile>();
    public static  List<Tile> obstacleTiles1 = new ArrayList<Tile>();
    public static  List<Tile> oreTiles1 = new ArrayList<Tile>();
    public static  List<Tile> quakeTiles1 = new ArrayList<Tile>();
    public static  List<Tile> veinTiles1 = new ArrayList<Tile>();
	public static final List<Tile> blacksmithTiles = Collections.unmodifiableList(loadImages("blacksmith", "B"));
    public static final List<Tile> blobTiles = Collections.unmodifiableList(loadImages("blob", "===*===*="));
	public static final List<Tile> fireTiles = Collections.unmodifiableList(loadImages("fire", "^-^-^-"));
    public static final List<Tile> meteorTiles = Collections.unmodifiableList(loadImages("meteor", "####+#"));
    public static final List<Tile> minerTiles = Collections.unmodifiableList(loadImages("miner", "mMmMm"));
    public static final List<Tile> minerFullTiles = Collections.unmodifiableList(loadImages("miner_full", "mM$mM"));
    public static final List<Tile> obstacleTiles = Collections.unmodifiableList(loadImages("obstacle", "O"));
    public static final List<Tile> oreTiles = Collections.unmodifiableList(loadImages("ore", "$"));
    public static final List<Tile> quakeTiles = Collections.unmodifiableList(loadImages("quake", "QqQqQq"));
    public static final List<Tile> veinTiles = Collections.unmodifiableList(loadImages("vein", "V"));



	
    public static WorldModel model;
    public static EventSchedule eventSchedule;
    public static SpriteWindow window;
	public static Tile rocks = getImageTile("rocks.png", '=');
	public static Tile grass = getImageTile("grass.png", '.');
	public static Tile fire = getImageTile("burned.png", '-');
    //public static List<Point> grasstiles = new ArrayList<Point>();

    public static Tile getImageTile(String imageFileName, char text) {
	Tile t = null;
	File f = new File(IMAGE_DIR, imageFileName);
	try {
	    t = new ImageTile(f, TILE_SIZE, text);
	} catch (IOException ex) {
	    System.out.println("Fatal error:  Image not found in " + f);
	    ex.printStackTrace();
	    System.exit(1);
	}
	return t;
    }

    public static void setup()
    {
	window = new SpriteWindow(NAME, WORLD_SIZE);
	window.setFps(30f);
	window.setTileSize(TILE_SIZE);
        System.out.println(NAME + ".  Press 'q' to quit.");
        window.setKeyTypedHandler((char ch) -> {
            if (ch == 'q' || ch == 'Q') {
                window.stop();
            }
        });

        model = new WorldModel(WORLD_SIZE);
		
		window.setMouseClickedHandler((int x, int y) -> {
			Point pos = new Point(x, y);
			if (model.withinBounds(model, pos)){
				if (model.isOccupied(model, pos)){
					eventSchedule.unscheduleAllEvents(eventSchedule, model.getOccupant(model, pos));
					model.removeEntity(model, model.getOccupant(model, pos));
				}
				Meteor meteor = Entity.createMeteor(pos, 0);
				model.addEntity(model, meteor);
				meteor.scheduleActions(meteor, eventSchedule, model);
				
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
					if (model.withinBounds(model, posn) && !posn.equals(new Point(-1,-1)))
					{
						if (!model.isOccupied(model, posn)){
							meteor = Entity.createMeteor(posn, 0);
							model.addEntity(model, meteor);
							meteor.scheduleActions(meteor, eventSchedule, model);
						}
						else
						{
							
							eventSchedule.unscheduleAllEvents(eventSchedule, model.getOccupant(model, posn));
							model.removeEntity(model, model.getOccupant(model, posn));
							Fire fire = Entity.createFire(posn, 0, 1);
							model.addEntity(model, fire);
							model.background[posn.getY()][posn.getX()] = VirtualWorld.fire;
							fire.scheduleActions(fire, eventSchedule, model);
						}
					}
			
				}
			}
		});
        eventSchedule = new EventSchedule(timeScale);
	setupBackground();
	//loadEntityImages();
        createInitialEntities();
        scheduleInitialActions(model, eventSchedule);
    }

    public static void setupBackground() {
	
	for (int y = 0; y < WORLD_SIZE.height; y++) {
	    for (int x = 0; x < WORLD_SIZE.width; x++) {
		char c = BACKGROUND[y].charAt(x);
		if (c == ' ') {
		    model.setBackground(y, x, grass);
            //grasstiles.add(new Point(x, y));
		} else if (c == 'R') {
		    model.setBackground(y, x, rocks);
		} else {
		    assert false;
		}
	    }
	}
    }

    public static void loadEntityImages() {
    meteorTiles1 = loadImages("meteor", "#####+##+#");
	fireTiles1 = loadImages("fire", "^-^-^-");
	blacksmithTiles1 = loadImages("blacksmith", "B");
	blobTiles1 = loadImages("blob", "===*===*=");
	minerTiles1 = loadImages("miner", "mMmMm");
    minerFullTiles1 = loadImages("miner_full", "mM$mM");
	obstacleTiles1 = loadImages("obstacle", "O");
	oreTiles1 = loadImages("ore", "$");
	quakeTiles1 = loadImages("quake", "QqQqQq");
	veinTiles1 = loadImages("vein", "V");
    }

    public static void createInitialEntities() {
	addInitial(Entity.createBlacksmith(new Point(0, 11)));
	addInitial(Entity.createBlacksmith(new Point(0, 29)));
	addInitial(Entity.createBlacksmith(new Point(19, 14)));
	addInitial(Entity.createBlacksmith(new Point(19, 29)));
	addInitial(Entity.createBlacksmith(new Point(20, 0)));
	addInitial(Entity.createBlacksmith(new Point(39, 0)));
	addInitial(Entity.createBlacksmith(new Point(39, 14)));
	addInitial(Entity.createBlacksmith(new Point(39, 29)));
	addInitial(Entity.createMinerNotFull(2, new Point(12,23), 954, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(17,22), 982, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(23,6), 777, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(24,26), 851, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(31,15), 933, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(31,26), 734, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(37,10), 600, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(37,18), 888, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(37,6), 991, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(5,6), 992, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(6,25), 930, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(6,3), 813, 100));
	addInitial(Entity.createMinerNotFull(2, new Point(7,13), 913, 100));
	addInitial(Entity.createObstacle(new Point(10, 23)));
	addInitial(Entity.createObstacle(new Point(10, 24)));
	addInitial(Entity.createObstacle(new Point(11, 21)));
	addInitial(Entity.createObstacle(new Point(11, 24)));
	addInitial(Entity.createObstacle(new Point(11, 25)));
	addInitial(Entity.createObstacle(new Point(12, 22)));
	addInitial(Entity.createObstacle(new Point(12, 25)));
	addInitial(Entity.createObstacle(new Point(12, 26)));
	addInitial(Entity.createObstacle(new Point(13, 22)));
	addInitial(Entity.createObstacle(new Point(13, 26)));
	addInitial(Entity.createObstacle(new Point(14, 23)));
	addInitial(Entity.createObstacle(new Point(14, 24)));
	addInitial(Entity.createObstacle(new Point(26, 26)));
	addInitial(Entity.createObstacle(new Point(27, 25)));
	addInitial(Entity.createObstacle(new Point(28, 19)));
	addInitial(Entity.createObstacle(new Point(28, 25)));
	addInitial(Entity.createObstacle(new Point(29, 20)));
	addInitial(Entity.createObstacle(new Point(29, 26)));
	addInitial(Entity.createObstacle(new Point(30, 21)));
	addInitial(Entity.createObstacle(new Point(31, 22)));
	addInitial(Entity.createObstacle(new Point(32, 23)));
	addInitial(Entity.createObstacle(new Point(5, 20)));
	addInitial(Entity.createObstacle(new Point(5, 21)));
	addInitial(Entity.createObstacle(new Point(6, 20)));
	addInitial(Entity.createObstacle(new Point(6, 21)));
	addInitial(Entity.createObstacle(new Point(7, 20)));
	addInitial(Entity.createObstacle(new Point(7, 21)));
	addInitial(Entity.createObstacle(new Point(8, 21)));
	addInitial(Entity.createObstacle(new Point(8, 22)));
	addInitial(Entity.createObstacle(new Point(9, 22)));
	addInitial(Entity.createObstacle(new Point(9, 23)));
	addInitial(Entity.createVein(new Point(10, 25), 8366));
	addInitial(Entity.createVein(new Point(14, 22), 8248));
	addInitial(Entity.createVein(new Point(21, 20), 9294));
	addInitial(Entity.createVein(new Point(27, 6), 9456));
	addInitial(Entity.createVein(new Point(28, 23), 13422));
	addInitial(Entity.createVein(new Point(33, 11), 10278));
	addInitial(Entity.createVein(new Point(33, 13), 10865));
	addInitial(Entity.createVein(new Point(33, 3), 11101));
	addInitial(Entity.createVein(new Point(34, 19), 11702));
	addInitial(Entity.createVein(new Point(6, 11), 15026));
	addInitial(Entity.createVein(new Point(7, 11), 9377));
	addInitial(Entity.createVein(new Point(8, 11), 13146));
    }

    public static void addInitial(Entity entity)
    {
        assert !model.isOccupied(model, entity.getPosition());
	model.addEntity(model, entity);
    }

    /**
     * Load a list of images for an entity.  text gives a series of
     * characters that serve as the animation for the text 
     * representation of the entity when in text mode.
     */
    public static List<Tile> loadImages(String fileBasename, String text) {
	int len = text.length();
	List<Tile> result = new ArrayList<Tile>(len);
	if (len == 1) {
	    result.add(getImageTile(fileBasename + ".png", text.charAt(0)));
	} else {
	    for (int i = 1; i <= len; i++) {
		String name = fileBasename + i + ".png";
		result.add(getImageTile(name, text.charAt(i - 1)));
	    }
	}
	return result;
	
    }

    public static void scheduleInitialActions(WorldModel model,
					      EventSchedule eventSchedule)
    {
        for (Entity entity : model.getEntities())
        {
			if (entity instanceof MinerFull){
				MinerFull ent = (MinerFull)entity;
			    ent.scheduleActions(ent, eventSchedule, model);}
			else if(entity instanceof MinerNotFull){
				MinerNotFull ent = (MinerNotFull)entity;
				ent.scheduleActions(ent, eventSchedule, model);
			}
			else if(entity instanceof OreBlob){
				OreBlob ent = (OreBlob)entity;
                ent.scheduleActions(ent, eventSchedule, model);
			}
			else if(entity instanceof Vein){
				Vein ent = (Vein)entity;
	            ent.scheduleActions(ent, eventSchedule, model);
			}
			else if(entity instanceof Ore){
				Ore ent = (Ore)entity;
	            ent.scheduleActions(ent, eventSchedule, model);
			}
			else if(entity instanceof Quake){
				Quake ent = (Quake)entity;
	            ent.scheduleActions(ent, eventSchedule, model);
			}
			else{
			}
        }
    }

    /**
     * Entry point to run the virtual world simulation.
     */
    public static void runSimulation() {
	setup();
    

	model.paint(model, window.getInitialFrame(), new java.awt.Color(0, 0, 0, 0), grass);
	window.start();
	    
        Lighting dncycle = new Lighting(window.getTimeSinceStart());
        java.awt.Color color = dncycle.getColor();
        while (true) {
	    AnimationFrame frame = window.waitForNextFrame();
	    if (frame == null) {
		break;
	    }
		int modTime = (int)Math.floor(window.getTimeSinceStart());
            if (modTime % 100 == 0) {
            dncycle.setTime(window.getTimeSinceStart());
            color = dncycle.getColor();
            }
	    eventSchedule.processEvents(eventSchedule, window.getTimeSinceStart());
  	    model.paint(model, frame, color, grass);
	    window.showNextFrame();
	}
    }
}
