
/**
 * An action data structure records information about
 * an action that is to be performed on an entity.  It 
 * is attached to an Event data structure.
 */

interface Action
{ 
    void executeAction(Action action, EventSchedule eventSchedule);
}
