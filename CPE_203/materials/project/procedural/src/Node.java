import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

	public class Node {
		
		public List<Node> neighbors = new ArrayList<Node>();
		public Node parent;
		public double g;
		public double h;
		public Point pos;
		public int cost;
		
		public Node(Point pos){
			this.pos = pos;
			this.g = 0.0;
			this.h = 0.0;
			this.parent = null;
			this.cost = 1;
			
		}
		
		public void calculateGValue(Node point){
			this.g = point.g + 1.0;
		}
		
		public void calculateHValue(Point end){
			this.h = Math.sqrt(Math.pow(pos.getX()-end.getX(),2) + Math.pow(pos.getY() - end.getY(),2));
		}
		
		public double getFValue() {
			return this.g + this.h;
		}
		
		public boolean equals(Object a){
			if (a instanceof Node){
				Node aNode = (Node)a;
				return (aNode.pos.getX() == this.pos.getX() && aNode.pos.getY() == this.pos.getY());
			}
			return false;
		}
	}