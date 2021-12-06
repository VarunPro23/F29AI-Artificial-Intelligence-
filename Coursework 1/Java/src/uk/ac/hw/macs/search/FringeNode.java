package uk.ac.hw.macs.search;

/**
 * Represents a node on the frontier of the search space. Includes the actual node,
 * the parent node (i.e., the node that was expanded to add this one), as well as the
 * cost of getting to this node (the "g" value).
 */
public class FringeNode {
	public Node node;
	public FringeNode parent;
	public int gValue;
	
	/**
	 * Creates a new FringeNode.
	 * 
	 * @param node The corresponding node in the search space
	 * @param parent The node that was expanded to produce this node
	 * @param cost The cost of the transition from the parent to this node
	 */
	public FringeNode(Node node, FringeNode parent, int cost) {
		this.node = node;
		this.parent = parent;
		this.gValue = cost;
		if (parent != null) {
			gValue += parent.gValue;
		}
	}
	
	/**
	 * @return The "f" value: the cost of getting to this node plus the estimated heuristic value.
	 */
	public int getFValue() {
		return gValue + node.getValue().getHeuristic();
	}

	@Override
	public String toString() {
		return "FringeNode [node=" + node + ", parent=" + parent + ", gValue=" + gValue + "]";
	}
	
}
