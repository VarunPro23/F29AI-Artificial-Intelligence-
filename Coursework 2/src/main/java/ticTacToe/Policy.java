package ticTacToe;


import java.util.HashMap;

/**
 * A Tic Tac Toe Policy implemented via a HashMap
 * @author ae187
 *
 */
public class Policy {
	
	/**
	 * a hash map that stores the policy
	 */
	HashMap<Game, Move> policy=new HashMap<Game, Move>();
	
	/**
	 * Create policy according to this map
	 * @param policy
	 */
	public Policy(HashMap<Game, Move> policy) {
		this.policy=policy;
	}
	
	public Policy()
	{}
	
	/**
	 * Default behaviour here is that if the game state is not in the policy map {@link Policy#policy} then the policy returns null.
	 * You can override this method for different behaviours, e.g. returning a random move if this happens.
	 *  
	 * @param g
	 * @return
	 */
	public Move getMove(Game g) {
		
		if (policy.containsKey(g))
			return policy.get(g);
		
		return null;
		
		
	}
	
	/**
	 * loads policy from file. One possible format for storing a policy is as a sequence of lines where each line
	 * specifies the action/move determined by the policy in a particular game state. The game states should not
	 * explicitly stored, only their hashes are stored. The {@link Game} object itself can be constructed using the 
	 * {@link Game#inverseHash} method.
	 *  
	 * @param file
	 */
	public Policy(String file)
	{
		/* implement this constructor to load a stored policy from file. */
		
	}

	
	
	

}
