import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

class AStarPathingStrategy implements PathingStrategy
{
	public AStarPathingStrategy(){}
    @Override
    public List<Point> computePath(
                            final Point start, final Point end,
                            Predicate<Point> canPassThrough,
                            Function<Point, List<Point>> potentialNeighbors,
                            ToIntBiFunction<Point, Point> stepsFromTo)
    {
        ArrayList<Point> result = new ArrayList<Point>();
		ArrayList<Node> open = new ArrayList<Node>();
		HashSet<Node> closed = new HashSet<Node>(9999);

		
		
		if (start.equals(end)){
			result.add(start);
			return result;
			}
        List<Point> neighbors = potentialNeighbors.apply(start);
 
		
		neighbors = potentialNeighbors.apply(end);
		int fail = 0;
		for (Point ob : neighbors){
			if (!canPassThrough.test(ob) && Point.distanceSquared(end, ob) == 1){
				fail += 1;
			}
		}
		if (fail == 4){
			result.add(start);
			return result;
		}
		Node startN = new Node(start);
		open.add(startN);
		while (open.size()!= 0){
			open.sort(new AStarComparators());
			Node active = open.get(0);
			open.remove(0);
			
			if (active.pos.equals(end)){
				return this.calculatePath(active);
			}
			
			closed.add(active);
			neighbors = potentialNeighbors.apply(active.pos);
			for (Point newpos : neighbors){
				//System.out.println(newpos + " s:" + start + " e:" + end + " a:" + active.pos);
				//System.out.println(open.size());
				if (newpos.equals(end) && Point.distanceSquared(newpos, active.pos) == 1){
					return this.calculatePath(active);
				}
				boolean inlist = false;
				if (canPassThrough.test(newpos)){
					for (Node a : closed){
						if (a.pos.getX() == newpos.getX() && a.pos.getY() == newpos.getY()){
						    inlist = true;
						}
					}
					if (inlist == false){
						for (Node a : open){
							if (a.pos.getX() == newpos.getX() && a.pos.getY() == newpos.getY()){
						    inlist = true;
							}
						}
						if (inlist == false){
							Node newnode = new Node(newpos);
							newnode.parent = active;
							newnode.calculateGValue(active);
							newnode.calculateHValue(end);
							
							open.add(newnode);
						}
						else if (open.size() > 0){

							Node node = open.get(open.indexOf(new Node(newpos)));
							if (node.g < active.g) {
							node.calculateGValue(active);
							active = node;
							}
						}
					}
				
				}
			}
			
		}
		result.add(start);
		return result;
	}

		
		
    private ArrayList<Point> calculatePath(Node end) {
        ArrayList<Point> path = new ArrayList<Point>();

        Node node = end;
        while (node.parent != null) {
			//VirtualWorld.addInitial(Entity.createObstacle(node.pos));
            path.add(0, node.pos);
            node = node.parent;
        }
        return path;
	}
    @Override
    public void setDebugGrid(DebugGrid grid) {
        // Nothing much interesting to see here, so we don't use it.
    }
	


	
	protected double costEstimate(Point a, Point b, int num){
		return Math.sqrt(Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
}

