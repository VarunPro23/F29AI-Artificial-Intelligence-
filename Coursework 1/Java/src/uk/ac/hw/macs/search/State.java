package uk.ac.hw.macs.search;

/**
 * Represents a state in the search space. You need to include a method for determining whether a state is a 
 * goal state, and one for computing the heuristic value.
 */
public interface State {
	public boolean isGoal();
	public int getHeuristic();
}
