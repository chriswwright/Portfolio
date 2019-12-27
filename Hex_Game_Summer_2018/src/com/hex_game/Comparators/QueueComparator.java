package com.hex_game.Comparators;

import com.hex_game.PathingStrategies.QueueItem;
import java.util.Comparator;

public class QueueComparator implements Comparator<QueueItem> {
    public int compare(QueueItem a, QueueItem b){
        return Integer.compare(a.getDistanceSquared(), b.getDistanceSquared());
    }

}
