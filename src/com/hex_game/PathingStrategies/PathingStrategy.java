package com.hex_game.PathingStrategies;

import com.hex_game.Entities.Point;
import com.hex_game.GameStates.WorldMap;
import java.util.List;

public interface PathingStrategy {

    List<Point> computePath(Point startPosition, Point endPosition,
                            boolean collision, WorldMap world);

}
