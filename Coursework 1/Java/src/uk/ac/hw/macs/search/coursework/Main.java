package uk.ac.hw.macs.search.coursework;

import uk.ac.hw.macs.search.*;

/*
 * References
 * 1.https://www.geeksforgeeks.org/a-search-algorithm/
 */

public class Main 
{
	/**
	 * This function calculates the manhattan distance between two nodes
	 
	 * @param xPosStart			The x-value of the starting position
	 * @param yPosStart			The y-value of the starting position
	 * @param xPosGoal			The x-value of the goal position
	 * @param yPosGoal			The y-value of the goal position
	 * @return					The manhattan distance
	 */
	public static int manhattanDist(int xPosStart, int yPosStart, int xPosGoal, int yPosGoal)
	{
		int a = Math.abs(xPosGoal - xPosStart);
		int b = Math.abs(yPosGoal - yPosStart);
		return a+b;
	}
	
	/**
	 * This function creates the adjacent positions to the node
	 
	 * @param grid				The grid
	 * @param parentX			The parent position's x-value
	 * @param parentY			The parent position's y-value
	 * @param parent			The parent node
	 * @param width				The width of the grid
	 * @param height			The height of the grid
	 */
	public static void addAdj(Grid[][] grid, int parentX, int parentY, Node parent, int width, int height)
	{
		Grid[] adjPos = new Grid[4];		// The node's current position can have 4 adjacent positions in the grid 
		
		Grid northAdj = new Grid(parentX, parentY-1, width, height);		// The adjacent position to the north of the current position
		Grid southAdj = new Grid(parentX, parentY+1, width, height);		// The adjacent position to the south of the current position
		Grid eastAdj = new Grid(parentX+1, parentY, width, height);			// The adjacent position to the east of the current position
		Grid westAdj = new Grid(parentX-1, parentY, width, height);			// The adjacent position to the west of the current position
		
		adjPos[0] = northAdj;
		adjPos[1] = eastAdj;
		adjPos[2] = southAdj;
		adjPos[3] = westAdj;
		
		for(Grid nearby : adjPos)		// Loop through the list of nearby positions
		{
			if(nearby.isValid())		// If the position is valid, add the child to the parent with the cost to get there
			{
				Grid current = grid[nearby.getPosOfX()][nearby.getPosOfY()];
				Node child = current.getNode();
				parent.addChild(child, current.getCost());
			}
		}
	}
	
	/**
	 * This function populates the grid
	 
	 * @param grid					The grid
	 * @param xPosGoal				The x-value of the goal node
	 * @param yPosGoal				The y-value of the goal node
	 * @param width					The width of the grid
	 * @param height				The height of the grid	
	 */
	public static void popGrid(Grid[][] grid, int xPosGoal, int yPosGoal, int width, int height)
	{
		for(int i = 0;i < width;i++)
		{
			for(int j = 0;j < height;j++)
			{
				Grid g = new Grid(i,j,width,height);
				Grid current;
				int h = manhattanDist(i,j,xPosGoal,yPosGoal);		// The heuristic
				
				if(i == xPosGoal && j == yPosGoal)
				{
					current = new Grid(i, j, true, width, height);
				}
				
				else
				{
					current = new Grid(i,j,width,height);
				}
				
				current.setHeuristic(h);				
				g.setNode(new Node(current));
				grid[i][j] = g;
			}
		}
	}
	
	/**
	 * This function populates the black and grey nodes in the grid.
	 * The black nodes cannot be visited. Their cost value is set to infinity.
	 * The grey nodes can be visited. Their cost is set to 3.
	 * 
	 * @param grid		The grid
	 * @param xBlack	The list of x-values for the black node
	 * @param yBlack	The list of y-values for the black node
	 * @param xGrey		The list of x-values for the grey nodes
	 * @param yGrey		The list of y-values for the grey nodes
	 * @param width		The width of the grid
	 * @param height	The height of the grid
	 */
	public static void popColorBlk(Grid[][] grid, int[] xBlack, int[] yBlack, int[] xGrey, int[] yGrey, int width, int height)
	{
		for(int i = 0;i < width; i++)
		{
			for(int j = 0;j < height;j++)
			{
				// Checking if both the x and y lists provided are of same length
				if(xBlack.length != yBlack.length || xGrey.length != yGrey.length)
				{
					System.err.println("X and Y lists must be of same length.");
					System.exit(0);
				}
				
				// Loop through the black values
				for(int a = 0;a < xBlack.length;a++)
				{
					if(xBlack[a] == i && yBlack[a] == j)	// Checking if the value at position a is equal to the grid position
					{
						grid[i][j].setCost(Integer.MAX_VALUE);		// Sets the cost to infinity, meaning it cannot be visited
					}
				}
				
				// Loop through the grey values
				for(int a = 0;a < xGrey.length;a++)
				{
					if(xGrey[a] == i && yGrey[a] == j)		// Checking if the value at position a is equal to the grid position
					{
						grid[i][j].setCost(3);				// Sets the cost to 3
					}
				}
			}
		}
	}
		
