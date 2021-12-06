package uk.ac.hw.macs.search.coursework;

import java.util.Comparator;

import uk.ac.hw.macs.search.*;

public class FNodeCompare implements Comparator<FringeNode>
{
	/*
	 * This function compares the two fringe nodes for their order in a
	 * frontier based on the F-Values.
	 * 
	 *  F-Value = Heuristic + gValue
	 */
	
	@Override
	public int compare(FringeNode o1, FringeNode o2) 
	{
		// Finding the F-Value for both the nodes
		
		int n1F = (int) o1.node.getValue().getHeuristic() + o1.gValue;
		int n2F = (int) o2.node.getValue().getHeuristic() + o2.gValue;
		return n1F - n2F;
	}
}
