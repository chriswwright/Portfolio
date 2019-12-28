import java.util.Comparator;

/**
 * A comparator used to sort events by time.
 */
final class AStarComparators implements Comparator<Node>
{
    public int compare(Node a, Node b)
    {
		
        return Double.compare(a.getFValue(), b.getFValue());
    }
}
