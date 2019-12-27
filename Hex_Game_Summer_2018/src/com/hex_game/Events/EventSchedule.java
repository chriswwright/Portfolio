package com.hex_game.Events;


import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import com.hex_game.Entities.Target;
import com.hex_game.Actions.Action;
import java.util.PriorityQueue;

/**
 * This data stucture is used to hold the schedule events that are queued
 * for later execution.
 */
final public class EventSchedule {

    private final PriorityQueue<Event> eventQueue;
    private final Map<Target, List<Event>> pendingEvents;
    private double currentTime;
    private final double timeScale;

    public EventSchedule(double timeScale)
    {
        this.eventQueue = new PriorityQueue<>(new EventTimeComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
        this.currentTime = 0.0;
    }

    public void
    scheduleEvent(EventSchedule eventSchedule, Object target, Action action,
                  long after) {
        Target outtarget = (Target) target;
        assert after >= 0;
        double time = this.currentTime +
                (after * this.timeScale);
        Event event = new Event(action, time, outtarget);

        this.eventQueue.add(event);

        // update list of pending events for the given target
        List<Event> pending = this.pendingEvents.get(outtarget);
        if (pending == null) {
            pending = new LinkedList<>();
            pendingEvents.put(outtarget, pending);
        }
        pending.add(event);
    }
    public void
    unscheduleAllEvents(EventSchedule eventSchedule, Target target)
    {
        List<Event> pending = this.pendingEvents.remove(target);

        if (pending != null)
        {
            for (Event event : pending)
            {
                this.eventQueue.remove(event);
            }
        }
    }

    public void
    removePendingEvent(EventSchedule eventSchedule, Event event)
    {
        List<Event> pending = this.pendingEvents.get(event.getTarget());

        if (pending != null)
        {
            pending.remove(event);
        }
    }
    public void processEvents(EventSchedule eventSchedule, double time)
    {
        while (!this.eventQueue.isEmpty() &&
                this.eventQueue.peek().getTime() <= time)
        {
            Event next = this.eventQueue.poll();
            assert this.currentTime <= next.getTime();
            this.currentTime = next.getTime();

            removePendingEvent(eventSchedule, next);

            next.getAction().executeAction(eventSchedule);
        }
        this.currentTime = time;
    }
}





