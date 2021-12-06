package ticTacToe;

/**
 * A defensive agent: if there is a move to block the opponent this agent will play it. Otherwise plays randomly.
 * @author ae187
 *
 */
public class DefensiveAgent extends Agent {
	
	public DefensiveAgent()
	{
		super(new DefensivePolicy());
	}

}
