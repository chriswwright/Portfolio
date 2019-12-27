package com.hex_game.GameStates;

import com.hex_game.Entities.*;
import com.hex_game.Entities.Controled_Entities.ControlledTest;
import com.hex_game.Entities.NPC_Entities.NPC_Village;
import com.hex_game.Entities.NPC_Entities.TestEntity;
import com.hex_game.Events.EventSchedule;
import com.hex_game.Main.HexGame;
import com.hex_game.Stats.EntityStats;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WorldMap extends BasicGameState {

    //entity info
    public static EventSchedule eventSchedule;
    public List<Image> testImages = new ArrayList<Image>();
    public List<Image> spawnImages = new ArrayList<>();
    public List<Image> playerImages = new ArrayList<>();
    public List<Image> emptyImages = new ArrayList<>();
    public List<Image> pathImages = new ArrayList<>();
    private int entityHeight;
    private int entityWidth;

    private int entityIndex = 0;
    private int entityMoveCount = 0;

    //timers and sizing
    public static double timeScale = 1.0;
    private int zoom_in = 12;
    private int time = 16;
    private int zoom_out = 32;

    //end turn
    private Image endTurnImage;
    private Image endTurnPressedImage;
    private Image endTurnHilightImage;
    private boolean endTurnHover = false;
    private boolean turnEnded = false;

    private int endTurnWidth;
    private int endTurnHeight;
    private int endTurnX;
    private int endTurnY;


    //selected image
    private Image selectedImage;
    private boolean drawSelected = false;

    private int selectedWidth;
    private int selectedHeight;
    private int selectedX;
    private int selectedY;

    private Entity selectedEntity = null;
    private Entity targetEntity = null;

    //game map + other
    private TiledMap game_map;
    private Image mouse_img;
    private Image[][] image_map = new Image[zoom_in][zoom_in];

    private int mouse_x;
    private int mouse_y;
    private int mouse_width;
    private int mouse_height;
    private long timer = 0;
    private long endtimer = 0;
    private long movingTimer = 0;


    public ArrayList<Entity> entities = new ArrayList<Entity>();

    private int parity = 0;
    private boolean moving = false;
    private boolean entityMoving = false;
    private boolean playerEntityMoving = false;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private int tile_width;
    private int tile_height;

    private int tile_x = 0;
    private int tile_y = 0;

    private Input input;

    // Initialization Method
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        eventSchedule = new EventSchedule(timeScale);

        //selected image
        selectedImage = new Image("./res/Entities/selected.png");
        selectedWidth = selectedImage.getWidth()/8;
        selectedHeight = selectedImage.getHeight()/8;
        selectedX = 0;
        selectedY = HexGame.HEIGHT - selectedHeight;


        //End turn image
        endTurnImage = new Image("./res/Entities/endTurn.png");
        endTurnHilightImage = new Image("./res/Entities/endTurnHilight.png");
        endTurnPressedImage = new Image("./res/Entities/endTurnPressed.png");
        endTurnWidth = endTurnImage.getWidth()/4;
        endTurnHeight = endTurnImage.getHeight()/4;
        endTurnX = HexGame.WIDTH - endTurnWidth;
        endTurnY = HexGame.HEIGHT - endTurnHeight;


        //Player Test
        playerImages = Collections.unmodifiableList(loadImages("./res/Entities/playertest", 1));

        //Empty Images
        emptyImages = Collections.unmodifiableList(loadImages("./res/Entities/redPath", 1));

        //Path Images
        pathImages = Collections.unmodifiableList(loadImages("./res/Entities/bluePath", 1));
        //Test image
        testImages = Collections.unmodifiableList(loadImages("./res/Entities/testImage", 1));
        entityHeight = testImages.get(0).getHeight();
        entityWidth = testImages.get(0).getWidth();

        //Spawner image
        spawnImages = Collections.unmodifiableList(loadImages("./res/Entities/spawner", 1));

        // Input Listener
        input = gc.getInput();
        Mouse.setGrabbed(true);

        //Map
        game_map = new TiledMap("./res/SpriteMaps/TiledMap/test.tmx", "./res/SpriteMaps/TileSets");
        for (int i = tile_x; i != zoom_in; i++) {
            for (int ii = tile_y; ii != zoom_in; ii++) {
                image_map[i][ii] = game_map.getTileImage(i, ii, 1);
            }
        }


        tile_height = (int) HexGame.SCALE * (image_map[0][0].getHeight());
        tile_width = (int) HexGame.SCALE * (image_map[0][0].getHeight());

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

        //Adding Entities
        addInitial(new TestEntity(new Point(8, 8), testImages, true, this, 1));
        addInitial(new TestEntity(new Point(8, 7), testImages, true, this, 1));
        addInitial(new TestEntity(new Point(2, 2), testImages, true, this, 1));
        addInitial(new TestEntity(new Point(3,3), testImages, true, this,1));
        addInitial(new NPC_Village(new Point(9, 9), spawnImages, true, 10,  this, 1));
        addInitial(new NPC_Village(new Point(6, 9), spawnImages, true, 10,  this, 1));
        addInitial(new NPC_Village(new Point(12, 12), spawnImages, true, 10,  this, 1));
        addInitial(new ControlledTest(new Point(5,5), playerImages, true, 20, 10, this, 0, new EntityStats(1, 1, 1, 1, 1, 1, 9)));

        /*for (Entity startEntity : entities){
            if(startEntity instanceof SchedulableEntity){
               ((SchedulableEntity) startEntity).scheduleActions((SchedulableEntity) startEntity, eventSchedule, this);
           }
        }*/
    }
            // Rendering Method
            @Override
            public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {


                //Rendering the background
                g.setBackground(Color.decode("#AFEEEE"));
                for(int ii = 0; ii != zoom_in; ii++) {
                    for(int i = 0; i != zoom_in; i++) {
                if (moving) {

                    if(up) {
                        //up
                        if (right){

                            if (parity == 0) {
                                if (ii % 2 != 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                }
                            } else {
                                if (ii % 2 == 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                }
                            }

                        }
                        else if (left){

                            if (parity == 0) {
                                if (ii % 2 != 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                }
                            } else {
                                if (ii % 2 == 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                }
                            }

                        }
                        else{

                        if (parity == 0) {
                                if (ii % 2 != 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                }
                            } else {
                                if (ii % 2 == 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), tile_width * 2, tile_height * 2);
                                }
                        }
                        }
                    }

                    else if(down){
                           //down
                        if(right){

                            if (parity == 0) {
                                if (ii % 2 != 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                }
                            } else {
                                if (ii % 2 == 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                }
                            }
                        }
                        else if(left){

                            if (parity == 0) {
                                if (ii % 2 != 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                }
                            } else {
                                if (ii % 2 == 0) {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                } else {
                                    image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                                }
                            }
                        }
                        else{

                        if (parity == 0) {
                            if (ii % 2 != 0) {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                            } else {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                            }
                        } else {
                            if (ii % 2 == 0) {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                            } else {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), tile_width * 2, tile_height * 2);
                            }
                        }
                    }
                    }

                    else if(left) {
                            //left
                        if (parity == 0) {
                            if (ii % 2 != 0) {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            } else {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            }
                        } else {
                            if (ii % 2 == 0) {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            } else {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            }
                        }
                    }

                    else if(right) {
                            //right
                        if (parity == 0) {
                            if (ii % 2 != 0) {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            } else {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            }
                        } else {
                            if (ii % 2 == 0) {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            } else {
                                image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                            }
                        }

                    }


                } else {
                    if (parity == 0) {
                        if (ii % 2 != 0) {
                            image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                        } else {
                            image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                        }
                    } else {
                        if (ii % 2 == 0) {
                            image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                        } else {
                            image_map[i][ii].draw((i * (tile_width * 7) / 8 * 2 - (tile_width * 7) / 8) - (tile_width * 2), (ii * (tile_height)) - (tile_height * 2), tile_width * 2, tile_height * 2);
                        }
                    }

                }
            }
        }





        //render entities

        if(entities.size() > 0){
            for(int i = 0; i < entities.size(); i++){
                Entity entityToDraw = entities.get(i);
                Point position = entityToDraw.getPosition();
                Image imageToDraw = entityToDraw.getImage();
                int entityParity = position.getY()%2;
                int posY = position.getY();
                int posX = position.getX();
                if (moving) {

                    if(up) {
                        //up
                        if (right){
                            if (entityParity == 0) {
                                imageToDraw.draw(((posX-tile_x+1) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y-1) * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), entityWidth, entityHeight);
                            } else {
                                    imageToDraw.draw(((posX-tile_x+1) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24)-(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y-1) * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), entityWidth, entityHeight);
                            }

                        }
                        else if (left){

                            if (entityParity == 0) {
                                    imageToDraw.draw(((posX-tile_x-1) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y-1) * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), entityWidth, entityHeight);
                            } else {
                                    imageToDraw.draw(((posX-tile_x-1) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24)+(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y-1) * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), entityWidth, entityHeight);
                            }

                        }
                        else{

                            if (entityParity == 0) {
                                    imageToDraw.draw(((posX-tile_x) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2), ((posY-tile_y-1) * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), entityWidth, entityHeight);
                            } else {
                                    imageToDraw.draw(((posX-tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24), ((posY-tile_y-1) * (tile_height)) - (tile_height * 2) + (tile_height * (timer % time) / time), entityWidth, entityHeight);
                            }
                        }
                    }

                    else if(down){
                        //down
                        if(right){

                            if (entityParity == 0) {
                                    imageToDraw.draw(((posX-tile_x+1) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y+1) * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), entityWidth, entityHeight);
                            } else {
                                    imageToDraw.draw(((posX-tile_x+1) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24)-(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y+1) * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), entityWidth, entityHeight);
                            }
                        }
                        else if(left){

                            if (entityParity == 0) {
                                    imageToDraw.draw(((posX-tile_x-1) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y+1) * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), entityWidth, entityHeight);
                            } else {
                                    imageToDraw.draw(((posX-tile_x-1) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24)+(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y+1) * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), entityWidth, entityHeight);
                            }
                        }
                        else{

                            if (entityParity == 0) {
                                    imageToDraw.draw(((posX-tile_x) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2), ((posY-tile_y+1) * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), entityWidth, entityHeight);
                            } else {
                                    imageToDraw.draw(((posX-tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24), ((posY-tile_y+1) * (tile_height)) - (tile_height * 2)-(tile_height*(timer%time)/time), entityWidth, entityHeight);
                            }
                        }
                    }

                    else if(left) {
                        //left
                        if (entityParity == 0) {
                                imageToDraw.draw(((posX-tile_x-1) * (tile_width * 7) / 8 * 2 - (tile_width*12/11)) - (tile_width * 2)+(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        } else {
                                imageToDraw.draw(((posX - tile_x - 1) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24) + (tile_width * 7 / 8 * 2 * (timer % time) / time), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                                }
                    }

                    else if(right) {
                        //right
                        if (entityParity == 0) {
                                imageToDraw.draw(((posX-tile_x+1) * (tile_width * 7) / 8 * 2 - (tile_width*12/11 )) - (tile_width * 2)-(tile_width*7/8*2*(timer%time)/time), ((posY-tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                            }
                         else {
                            imageToDraw.draw(((posX - tile_x + 1) * (tile_width * 7) / 8 * 2) - (tile_width * 95/24) - (tile_width * 7 / 8 * 2 * (timer % time) / time), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        }

                    }


                } else if(!entityMoving || (entityToDraw!= entities.get(entityIndex))) {
                    if (entityParity == 0) {
                        imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width * 2), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                    } else {
                        imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95 / 24), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                    }
                }






                if(!moving && entityMoving && (entityToDraw == entities.get(entityIndex))){
                    int direction = ((MovableEntity) entityToDraw).getDirection();
                    if(direction == 0){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width * 2), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95 / 24), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        }
                    }
                    else if(direction == 1){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width*2*7/8)- (tile_width * 2)+(tile_width*2*7/8*movingTimer/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width*2*7/8)-(tile_width * 95 / 24)+(tile_width*2*7/8*movingTimer/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        }
                    }

                    else if(direction == 3){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width * 2) - (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) - (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95 / 24) - (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) - (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        }
                    }

                    else if(direction == 4){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) +(tile_width * 2 * 7 / 8) - (tile_width * 12 / 11) - (tile_width * 2)-(tile_width*2*7/8*movingTimer/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) + (tile_width * 2 * 7 / 8) - (tile_width * 95 / 24)-(tile_width*2*7/8*movingTimer/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2), entityWidth, entityHeight);
                        }
                    }

                    else if(direction == 6){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width * 2) + (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) - (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95 / 24) + (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) - (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        }
                    }

                    else if(direction == 9){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width * 2) - (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) + (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95 / 24) - (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) + (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        }

                    }
                    else if(direction == 12){
                        if (entityParity == 0) {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 12 / 11) - (tile_width * 2) + (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) + (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        } else {
                            imageToDraw.draw(((posX - tile_x) * (tile_width * 7) / 8 * 2) - (tile_width * 95 / 24) + (tile_width * 7 / 8 *(20-movingTimer)/20), ((posY - tile_y) * (tile_height)) - (tile_height * 2) + (tile_height*(20 - movingTimer)/20), entityWidth, entityHeight);
                        }

                    }
                }
            }
        }


        if(!turnEnded){
        if(!endTurnHover){
            endTurnImage.draw(endTurnX, endTurnY, endTurnWidth, endTurnHeight);}
        else{
            endTurnHilightImage.draw(endTurnX, endTurnY, endTurnWidth, endTurnHeight);
        }}
        else{
            endTurnPressedImage.draw(endTurnX, endTurnY, endTurnWidth, endTurnHeight);
        }

        if(drawSelected){
            selectedImage.draw(selectedX, selectedY, selectedWidth, selectedHeight);
        }

        mouse_img.draw(mouse_x, mouse_y, mouse_width, mouse_height);

    }


    // Update Method
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        eventSchedule.processEvents(eventSchedule, timer);

        // Menu Buttons
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //Mouse

        mouse_x = mouseX;
        mouse_y = mouseY;


        timer++;
        movingTimer++;
        endtimer++;
        if(timer % time == 0){
            moving = false;
            up = false;
            down = false;
            left = false;
            right = false;
        }

        if(!turnEnded && !playerEntityMoving){

        if(mouseX > endTurnX && mouseY > endTurnY){
            endTurnHover = true;
        }
        else{
            endTurnHover = false;
        }

       if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){

           if(mouseX >= endTurnX && mouseY >= endTurnY){
               turnEnded = true;
               entityIndex = 0;
               endtimer = 0;
           }
           else{
            selectedEntity = selectEntity(entities, mouse_x, mouse_y);
            if(selectedEntity != null && selectedEntity.getOwner() == 0){
                drawSelected = true;
            }
           else{
               selectedEntity = null;
               drawSelected = false;
            }}


       }

       if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
           Point targetToTest = decodePoint(mouseX, mouseY);


           if(drawSelected){
               if(selectEntity(entities, mouse_x, mouse_y) == null && withinBounds(targetToTest)){
                   targetEntity = null;
                   if(selectedEntity instanceof MovableEntity ){
                       ((MovableEntity) selectedEntity).setTarget(decodePoint(mouseX, mouseY));
                       ((MovableEntity) selectedEntity).clearPath();

                   }
                   playerEntityMoving = true;
                   entityIndex = entities.indexOf(selectedEntity);
               }
               else if(selectEntity(entities, mouse_x, mouse_y).getOwner() == 1){
                   if(selectedEntity instanceof MovableEntity ) {
                       targetEntity = selectEntity(entities, mouse_x, mouse_y);
                       if(Point.adjacent(targetEntity.getPosition(), selectedEntity.getPosition())){
                           removeEntity(targetEntity.getPosition());
                       }
                       else {
                           List<Point> openTargets = findOpenAround(this, targetEntity.getPosition());
                           if (openTargets != null) {
                               Point closest = openTargets.get(0);
                               for (Point testPoint : openTargets) {
                                   if (Point.distanceSquared(selectedEntity.getPosition(), testPoint) < Point.distanceSquared(selectedEntity.getPosition(), closest)) {
                                       closest = testPoint;
                                   }
                               }
                               ((MovableEntity) selectedEntity).setTarget(closest);
                               ((MovableEntity) selectedEntity).clearPath();


                               playerEntityMoving = true;
                               entityIndex = entities.indexOf(selectedEntity);
                           }

                       }
                   }


               }
           }
       }

        }
        if(!moving){
            parity = tile_y%2;
            for(int i = 0; i != (zoom_in); i++) {
                for(int ii = 0; ii != (zoom_in); ii++) {
                    image_map[i][ii] = game_map.getTileImage(i + tile_x, ii + tile_y, 1);
                }
            }}

        if(!turnEnded && !playerEntityMoving){
        if(input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)){
           if(tile_y != 0 && timer % time == 0){
               tile_y--;
               moving = true;
               up = true;
           }
        }
        if(input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP)){
            if(tile_y != 56 && timer % time == 0){
                tile_y++;
                moving = true;
                down = true;
            }
        }
        if(input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)){
            if(tile_x != 0 && timer % time == 0){
                tile_x--;
                moving = true;
                left = true;
            }
        }
        if(input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT)){
            if(tile_x != 52 && timer % time == 0){
                tile_x++;
                moving = true;
                right = true;
            }
        }}else if(turnEnded && endtimer >= 10){
            Entity entityToMove = entities.get(entityIndex);
            if(entityToMove instanceof MovableEntity && !entityMoving && entityToMove.getOwner() != 0) {
                entityMoveCount = ((MovableEntity) entityToMove).getMoveCount();
                if (entityMoveCount <= 0) {
                    entityIndex++;
                    entityMoving = false;
                    ((MovableEntity) entityToMove).resetMoveCount();
                } else {
                    if(tile_x + 9< entityToMove.getPosition().getX() || tile_x + 3 > entityToMove.getPosition().getX() || tile_y + 10< entityToMove.getPosition().getY()|| tile_y + 3 > entityToMove.getPosition().getY()+1){
                    tile_x = entityToMove.getPosition().getX() - 6;
                    tile_y = entityToMove.getPosition().getY() - 6;
                    if(tile_y <= 0){tile_y = 1;}
                    if(tile_x<= 0){tile_x = 1; }
                    }
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
            else if(entityToMove instanceof SchedulableEntity){
                ((SchedulableEntity) entityToMove).scheduleActions((SchedulableEntity) entityToMove, eventSchedule, this);
                entityIndex++; }else{
                entityIndex++;
            }
            if(entityIndex == entities.size()){
                entityIndex = 0;
                turnEnded = false;
                drawSelected = false;
            }


        }else if(playerEntityMoving){
        Entity entityToMove = selectedEntity;
        if(entityToMove instanceof MovableEntity && !entityMoving) {
        entityMoveCount = ((MovableEntity) entityToMove).getMoveCount();
        if (entityMoveCount <= 0 || entityToMove.getPosition().equals(((MovableEntity) entityToMove).target)) {
            if(targetEntity != null && Point.adjacent(entityToMove.getPosition(), targetEntity.getPosition())){
                removeEntity(targetEntity.getPosition());
        }
        playerEntityMoving = false;
        drawSelected = false;
        entityMoving = false;
        ((MovableEntity) entityToMove).resetMoveCount();
        } else {
        if(tile_x + 9< entityToMove.getPosition().getX() || tile_x + 3 > entityToMove.getPosition().getX() || tile_y + 10< entityToMove.getPosition().getY()|| tile_y + 3 > entityToMove.getPosition().getY()+1){
        tile_x = entityToMove.getPosition().getX() - 6;
        tile_y = entityToMove.getPosition().getY() - 6;
        if(tile_y <= 0){tile_y = 1;}
        if(tile_x<= 0){tile_x = 1; }
        }
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
            drawSelected = false;
            playerEntityMoving = false;
        }
        }


        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            System.exit(0);
        }

        //return to main menu
        if(input.isKeyPressed(Input.KEY_BACK)){
            sbg.enterState(HexGame.MAINMENU);
        }

        //debug for battle
        if(input.isKeyPressed(Input.KEY_B)){
            sbg.enterState(HexGame.BattleScreen);
        }

    }


        // Returning 'ID' from class 'MainMenu'

    public Entity selectEntity(List<Entity> entities, int position_x, int position_y){
        for(Entity ent: entities){
            if (ent.getPosition().equals(decodePoint(position_x, position_y))){
                    return ent;
            }
        }
        return null;


    }

    @Override
    public int getID() {
        return HexGame.WorldMap;
    }

    public static List<Image> loadImages(String fileBasename, int len) {
        List<Image> result = new ArrayList<Image>(len);
        if (len == 1) {
            result.add(getImageTile((fileBasename + ".png")));
        } else {
            for (int i = 1; i <= len; i++) {
                String name = fileBasename + i + ".png";
                result.add(getImageTile(name));
            }
        }
        return result;

    }

    public Point decodePoint(int xPos, int yPos){
        int tileYToReturn = (yPos+(9*tile_height/4))/tile_height+tile_y;
        int tileXToReturn;
        if(tileYToReturn%2 != 0){
           tileXToReturn = (xPos+(23*tile_width/8  ))/(tile_width*14/8) + tile_x;
        tileXToReturn++;
        }else{
           tileXToReturn = (xPos+(31 *tile_width/16))/(tile_width*14/8) + tile_x;
        tileXToReturn++;
        }

        return new Point(tileXToReturn, tileYToReturn);
    }

    private static Image getImageTile(String source){
        try{
            return new Image(source);
        }
        catch(SlickException e){
            return null;
        }
    }

    public void addEntity(Entity entityToAdd){
        entities.add(entityToAdd);
    }

    public void removeEntity(Point positionToRemove){
        int i = 0;
        for(Entity entityToRemove : entities){
            if(entityToRemove.getPosition().equals(positionToRemove)){
                entities.remove(i);
                break;
            }
            i++;
        }
    }

    public boolean isOccupied(Point positionToCheck){
        for(Entity currentEntity : entities){
            if(positionToCheck.equals(currentEntity.getPosition()) && currentEntity.getCollision()){
                return true;
            }
        }

        return false;

    }

    public void addInitial(Entity entity)
    {
        assert !isOccupied(entity.getPosition());
        addEntity(entity);
    }

    public List<Point> findOpenAround(WorldMap world, Point position) {
            ArrayList<Point> positionList = new ArrayList<Point>();
            List<Point> finalList = new ArrayList<>();
            positionList.add(new Point(position.getX(), position.getY()-1));
            positionList.add(new Point(position.getX()+1, position.getY()));
            positionList.add(new Point(position.getX()-1, position.getY()));
            positionList.add(new Point(position.getX(), position.getY()+1));
            if(position.getY()%2 != 0) {
                positionList.add(new Point(position.getX() - 1, position.getY() - 1));
                positionList.add(new Point(position.getX() - 1, position.getY() + 1));
            }else{
                positionList.add(new Point(position.getX()+1, position.getY()-1));
                positionList.add(new Point(position.getX()+1, position.getY()+1));
            }

            Collections.shuffle(positionList);
            for (Point positionToCheck : positionList){
                if (world.withinBounds(positionToCheck) &&
                        !world.isOccupied(positionToCheck) && !positionToCheck.equals(new Point(-1,-1)))
                {
                    finalList.add(positionToCheck);
                }
            }


            return finalList;
        }

    public boolean withinBounds(Point position){
        if(position.getY() >= game_map.getHeight() - 3 || position.getY() < 3 || position.getX() >= game_map.getWidth() - 3 || position.getX() < 3  ){
            return false;
        }
        return true;
    }

    public void clearAllEntities(){
        entities = new ArrayList<>();
    }



}








/*to do:
    add zoom out.
    then back to tiles.
*/