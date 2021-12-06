package uk.ac.hw.macs.search.example;

import uk.ac.hw.macs.search.State;

/**
 * A simple state representing integers.
 */
public class IntState implements State {
	
	private int value;
	private boolean goal;
	
	public IntState (int value) {
		this(value, false);
	}
	
	public IntState (int value, boolean goal) {
		this.value = value;
		this.goal = goal;
	}

	@Override
	public boolean isGoal() {
		return this.goal;
	}

	@Override
	public int getHeuristic() {
		return 0;
	}

	@Override
	public String toString() {
		return "IntegerState [value=" + value + ", goal=" + goal + "]";
	}
	
}
