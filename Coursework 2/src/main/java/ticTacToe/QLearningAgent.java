package ticTacToe;

import java.util.List;
import java.util.Random;

/**
 * A Q-Learning agent with a Q-Table, i.e. a table of Q-Values. This table is implemented in the {@link QTable} class.
 * 
 *  The methods to implement are: 
 * (1) {@link QLearningAgent#train}
 * (2) {@link QLearningAgent#extractPolicy}
 * 
 * Your agent acts in a {@link TTTEnvironment} which provides the method {@link TTTEnvironment#executeMove} which returns an {@link Outcome} object, in other words
 * an [s,a,r,s']: source state, action taken, reward received, and the target state after the opponent has played their move. You may want/need to edit
 * {@link TTTEnvironment} - but you probably won't need to. 
 * @author ae187
 */

public class QLearningAgent extends Agent {
	
	/**
	 * The learning rate, between 0 and 1.
	 */
	double alpha=0.1;
	
	/**
	 * The number of episodes to train for
	 */
	int numEpisodes=10000;
	
	/**
	 * The discount factor (gamma)
	 */
	double discount=0.9;
	
	
	/**
	 * The epsilon in the epsilon greedy policy used during training.
	 */
	double epsilon=0.1;
	
	/**
	 * This is the Q-Table. To get an value for an (s,a) pair, i.e. a (game, move) pair.
	 * 
	 */
	
	QTable qTable=new QTable();
	
	
	/**
	 * This is the Reinforcement Learning environment that this agent will interact with when it is training.
	 * By default, the opponent is the random agent which should make your q learning agent learn the same policy 
	 * as your value iteration and policy iteration agents.
	 */
	TTTEnvironment env=new TTTEnvironment();
	
	
	/**
	 * Construct a Q-Learning agent that learns from interactions with {@code opponent}.
	 * @param opponent the opponent agent that this Q-Learning agent will interact with to learn.
	 * @param learningRate This is the rate at which the agent learns. Alpha from your lectures.
	 * @param numEpisodes The number of episodes (games) to train for
	 */
	public QLearningAgent(Agent opponent, double learningRate, int numEpisodes, double discount)
	{
		env=new TTTEnvironment(opponent);
		this.alpha=learningRate;
		this.numEpisodes=numEpisodes;
		this.discount=discount;
		initQTable();
		train();
	}
	
	/**
	 * Initialises all valid q-values -- Q(g,m) -- to 0.
	 *  
	 */
	
	protected void initQTable()
	{
		List<Game> allGames=Game.generateAllValidGames('X');//all valid games where it is X's turn, or it's terminal.
		for(Game g: allGames)
		{
			List<Move> moves=g.getPossibleMoves();
			for(Move m: moves)
			{
				this.qTable.addQValue(g, m, 0.0);
				//System.out.println("initing q value. Game:"+g);
				//System.out.println("Move:"+m);
			}
			
		}
		
	}
	
	/**
	 * Uses default parameters for the opponent (a RandomAgent) and the learning rate (0.2). Use other constructor to set these manually.
	 */
	public QLearningAgent()
	{
		this(new RandomAgent(), 0.1, 100, 0.9);
		
	}
	
	/*
	 * This method gets the maximim q value for a game state. It takes all the possible moves from the state.
	 * If the list is empty, then we make the q value 0, else we compare and keep updating the max q value. 
	 */
	public double maxQValue(Game g)
	{
		double maxQ , qVal;													// Initialized variables to store the maximum q value and the current q value					
		
		List<Move> posMoves = g.getPossibleMoves();							// Initialize a list of all possible moves
		
		if(g.isTerminal())													// If the game state is in the terminal state,
			maxQ = 0;														// set max q value to 0
		else																// else
			maxQ = Double.NEGATIVE_INFINITY;								// set max q to a very low number.			
		
		
		if(posMoves.isEmpty())												// If the list of possible moves is empty,
			return 0;														// then return 0 as the max q value	
		
		for(Move move : posMoves)											// Loop through all possible moves
		{
			qVal = qTable.getQValue(g, move);								// Get the current q value from the q table
			
			if(qVal > maxQ)													// If the q value is greater than the max q value	
			{	
				maxQ = qVal;												// set the max q value as the current q value	
			}
		}
		return maxQ;														// Return the max q value
	}
	
