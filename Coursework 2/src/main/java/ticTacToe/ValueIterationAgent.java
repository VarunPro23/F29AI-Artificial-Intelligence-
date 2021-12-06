package ticTacToe;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Value Iteration Agent, only very partially implemented. The methods to implement are: 
 * (1) {@link ValueIterationAgent#iterate}
 * (2) {@link ValueIterationAgent#extractPolicy}
 * 
 * You may also want/need to edit {@link ValueIterationAgent#train} - feel free to do this, but you probably won't need to.
 * @author ae187
 *
 */
public class ValueIterationAgent extends Agent 
{

	/**
	 * This map is used to store the values of states
	 */
	Map<Game, Double> valueFunction=new HashMap<Game, Double>();
	
	/**
	 * the discount factor
	 */
	double discount=0.9;
	
	/**
	 * the MDP model
	 */
	TTTMDP mdp=new TTTMDP();
	
	/**
	 * the number of iterations to perform - feel free to change this/try out different numbers of iterations
	 */
	int k=10;
	
	
	/**
	 * This constructor trains the agent offline first and sets its policy
	 */
	public ValueIterationAgent()
	{
		super();
		mdp=new TTTMDP();
		this.discount=0.9;
		initValues();
		train();
	}
	
	
	/**
	 * Use this constructor to initialise your agent with an existing policy
	 * @param p
	 */
	public ValueIterationAgent(Policy p) 
	{
		super(p);	
	}

	public ValueIterationAgent(double discountFactor) 
	{	
		this.discount=discountFactor;
		mdp=new TTTMDP();
		initValues();
		train();
	}
	
	/**
	 * Initialises the {@link ValueIterationAgent#valueFunction} map, and sets the initial value of all states to 0 
	 * (V0 from the lectures). Uses {@link Game#inverseHash} and {@link Game#generateAllValidGames(char)} to do this. 
	 * 
	 */
	public void initValues()
	{		
		List<Game> allGames=Game.generateAllValidGames('X');//all valid games where it is X's turn, or it's terminal.
		for(Game g: allGames)
			this.valueFunction.put(g, 0.0);		
	}
	
	
	
	public ValueIterationAgent(double discountFactor, double winReward, double loseReward, double livingReward, double drawReward)
	{
		this.discount=discountFactor;
		mdp=new TTTMDP(winReward, loseReward, livingReward, drawReward);
	}
	
	/*
	 * This method calculates the transition value based on the formula given in the lectures.
	 */
	public double calcTransitionVal(TransitionProb t)
	{
		return t.prob * (t.outcome.localReward + (discount * valueFunction.get(t.outcome.sPrime)));
	}
	
	
	/*
	 * Performs {@link #k} value iteration steps. After running this method, the {@link ValueIterationAgent#valueFunction} map should contain
	 * the (current) values of each reachable state. You should use the {@link TTTMDP} provided to do this.
	 */
	public void iterate()
	{
		double qVal = 0, maxQ;												// Initialize two variables of type double
		
		for(int i = 0; i < k; i++)											// Loop through k number of transitions
		{
			for(Game state : valueFunction.keySet())						// Loop through all states in the valueFunction
			{
				List<Move> posMove = state.getPossibleMoves();				// Initializing a list with all possible moves
				
				if(state.isTerminal())										// If we are in terminal state
					maxQ = 0;												// set q value to 0	
				else														// else			
					maxQ = -1000000;										// the q value is set to a very low number
				
				for(Move m : posMove)										// Loop through all possible moves
				{
					List<TransitionProb> transitions = mdp.generateTransitions(state, m);	// Initializing a list of all transitions
					
					qVal = 0;												// For each move we set the q value to 0
					
					for(TransitionProb t : transitions)						// Loop through all transitions
					{
						qVal += calcTransitionVal(t);						// Calculate the new q value
					}
					
					if(qVal > maxQ)											// If the new q value is greater than the maximum q value
						maxQ = qVal;										// then set the new q value as the maximum q value
				}
				
				valueFunction.replace(state, maxQ);
			}
		}
	}
	
	/**This method should be run AFTER the train method to extract a policy according to {@link ValueIterationAgent#valueFunction}
	 * You will need to do a single step of expectimax from each game (state) key in {@link ValueIterationAgent#valueFunction} 
	 * to extract a policy.
	 * 
	 * @return the policy according to {@link ValueIterationAgent#valueFunction}
	 */
	public Policy extractPolicy()
	{
		double maxQ, qVal = 0;													// Initialize two variables of type double 
		Policy p = new Policy();												// Create a new policy
		
		for(Game state: valueFunction.keySet())									// Loop through all states in the valueFunction
		{
			List<Move> posMove = state.getPossibleMoves();						// Initialize a list of all possible moves	
			
			if(state.isTerminal())												// If we are in terminal state
				maxQ = 0;														// we set the max q value to 0	
			else																// else	
				maxQ = -1000000;												// we set it to a very low number
			
			for(Move m : posMove)												// Looping through all possible moves
			{
				List<TransitionProb> transitions = mdp.generateTransitions(state, m);	// Initialize a list of transitions
				
				qVal = 0;														// For each move, we set the qVal to 0
				
				for(TransitionProb t : transitions)								// Loop through all transitions
				{
					qVal += calcTransitionVal(t);								// Calculate the new q value
				}
				
				if(qVal > maxQ)													// If the q value is greater than the max q value			
				{
					maxQ = qVal;												// set the new q valuea as the max q value
					p.policy.put(state, m);										// Set the policy for the game		
				}
			}
		}	
		return p;																// Return the policy
	}
	
	/**
	 * This method solves the mdp using your implementation of {@link ValueIterationAgent#extractPolicy} and
	 * {@link ValueIterationAgent#iterate}. 
	 */
	public void train()
	{
		/**
		 * First run value iteration
		 */
		this.iterate();
		/**
		 * now extract policy from the values in {@link ValueIterationAgent#valueFunction} and set the agent's policy 
		 *  
		 */
		
		super.policy=extractPolicy();
		
		if (this.policy==null)
		{
			System.out.println("Unimplemented methods! First implement the iterate() & extractPolicy() methods");
			//System.exit(1);
		}
	}

	public static void main(String a[]) throws IllegalMoveException
	{
		//Test method to play the agent against a human agent.
		ValueIterationAgent agent=new ValueIterationAgent();
		HumanAgent d=new HumanAgent();
		
		Game g=new Game(agent, d, d);
		g.playOut();		
	}
}
