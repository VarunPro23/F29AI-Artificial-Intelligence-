package uk.ac.hw.macs.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Represents a search problem, by running the given search order on given search spaces.
 */
public class SearchProblem {
	
	private SearchOrder searchOrder;
	
	public SearchProblem(SearchOrder searchOrder) {
		this.searchOrder = searchOrder;
	}

	/**
	 * Runs the given search order on the given search space, and prints the result.
	 * 
	 * @param root The root note of the search space (the starting state)
	 * @return True if the search finds a goal state, and false if it does not
	 */
	public boolean doSearch(Node root) {
		List<FringeNode> fringe = new LinkedList();
		fringe.add(new FringeNode(root, null, 0));
		Set<Node> visitedStates = new HashSet();	
		FringeNode goalNode = null;
		
		while (true) {
			if (fringe.isEmpty()) {
				// We didn't find it
				break;
			}
			
			// Take the front node off the list
			FringeNode searchNode = fringe.remove(0);
			System.out.println("Current node: " + searchNode);
			
			// If current node is on block list, ignore it
			if (visitedStates.contains(searchNode.node)) {
				continue;
			}
			
			// Have we reached a goal state? If so, we're done
			if (searchNode.node.isGoal()) {
				goalNode = searchNode;
				break;
			}
			
			// Otherwise: Expand current node
			searchOrder.addToFringe(fringe, searchNode, searchNode.node.getChildren());
			visitedStates.add(searchNode.node);
		}
		
		if (goalNode == null) {
			System.out.println("No goal found");
			return false;
		} else {
			System.out.println("Found goal node: " + goalNode.node);
			System.out.println("Cost: " + goalNode.gValue);
			System.out.println("Nodes expanded: " + visitedStates.size());
			System.out.println("Path to root:");
			FringeNode fnode = goalNode;
			while (fnode != null) {
				System.out.println("- node: " + fnode.node.getValue());
				fnode = fnode.parent;
			}
			return true;
		}

	}

}