	/**
	 * This function populates the nodes neighbouring the current position in the grid
	 
	 * @param grid		The grid
	 * @param width		The width of the grid
	 * @param height	The height of the grid
	 */
	public static void popAdj(Grid[][] grid, int width, int height)
	{
		for(int i = 0;i < width;i++) 
		{
			for(int j = 0;j < height;j++)
			{
				Node n = grid[i][j].getNode();		// Get the node at the position
				addAdj(grid,i,j,n,width,height);	// Add the adjacent blocks 
			}
		}
	}
	
	/**
	 * This function runs the the search on the grid.
	 * It creates a grid of given width and height, while also populating the grey,black and neighbouring nodes.
	 * It calls a A* search on the grid.
	 * 
	 * @param xBegin			The x-value of the starting position node 
	 * @param yBegin			The y-value of the starting position node
	 * @param xPosGoal			The x-value of the goal node 	
	 * @param yPosGoal			The y-value of the goal node
	 * @param xBlack			List of x-values for the black nodes  
	 * @param yBlack			List of y-values for the black nodes 
	 * @param xGrey				List of x-values for the grey nodes  
	 * @param yGrey				List of y-values for the grey nodes 
	 * @param width				The width of the grid
	 * @param height			The height of the grid
	 */
	public static void gridSearch(int xBegin, int yBegin, int xPosGoal, int yPosGoal, int[] xBlack, int[] yBlack, int[] xGrey, int[]yGrey, int width, int height)
	{
		Grid[][] grid = new Grid[width][height];		// Creating the grid of width and height
		
		// Populate the grid
		
		popGrid(grid, xPosGoal, yPosGoal, width, height);
		popColorBlk(grid, xBlack, yBlack, xGrey, yGrey, width, height);
		popAdj(grid, width, height);
		
		// A* Search
		
		Node startNode = grid[xBegin][yBegin].getNode();
		SearchOrder order = new AstarSearchOrder();
		SearchProblem so = new SearchProblem(order);
		so.doSearch(startNode);
	}
	
	/*
	 * The main function
	 */
	public static void main(String[] args)
	{
		int width = 6, height = 4;			// The width and height of the grid
		
		// Grid 1 values
		
		int xBegin = 0, yBegin = 0;			// The starting position's x and y coordinates
		int xGoal = 3, yGoal = 3;			// The goal position's x and y coordinates
		
		int[] xBlack = new int[] {1,1,2,3};		// The list of x coordinates for the black blocks
		int[] yBlack = new int[] {1,2,1,1};		// The list of y coordinates for the black blocks
		
		int[] xGrey = new int[] {0,1,2,2,3,4};	// The list of x coordinates for the grey blocks
		int[] yGrey = new int[] {3,3,3,2,2,3};	// The list of y coordinates for the grey blocks
		
		System.out.println("\n---------------- Problem 1 ----------------\n");
		gridSearch(xBegin, yBegin, xGoal, yGoal, xBlack, yBlack, xGrey, yGrey, width, height);
		System.out.println("\n------------------ End of problem ------------------\n");
		
		// Grid 2 values
		
		xBegin = 0; yBegin = 0;				// The starting position's x and y coordinates
		xGoal = 5; yGoal = 2;				// The goal position's x and y coordinates
		
		xBlack = new int[] {2,3,4};			// The list of x coordinates for the black blocks
		yBlack = new int[] {1,1,2};			// The list of y coordinates for the black blocks
		
		xGrey = new int[] {1,1,2};			// The list of x coordinates for the grey blocks	
		yGrey = new int[] {0,1,0};			// The list of y coordinates for the grey blocks
		
		System.out.println("\n---------------- Problem 2 ----------------\n");
		gridSearch(xBegin, yBegin, xGoal, yGoal, xBlack, yBlack, xGrey, yGrey, width, height);
		System.out.println("\n------------------ End of problem ------------------\n");
	}
}
