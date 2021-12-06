package uk.ac.hw.macs.search.example;

import java.util.List;
import java.util.Set;

import uk.ac.hw.macs.search.ChildWithCost;
import uk.ac.hw.macs.search.FringeNode;
import uk.ac.hw.macs.search.SearchOrder;

/**
 * A simple search order that adds all nodes to the start of the fringe.
 */
public class DepthFirstSearchOrder implements SearchOrder {

	@Override
	public void addToFringe(List<FringeNode> frontier, FringeNode parent, Set<ChildWithCost> children) {
		// Add them at the start, ignoring the cost
		for (ChildWithCost child : children) {
			frontier.add(0, new FringeNode(child.node, parent, child.cost));
		}
	}
}
