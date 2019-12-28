/**
 * A timed event in the virtual world.  Events are queued, and then
 * executed when their time arrives.
 */
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