	/*
	 *  This function is used to perform the epsilon greedy policy on the game state. It returns the move that was performed.
	 *  This method uses both random move and best move.
	 */
	public Move epsilon(Game g)
	{
		double maxQ , qVal;												// Initialize variables to store the maximum q value and the current q value.
		Move move = null;												// The move that is returned. It is set to null at the start.	
		
		List<Move> posMoves = g.getPossibleMoves();						// Initialize a list of all possible moves		
		
		Random rand = new Random();										// Random variable initialized to perform exploration(random move)	
		
		if(rand.nextDouble() < epsilon)									// If the next double is less than epsilon, we perform random move  
		{
			int r = rand.nextInt(posMoves.size());						// Get a random number based on the size of the list
			
			move = posMoves.get(r);										// Update the move to be returned.
		}
		
		else															// Else, we perform exploitation (best move)	
		{
			if(g.isTerminal())											// If we are in terminal state,
				maxQ = 0;												// we set maximum q value to 0
			else														// else
				maxQ = Double.NEGATIVE_INFINITY;						// maximum q value is set as a very low number	
			
			for(Move m : posMoves)										// Loop through all the possibe moves
			{
				qVal = qTable.getQValue(g, move);						// Get the q value from the q table			
				
				if(qVal > maxQ)											// If the current q value is greater than the max q value
				{
					maxQ = qVal;										// Set the maximum q value as the current q value
					move = m;											// Update the move 
				}
			}
		}	
		return move;													// Return the move 
	}
	
	/**
	 *  Implement this method. It should play {@code this.numEpisodes} episodes of Tic-Tac-Toe with the TTTEnvironment, updating q-values according 
	 *  to the Q-Learning algorithm as required. The agent should play according to an epsilon-greedy policy where with the probability {@code epsilon} the
	 *  agent explores, and with probability {@code 1-epsilon}, it exploits. 
	 *  
	 *  At the end of this method you should always call the {@code extractPolicy()} method to extract the policy from the learned q-values. This is currently
	 *  done for you on the last line of the method.
	 */
	
	public void train()
	{
		/* 
		 * YOUR CODE HERE
		 */
		
		
		//--------------------------------------------------------
		//you shouldn't need to delete the following lines of code.
		this.policy=extractPolicy();
		if (this.policy==null)
		{
			System.out.println("Unimplemented methods! First implement the train() & extractPolicy methods");
			//System.exit(1);
		}
	}
	
	/** Implement this method. It should use the q-values in the {@code qTable} to extract a policy and return it.
	 *
	 * @return the policy currently inherent in the QTable
	 */
	public Policy extractPolicy()
	{
		double maxQ , qVal = 0;														// Initialized two variables to store max q value and current q value			
		Move topMove = null;														// VAriable to store the maximum move
		
		Policy p = new Policy();													// Create a new policy
		
		for(Game state : qTable.keySet())											// Loop through all the game states
		{
			List<Move> posMove = state.getPossibleMoves();							// Initialize a list of all possible moves
			
			if(state.isTerminal())													// If we are in terminal state,
				maxQ = 0;															// set maximum q value as 0	
			else																	// else
				maxQ = -1000000;													// set it to a very low number
			
			for(Move move : posMove)												// Looping through all the possible moves
			{
				qVal = qTable.getQValue(state, move);								// Get the current q value from the q table		
				
				if(qVal > maxQ)														// If the curent q value is greater than the maximum q value
				{
					maxQ = qVal;													// set the maximum q value as the current q value
					topMove = move;													// set the maximum move as the move	
				}
			}
			
			p.policy.put(state, topMove);											// Update the policy
		}
		
		return p;																	// Return the policy
	}
	
	public static void main(String a[]) throws IllegalMoveException
	{
		//Test method to play your agent against a human agent (yourself).
		QLearningAgent agent=new QLearningAgent();
		
		HumanAgent d=new HumanAgent();
		
		Game g=new Game(agent, d, d);
		g.playOut();	
	}	
}
