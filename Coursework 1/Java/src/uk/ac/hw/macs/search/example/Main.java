package uk.ac.hw.macs.search.example;

import uk.ac.hw.macs.search.Node;
import uk.ac.hw.macs.search.SearchOrder;
import uk.ac.hw.macs.search.SearchProblem;

/**
 * Demonstrates how to use the provided classes.
 */
public class Main {

	private static Node addChild(int value, boolean goal, Node parent) {
		Node child = new Node(new IntState(value, goal));
		parent.addChild(child, 1);
		return child;
	}

	public static void main(String[] args) {
		// Create some states
		Node root = new Node(new IntState(0));
		Node goal = new Node(new IntState(5, true));
		Node child = addChild(1, false, root);
		child = addChild(2, false, child);
		child = addChild(3, false, child);
		addChild(4, false, child);
		root.addChild(goal, 1);

		// Run the search
		SearchOrder order = new BreadthFirstSearchOrder();
		SearchProblem problem = new SearchProblem(order);
		problem.doSearch(root);
	}

}
