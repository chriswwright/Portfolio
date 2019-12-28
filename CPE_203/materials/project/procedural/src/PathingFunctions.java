import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;
import java.util.List;
import java.util.ArrayList;

public class PathingFunctions{
	    
		public static Predicate<Point> canPassThrough(WorldModel world) {
			return (Point p) -> !world.isOccupied(world, p) && p.getY() >= 0 && p.getX() >= 0 && p.getY() < world.size.height && p.getX() < world.size.width;
		}
		
		
		public static ToIntBiFunction<Point, Point> stepsFromTo =
			(Point a, Point b) -> Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
		
		
		public static Function<Point, List<Point>> potentialNeighbors = 
			(Point a) -> {List<Point> points = new ArrayList<Point>();
			points.add(new Point(a.getX() + 1, a.getY()));
			points.add(new Point(a.getX(), a.getY()+1));
			points.add(new Point(a.getX() - 1, a.getY()));
			points.add(new Point(a.getX(), a.getY()-1));
			points.add(new Point(a.getX() + 1, a.getY() + 1));
			points.add(new Point(a.getX() - 1, a.getY() + 1));
			points.add(new Point(a.getX() - 1, a.getY() - 1));
			points.add(new Point(a.getX() + 1, a.getY() - 1));
			return points;};
				
				
			
}
