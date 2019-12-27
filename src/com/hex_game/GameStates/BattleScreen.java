package com.hex_game.GameStates;

import com.hex_game.Entities.*;
import com.hex_game.Events.EventSchedule;
import com.hex_game.Main.HexGame;
import com.hex_game.Stats.EntityStats;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BattleScreen extends WorldMap {

    private int generationType = 0x000000;

    public int mapSize = 8;
    private ArrayList<TileEntity> battleMap = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();

    private List<Image> placeHolderTileImages= new ArrayList<>();
    private List<Image> heroSpriteImages = new ArrayList<>();
    private List<Image> archerSpriteImages = new ArrayList<>();
    private List<Image> militaSpriteImages = new ArrayList<>();
    private List<Image> wolfGruntImages = new ArrayList<>();

    private Image referenceSizeImage;

    private Image mouse_img;
    private Image selectedImage;

    private int selectedWidth;
    private int selectedHeight;
    private int selectedX;
    private int selectedY;
    private int entityMoveCount;

    private int mouse_x;
    private int mouse_y;
    private int mouse_width;
    private int mouse_height;

    private int tileHeight;
    private int tileWidth;
    private int tileRefHeight;
    private int tileRefWidth;
    private int tileX;
    private int tileY;
    private int entityHeight;
    private int entityWidth;
    private int movingTimer;

    private long timer = 0;


    private Input input;

    private EventSchedule eventSchedule;
    private boolean entityMoving;
    private boolean entityMoveOrder;
    private boolean selected;
    private Entity selectedEntity;
    private Entity targetEntity;
    private Point targetPosition;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        eventSchedule = new EventSchedule(1);

        selected = false;
        movingTimer = 0;

        selectedEntity = null;
        targetEntity = null;
        targetPosition = null;

        //selected image
        selectedImage = new Image("./res/Entities/selected.png");
        selectedWidth = selectedImage.getWidth()/8;
        selectedHeight = selectedImage.getHeight()/8;
        selectedX = 0;
        selectedY = HexGame.HEIGHT - selectedHeight;

        //PlaceHolderTileImage
        referenceSizeImage = new Image("./res/Entities/placeholderTileEntity/placeholder_a.png");
        heroSpriteImages = Collections.unmodifiableList(WorldMap.loadImages("./res/Entities/heroEntity/heroArmy_idle_", 8));
        archerSpriteImages = Collections.unmodifiableList(WorldMap.loadImages("./res/Entities/heroEntity/ArcherArmy_idle_", 8));
        militaSpriteImages = Collections.unmodifiableList(WorldMap.loadImages("./res/Entities/heroEntity/militaPikeman_idle_", 8));
        wolfGruntImages = Collections.unmodifiableList(WorldMap.loadImages("./res/Entities/wolfKinEntity/wolfKinGrunt_idle_", 8));
        placeHolderTileImages = Collections.unmodifiableList(WorldMap.loadImages("./res/Entities/forestTileEntity/forest_battle", 3));
        tileRefHeight = referenceSizeImage.getHeight();
        tileRefWidth = referenceSizeImage.getWidth();
        tileHeight = placeHolderTileImages.get(0).getHeight() ;
        tileWidth = placeHolderTileImages.get(0).getWidth() ;
        tileX = HexGame.WIDTH / 2 - tileWidth / 2;
        tileY = HexGame.HEIGHT / 2 - tileHeight / 2;
        entityHeight = heroSpriteImages.get(0).getHeight();
        entityWidth = heroSpriteImages.get(0).getWidth();



        //generate first map
        generateMap(generationType, this);

        // Input Listener
        input = gameContainer.getInput();
        Mouse.setGrabbed(true);

        // Menu Buttons
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //Mouse
        mouse_img = new Image("./res/menus/mouse.png");
        mouse_img.setFilter(Image.FILTER_NEAREST);

        mouse_width = (int) HexGame.SCALE * (mouse_img.getWidth() / 2);
        mouse_height = (int) HexGame.SCALE * (mouse_img.getHeight() / 2);

        mouse_x = mouseX;
        mouse_y = mouseY;

        MovableEntity test = new MovableEntity(new Point(2,2), heroSpriteImages, true, 10, 40, this, 0, new EntityStats(1,1,1,1,1,1,1));
        test.scheduleAnimation(test, eventSchedule, this);
        entities.add(test);
        MovableEntity testA = new MovableEntity(new Point(3,3), militaSpriteImages, true, 10, 40, this, 0, new EntityStats(1,1,1,1,1,1,1));
        testA.scheduleAnimation(testA, eventSchedule, this);
        entities.add(testA);
        MovableEntity testB = new MovableEntity(new Point(2,3), archerSpriteImages, true, 10, 40, this, 0, new EntityStats(1,1,1,1,1,1,1));
        testB.scheduleAnimation(testB, eventSchedule, this);
        entities.add(testB);
        MovableEntity testC = new MovableEntity(new Point(4,4), wolfGruntImages, true, 10, 40, this, 1, new EntityStats(1,1,1,1,1,1,1));
        testC.scheduleAnimation(testC, eventSchedule, this);
        entities.add(testC);

    }


    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setBackground(Color.decode("#000000"));
        int i = 0;
        for(TileEntity tileEntityToDraw : battleMap){
            Point pos = tileEntityToDraw.getPosition();
            if(pos.getY() % 2 != 0){
                tileEntityToDraw.getImage().draw(((pos.getX()+2) * (tileWidth * 7) / 8) - (tileWidth), ((pos.getY()+2) * (tileRefHeight*87/160)) - (tileHeight*7/6), tileWidth, tileHeight);
            }
            else{
                tileEntityToDraw.getImage().draw(((pos.getX()+2) * (tileWidth * 7) / 8 - (tileWidth * 7) / 16) - (tileWidth), ((pos.getY()+2) * (tileRefHeight *87/160)) - (tileHeight*7/6), tileWidth, tileHeight);
            }
        }




        for(Entity entityToDraw: entities){
            Point pos = entityToDraw.getPosition();
            if(pos.getY() % 2 != 0){
                entityToDraw.getImage().draw(((pos.getX()+2) * (tileWidth * 7) / 8) - (tileWidth*11/16), ((pos.getY()+2) * (tileRefHeight*87/160)) - (tileHeight*37/48), entityToDraw.getImage().getWidth(), entityToDraw.getImage().getHeight());
            }
            else{
                entityToDraw.getImage().draw((pos.getX()+2) * (tileWidth * 7) / 8 - (tileWidth * 7 / 16) - (tileWidth*11/16), ((pos.getY()+2) * (tileRefHeight *87/160)) - (tileHeight*37/48), entityToDraw.getImage().getWidth(), entityToDraw.getImage().getHeight());
            }
        }
        if(selected){
            selectedImage.draw(selectedX, selectedY, selectedWidth, selectedHeight);
        }

        mouse_img.draw(mouse_x, mouse_y, mouse_width, mouse_height);


    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        eventSchedule.processEvents(eventSchedule, timer);

        timer++;
        movingTimer++;

        // Menu Buttons
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //Mouse

        mouse_x = mouseX;
        mouse_y = mouseY;


        if(!entityMoveOrder){
        //left click
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            TileEntity tileToTarget;
            tileToTarget = selectTile(battleMap, mouse_x, mouse_y);
            if(tileToTarget != null) {
                System.out.println(tileToTarget.getPosition().getX() + "   " + tileToTarget.getPosition().getY()            );
                selectedEntity = selectEntity(entities, tileToTarget.getPosition());
                if (selectedEntity != null && selectedEntity.getOwner() == 0) {
                    selected = true;
                }else{
                    selectedEntity = null;
                    selected = false;
                }

            }
        }

        if(selected){
            //right click
            if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
                TileEntity tileToTarget;
                tileToTarget = selectTile(battleMap, mouse_x, mouse_y);
                if (tileToTarget != null) {
                    targetEntity = selectEntity(entities, tileToTarget.getPosition());
                    if (targetEntity != null) {
                        List<Point> openTargets = findOpenAround(this, targetEntity.getPosition());
                        if (openTargets != null) {
                            Point closest = openTargets.get(0);
                            for (Point testPoint : openTargets) {
                                if (Point.distanceSquared(selectedEntity.getPosition(), testPoint) < Point.distanceSquared(selectedEntity.getPosition(), closest)) {
                                    closest = testPoint;
                                }
                            }
                        }
                    } else {
                        targetPosition = tileToTarget.getPosition();
                        if (selectedEntity instanceof MovableEntity) {
                            ((MovableEntity) selectedEntity).setTarget(targetPosition);
                            ((MovableEntity) selectedEntity).clearPath();
                            entityMoveOrder = true;
                        }
                        selected = false;
                    }
                } else {
                    targetPosition = tileToTarget.getPosition();
                }
            }




            }
        }

        if(entityMoveOrder){
            System.out.println("test");
            Entity entityToMove = selectedEntity;
            if(entityToMove instanceof MovableEntity && !entityMoving) {
                entityMoveCount = ((MovableEntity) entityToMove).getMoveCount();
                if (entityMoveCount <= 0 || entityToMove.getPosition().equals(((MovableEntity) entityToMove).target)) {
                    if(targetEntity != null && Point.adjacent(entityToMove.getPosition(), targetEntity.getPosition())){
                        /*combat animation here */
                    }
                    entityMoveOrder = false;
                    selected = false;
                    entityMoving = false;
                    ((MovableEntity) entityToMove).resetMoveCount();
                } else {
                    ((MovableEntity) entityToMove).executeActivity((MovableEntity) entityToMove, this, eventSchedule);
                    movingTimer = 0;
                    entityMoveCount--;
                    ((MovableEntity) entityToMove).setMoveCount(entityMoveCount);
                    entityMoving = true;

                }
            }else if(entityMoving){
                if(movingTimer % 20 == 0){
                    entityMoving = false;
                }
            }
            else{
                selected = false;
                entityMoveOrder = false;
            }

        }



        //return to main menu
        if(input.isKeyPressed(Input.KEY_BACK)){
            stateBasedGame.enterState(HexGame.MAINMENU);
        }

        //exit
        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            System.exit(0);
        }

    }

    @Override
    public int getID() {
        return HexGame.BattleScreen;
    }

    public void setGenerationType(int value){
        this.generationType = value;
    }

    public void generateMap(int generationType, BattleScreen battleScreen) {
        int i;
        int ii;

        Point center = new Point(3, 3);
        for (ii = 0; ii < mapSize; ii++) {
            for (i = 0; i < mapSize; i++) {
                if (generationType == 0x000000) {
                    Point pointToTest = new Point(i, ii);
                    if (Point.distanceSquared(pointToTest, center) <= 5 && !pointToTest.equals(new Point(1, 2)) && !pointToTest.equals(new Point(1, 4))) {
                        TileEntity entityToAdd = new TileEntity(new Point(i, ii), placeHolderTileImages, false, 0, 30, battleScreen, 2, 1);
                        entityToAdd.scheduleActions(entityToAdd, eventSchedule, battleScreen);
                        battleMap.add(entityToAdd);
                    }
                }

            }
        }
    }
    public Entity selectEntity(List<Entity>  entities, int positionX, int positionY){
            for(Entity ent: entities){
                if (ent.getPosition().equals(decodePoint(positionX, positionY))){
                    return ent;
                }
            }
            return null;

    }

    public Entity selectEntity(List<Entity> entities, Point position){
        for(Entity entityToTest: entities){
            if(entityToTest.getPosition().equals(position)){
                return entityToTest;
            }
        }
        return null;
    }

    public TileEntity selectTile(ArrayList<TileEntity> tileMap, int positionX, int positionY){
        for(TileEntity tile : tileMap){
            Point pos = tile.getPosition();
            if(pos.getY() % 2 != 0){
                if(positionX >= ((pos.getX()+2) * (tileWidth * 7) / 8) - (tileWidth) && positionX <= ((pos.getX()+2) * (tileWidth * 7) / 8) - (tileWidth) + tileWidth && positionY >= ((pos.getY()+2) * (tileRefHeight*87/160)) - (tileHeight*15/12) && positionY <= ((pos.getY()+2) * (tileRefHeight*87/160)) - (tileHeight*15/12) + tileRefHeight) {
                    return tile;
                }
            }else{
                if(positionX >= ((pos.getX()+2) * (tileWidth * 7) / 8 - (tileWidth * 7) / 16) - (tileWidth) && positionX <= ((pos.getX()+2) * (tileWidth * 7) / 8 - (tileWidth * 7) / 16) - (tileWidth) + tileRefWidth && positionY >= ((pos.getY()+2) * (tileRefHeight *87/160)) - (tileHeight*15/12) && positionY <= ((pos.getY()+2) * (tileRefHeight *87/160)) - (tileHeight*15/12) + tileRefHeight){
                    return tile;
                }
            }

        }
        return null;
    }


    public List<Point> findOpenAround(WorldMap world, Point position){
        return null;
    }

    public boolean isOccupied(Point positionToCheck){
        for(Entity currentEntity : entities){
            if(positionToCheck.equals(currentEntity.getPosition()) && currentEntity.getCollision()){
                return true;
            }
        }


        return false;

    }

    public boolean withinBounds(Point position){
        for(TileEntity tile : battleMap){
            if(tile.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }


}

/*to do list
* Add a queue based on initiative
* Feed through the queue. Highlight active unit.
* Add movement animations
* Finish forest generation
* Add height
* */