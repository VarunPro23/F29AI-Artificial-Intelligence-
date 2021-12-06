package uk.ac.hw.macs.search.coursework;

import java.util.List;
import java.util.Set;

import uk.ac.hw.macs.search.*;

/*
 * This function adds a node to the fringe.
 */
public class AstarSearchOrder implements SearchOrder 
{

	@Override
	public void addToFringe(List<FringeNode> frontier, FringeNode parent, Set<ChildWithCost> children) 
	{
		for(ChildWithCost child : children)					// Loops through all the children in the children set
		{
			frontier.add(new FringeNode(child.node, parent, child.cost));		// Ads the child to the frontier
		}
		frontier.sort(new FNodeCompare());	
	}

}
