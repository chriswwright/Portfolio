
/**
 * An action data structure records information about
 * an action that is to be performed on an entity.  It 
 * is attached to an Event data structure.
 */

final class ActivityAction implements Action
{
    private final Entity entity;
    private final WorldModel world;
    private final int repeatCount;	// A repeat count of 0 means to repeat forever

    public ActivityAction(Entity entity, WorldModel world,
		  int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.repeatCount = repeatCount;
    }



    public void executeAction(Action action, EventSchedule eventSchedule)
    {
		
        if (entity instanceof Obstacle){
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for OBSTACLE"));
		}else if(entity instanceof Blacksmith){
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for BLACKSMITH"));			
		}else{
			if (this.entity instanceof MinerFull){
				MinerFull ent = (MinerFull)this.entity;
		        ent.executeActivity(ent, this.world, eventSchedule);}
			else if(this.entity instanceof MinerNotFull){
				MinerNotFull ent = (MinerNotFull)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else if(this.entity instanceof OreBlob){
				OreBlob ent = (OreBlob)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else if(this.entity instanceof Vein){
				Vein ent = (Vein)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else if(this.entity instanceof Ore){
				Ore ent = (Ore)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else if(this.entity instanceof Quake){
				Quake ent = (Quake)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else if(this.entity instanceof Fire){
				Fire ent = (Fire)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else if(this.entity instanceof Meteor){
				Meteor ent = (Meteor)this.entity;
				ent.executeActivity(ent, this.world, eventSchedule);
			}
			else{
			}

		}
		
        
        
        
    

     
            

        
    }
}
