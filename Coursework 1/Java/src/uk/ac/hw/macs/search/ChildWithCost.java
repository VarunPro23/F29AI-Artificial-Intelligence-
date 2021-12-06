package uk.ac.hw.macs.search;

/**
 * Represents a connection in the state graph: a node, and the cost of getting from the parent to this node.
 */
public class ChildWithCost  {
	
	public final Node node;
	public final int cost;
	
	public ChildWithCost(Node child, int cost) {
		this.node = child;
		this.cost = cost;
	}
	
}
