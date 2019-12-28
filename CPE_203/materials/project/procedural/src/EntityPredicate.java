import java.util.function.Predicate;

class EntityPredicate {
	public static Predicate<Entity> isBlackSmith()
	{
		return p -> p instanceof Blacksmith;
	}
	
	public static Predicate<Entity> isOre()
	{
		return p -> p instanceof Blacksmith;
	}
	
	public static Predicate<Entity> isVein()
	{
		return p -> p instanceof Blacksmith;
	}
	


	
	// public static Predicate<Entity> type()
	// {

	// }
}	