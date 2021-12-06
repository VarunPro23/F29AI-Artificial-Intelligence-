package uk.ac.hw.macs.search.coursework;

import uk.ac.hw.macs.search.*;

public class Grid implements State
{
	private int heuristic;						// Variable to store the heuristic value of the node
	private boolean isGoal;						// Boolean variable to check if the node is the goa or not			
	private int xpos;							// X position of the node
	private int ypos;							// Y position of the node
	private int width;							// Width of the grid
	private int height;							// Height of the grid
	private Node node;							// Node object
	private int cost = 1;						// The cost to visit a position which is set to 1 as default.	

	public Grid(int xpos, int ypos, boolean isGoal, int width, int height)
	{
		this.isGoal = isGoal;
		this.xpos = xpos;
		this.ypos = ypos;
		this.height = height;
		this.width = width;
	}
	
	public Grid(int xpos, int ypos, int width, int height)
	{
		this(xpos, ypos, false, width, height);
	}
	
	@Override
	public boolean isGoal() 
	{
		return this.isGoal;
	}

	@Override
	public int getHeuristic() 
	{
		return heuristic;
	}
	
	/*
	 * This function sets the heuristic value
	 */
	public void setHeuristic(int h)
	{
		this.heuristic = h;
	}
	
	/*
	 * This function checks if the positions next to the node is valid
	 */
	public boolean isValid()
	{
		return !(xpos < 0 || xpos > width-1 || ypos < 0 || ypos > height-1);
	}
	
	/*
	 * This function returns the cost to travel to the next node
	 */
	public int getCost()
	{
		return cost;
	}
	
	/*
	 * This function sets the cost
	 */
	public void setCost(int c)
	{
		cost = c;
	}
	
	/*
	 * This function returns the X position of the node in the grid
	 */
	public int getPosOfX()
	{
		return xpos;
	}
	
	/*
	 * This function returns the Y position of the node in the grid
	 */
	public int getPosOfY()
	{
		return ypos;
	}
	
	/*
	 * This function gets the node
	 */
	public Node getNode()
	{
		return node;
	}
	
	/*
	 * This function sets the node
	 */
	public void setNode(Node n)
	{
		this.node = n;
	}
	
	/*
	 * This function prints the node's position in the grid to the terminal
	 */
	public String toString()
	{
		return "Grid [position: [" + xpos + "," + ypos + "]" + ", goal=" + isGoal + "]";
	}

}
