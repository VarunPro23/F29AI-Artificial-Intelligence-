package ticTacToe;


public class Agent {
	
	/**
	 * A Tic Tac Toe Policy
	 */
	protected Policy policy;
	
	protected char name; //'X' or 'O'
	
	/**
	 * Name of the agent ('X' or 'O') is set when the agent joins the game
	 * @param p
	 */
	public Agent(Policy p)
	{
		this.policy=p;
	}
	/**
	 * new agent with no (null) policy
	 * 
	 */
	public Agent()
	{
		this(null);
	}
	/**
	 * This is the method that uses the agent's policy to return a move, given a game.
	 * @param g the game
	 * @return a move according to the agent's policy
	 */
	public Move getMove(Game g)
	{
		return policy.getMove(g);
	}
	
	public char getName()
	{
		return name;
	}
	
	public Policy getPolicy()
	{
		return policy;
		
	}
	
	public String toString()
	{
		return ""+this.getName();
	}
	
	public void setName(char n)
	{
		this.name=n;
	}
	
	
	public Policy loadPolicyFromFile()
	{
		return null;
	}
	
	
	
	
	
	

}
