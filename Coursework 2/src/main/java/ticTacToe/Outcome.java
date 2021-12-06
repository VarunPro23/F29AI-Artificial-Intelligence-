package ticTacToe;

/**
 * This is a game outcome: a data structure containing a game (state), a move, a reward, and the next target game state.
 * 
 * From your lectures, this is essentially a (s,a,r,s') tuple, where s is the source state, a is the action taken, r is the reward
 * received locally, and s' is the state we transitioned into.
 * @author ae187
 *
 */
public class Outcome {

	

	

	public Game s;//source state
	public Move move;
	public double localReward=0.0;//reward received.
	public Game sPrime;//destination state
	
	public Outcome(Game s, Move move, double reward, Game sPrime) {
		this.s=s;
		this.sPrime=sPrime;
		this.move=move;
		this.localReward=reward;
	}

	public String toString()
	{
		String result=s.toString();
		result+="Move: "+move+"\n";
		result+="-->\n";
		result+=sPrime;
		result+="Reward = "+localReward;
		
		return result;
		
	}
	
	
}
