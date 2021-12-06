package ticTacToe;

/**
 * A tuple containing an {@link Outcome} object, with its associated probability - this specifies the probability of the transition occurring. 
 * Typically, a list of these objects is returned by a {@link TTTMDP} instance when querying it for the transition probability distribution.
 * @author ae187
 *
 */
public class TransitionProb {
	public Outcome outcome;
	public double prob;
	
	

	

	public TransitionProb(Outcome o, double prob) {
		this.outcome=o;
		this.prob=prob;
	}
	
	public String toString()
	{
		return outcome.toString()+"\n"+"Prob = "+prob;
	}

}
