package ticTacToe;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**
 * A policy iteration agent. You should implement the following methods:
 * (1) {@link PolicyIterationAgent#evaluatePolicy}: this is the policy evaluation step from your lectures
 * (2) {@link PolicyIterationAgent#improvePolicy}: this is the policy improvement step from your lectures
 * (3) {@link PolicyIterationAgent#train}: this is a method that should runs/alternate (1) and (2) until convergence. 
 * 
 * NOTE: there are two types of convergence involved in Policy Iteration: Convergence of the Values of the current policy, 
 * and Convergence of the current policy to the optimal policy.
 * The former happens when the values of the current policy no longer improve by much (i.e. the maximum improvement is less than 
 * some small delta). The latter happens when the policy improvement step no longer updates the policy, i.e. the current policy 
 * is already optimal. The algorithm should stop when this happens.
 * 
 * @author ae187
 *
 */
public class PolicyIterationAgent extends Agent {

	/**
	 * This map is used to store the values of states according to the current policy (policy evaluation). 
	 */
	HashMap<Game, Double> policyValues=new HashMap<Game, Double>();
	
	/**
	 * This stores the current policy as a map from {@link Game}s to {@link Move}. 
	 */
	HashMap<Game, Move> curPolicy=new HashMap<Game, Move>();
	
	double discount=0.9;
	
	/**
	 * The mdp model used, see {@link TTTMDP}
	 */
	TTTMDP mdp;
	
	/**
	 * loads the policy from file if one exists. Policies should be stored in .pol files directly under the project folder.
	 */
	public PolicyIterationAgent() 
	{
		super();
		this.mdp=new TTTMDP();
		initValues();
		initRandomPolicy();
		train();	
	}
	
	
	/**
	 * Use this constructor to initialise your agent with an existing policy
	 * @param p
	 */
	public PolicyIterationAgent(Policy p) 
	{
		super(p);	
	}

	/**
	 * Use this constructor to initialise a learning agent with default MDP paramters (rewards, transitions, etc) as specified in 
	 * {@link TTTMDP}
	 * @param discountFactor
	 */
	public PolicyIterationAgent(double discountFactor) 
	{	
		this.discount=discountFactor;
		this.mdp=new TTTMDP();
		initValues();
		initRandomPolicy();
		train();
	}
	/**
	 * Use this constructor to set the various parameters of the Tic-Tac-Toe MDP
	 * @param discountFactor
	 * @param winningReward
	 * @param losingReward
	 * @param livingReward
	 * @param drawReward
	 */
	public PolicyIterationAgent(double discountFactor, double winningReward, double losingReward, double livingReward, double drawReward)
	{
		this.discount=discountFactor;
		this.mdp=new TTTMDP(winningReward, losingReward, livingReward, drawReward);
		initValues();
		initRandomPolicy();
		train();
	}
	/**
	 * Initialises the {@link #policyValues} map, and sets the initial value of all states to 0 
	 * (V0 under some policy pi ({@link #curPolicy} from the lectures). Uses {@link Game#inverseHash} and {@link Game#generateAllValidGames(char)} to do this. 
	 * 
	 */
	public void initValues()
	{
		List<Game> allGames=Game.generateAllValidGames('X');//all valid games where it is X's turn, or it's terminal.
		for(Game g: allGames)
			this.policyValues.put(g, 0.0);	
	}
		
	/**
	 *  You should implement this method to initially generate a random policy, i.e. fill the {@link #curPolicy} for every state. Take care that the moves you choose
	 *  for each state ARE VALID. You can use the {@link Game#getPossibleMoves()} method to get a list of valid moves and choose 
	 *  randomly between them. 
	 */
	public void initRandomPolicy()
	{
		for (Game state : policyValues.keySet())											// Looping through all states in the policy values
		{
			List<Move> posMoves = state.getPossibleMoves();									// Initialize a list of possible moves
			
			Random rand = new Random();														// Create a object to store a random number
			for(int i = 0; i < posMoves.size();i++)											// Loop through all the possible moves
			{
				Move randMove = posMoves.get(rand.nextInt(posMoves.size()));				// Get a move between 0 and the size of possible moves list
				
				curPolicy.put(state, randMove);												// Update a policy
			}
		}
	}
	
	/*
	 * This method calculates the transition value based on the formulas from the lectures.
	 */
	public double calcTransitionVal(TransitionProb t)
	{
		return t.prob * (t.outcome.localReward + (discount * policyValues.get(t.outcome.sPrime)));
	}
	
	/**
	 * Performs policy evaluation steps until the maximum change in values is less than {@code delta}, in other words
	 * until the values under the currrent policy converge. After running this method, 
	 * the {@link PolicyIterationAgent#policyValues} map should contain the values of each reachable state under the current policy. 
	 * You should use the {@link TTTMDP} {@link PolicyIterationAgent#mdp} provided to do this.
	 *
	 * @param delta
	 */
	protected void evaluatePolicy(double delta)
	{
		double maxQVal = -1000000;																	// Variable to store the max q value. We set it to a very low number.
		double qVal = 0;																			// Variable to store the current q value
		
		do																		
		{
			for(Game state : curPolicy.keySet())													// Loop through all game states in the curPolicy
			{
				if(state.isTerminal())																// If we are in the terminal state,
					maxQVal = 0;																	// set the max q value to 0		
				
				qVal = 0;																			// Set the current q value to 0
				
				Move movePolicy = curPolicy.get(state);												// Move being done under the current policy	
				
				for(TransitionProb trans : mdp.generateTransitions(state, movePolicy))				
				{
					qVal += calcTransitionVal(trans);												// Update the current qVal based on the formula from the lectures.
				}
				
				double oldQVal = policyValues.get(state);											// Get the old q value of the state
				policyValues.put(state, qVal);														// Update the state's current q value
				
				maxQVal = Math.abs(oldQVal - qVal);													// Calculate the maximum q value		
			}
		}while(maxQVal > delta);																	// Keep looping as long as the maximium q value is less than delta.	
	}
		
	
	
	/**This method should be run AFTER the {@link PolicyIterationAgent#evaluatePolicy} train method to improve the current policy according to 
	 * {@link PolicyIterationAgent#policyValues}. You will need to do a single step of expectimax from each game (state) key in {@link PolicyIterationAgent#curPolicy} 
	 * to look for a move/action that potentially improves the current policy. 
	 * 
	 * @return true if the policy improved. Returns false if there was no improvement, i.e. the policy already returned the optimal actions.
	 */
	protected boolean improvePolicy()
	{
		boolean changePolicy = false;																// Initialize a flag to check if the policy has changed
		double maxQ , qVal;																			// Initialize variables to store the max q value and the current q value
		
		for(Game state : policyValues.keySet())														// Loop through all the game states
		{
			if(state.isTerminal())																	// If we are in the terminal state
				maxQ = 0;																			// Set the max q value to 0		
			else																					// else
				maxQ = -1000000;																	// set the max q value to a very low number
			
			List<Move> posMoves = state.getPossibleMoves();											// Initialize a list of all possible moves	
			
			for(Move move : posMoves)																// Loop through all the possible moves
			{
				List<TransitionProb> transList = mdp.generateTransitions(state, move);				// Initialize a list of all transitions		
				
				qVal = 0;																			// Set the current q value to 0
				
				for(TransitionProb trans : transList)												// Loop through all the transitions in the list
				{
					qVal += calcTransitionVal(trans);												// Update the current q value using the formula from the lectures		
				}
				
				if(qVal > maxQ)																		// If the current q val is greater than the max q value
				{
					maxQ = qVal;																	// Set the current q value as the max q value
					curPolicy.put(state, move);														// Update the current policy	
					changePolicy = true;															// Change the flag to true
				}
			}
		}
		
		return changePolicy;																		// Return the policy changed value	
	}
	
	/**
	 * The (convergence) delta
	 */
	double delta=0.1;
	
	/**
	 * This method should perform policy evaluation and policy improvement steps until convergence (i.e. until the policy
	 * no longer changes), and so uses your 
	 * {@link PolicyIterationAgent#evaluatePolicy} and {@link PolicyIterationAgent#improvePolicy} methods.
	 */
	public void train()
	{
		initValues();																	// Initialize the values
		initRandomPolicy();																// Initialize random policies	
		
		do
		{
			evaluatePolicy(delta);														// Evaluate the policy
			
			HashMap<Game, Move> prePolicy = new HashMap<>(this.curPolicy);				// Initialize a hash-map to store all the old policies	
			
			improvePolicy();															// Improve the policy
			
			if(curPolicy.equals(prePolicy) || !improvePolicy())							// If the policies are not the same,
				break;																	// break the loop		
		}while(true);																	// Continue the loop until false	
		
		policy = new Policy();															// Create a new policy
		policy.policy = curPolicy;														// Set the new policy as the current policy
	}
	
	public static void main(String[] args) throws IllegalMoveException
	{
		/**
		 * Test code to run the Policy Iteration Agent against a Human Agent.
		 */
		PolicyIterationAgent pi=new PolicyIterationAgent();
		
		HumanAgent h=new HumanAgent();
		
		Game g=new Game(pi, h, h);
		
		g.playOut();		
	}
	

}
