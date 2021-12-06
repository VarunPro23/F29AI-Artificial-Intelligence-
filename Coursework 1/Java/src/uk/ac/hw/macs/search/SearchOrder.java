package uk.ac.hw.macs.search;

import java.util.List;
import java.util.Set;

/**
 * Encodes a search order by describing how nodes are added to the fringe. Note that nodes are 
 * always removed from the front of the fringe.
 */
public interface SearchOrder {
	
	public void addToFringe(List<FringeNode> frontier, FringeNode parent, Set<ChildWithCost> children);

}
