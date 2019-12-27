package com.hex_game.Events;

import com.hex_game.Actions.Action;
import com.hex_game.Entities.Target;

final class Event
{
    private final Action action;
    private final double time;
    private final Target target;

    public Event(Action action, double time, Target target)
    {
        this.action = action;
        this.time = time;
        this.target = target;
    }

    public Action getAction()
    {
        return action;
    }

    public double getTime()
    {
        return time;
    }

    public Target getTarget()
    {
        return target;
    }


}

