import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ticTacToe.Agent;
import ticTacToe.AggressiveAgent;
import ticTacToe.DefensiveAgent;
import ticTacToe.Game;
import ticTacToe.IllegalMoveException;
import ticTacToe.PolicyIterationAgent;
import ticTacToe.RandomAgent;

public class TestPolicyIterationAgent {

	/**
	 * 
	 * @param a1
	 * @param a2
	 * @param howmanyTimes
	 * @return list of results: [xWon, oWon, draw]
	 */
	public static int[] playAgainstEachOther(Agent a1, Agent a2, int howmanyTimes)
	{
		
		Game gn;
		//int[] results=new int[3];

		int xWon = 0;
		int oWon = 0;
		int dr = 0;

		
		for (int i=0;i<howmanyTimes;i++) {
			gn = new Game(a1, a2, a1);
			try {
				gn.playOut();
			}
			catch(NullPointerException e)
			{
				System.out.println("NullPointerException Thrown");
				assertTrue(false);
			}
			catch(IllegalMoveException e)
			{
				System.out.println("Policy returned illegal move");
				assertTrue(false);
				
			}
			catch(Exception e)
			{
		
				e.printStackTrace();
				assertTrue(false);
			}
			if (gn.getState() == Game.X_WON) xWon++;
			else if (gn.getState() == Game.O_WON) oWon++;
			else if (gn.getState() == Game.DRAW) dr++;
		}
		
		
		
		//System.out.println("Wins: " + xWon + " Losses: " + oWon + " Draws: " + dr);
		int[] results = {xWon,oWon,dr};
		
		return results;
		
		
		
	}
	
	@Test
	public void testDefensive() {
		System.out.println("Against Defensive Agent:");
		int[] results=playAgainstEachOther(new PolicyIterationAgent(), new DefensiveAgent(), 50);
		System.out.println("Wins: " + results[0] + " Losses: " + results[1] + " Draws: " + results[2]);
		assertEquals(0, results[1]);
		

	}
	
	@Test
	public void testAggressive() {
		System.out.println("Against Aggressive Agent:");
		
		int[] results=playAgainstEachOther(new PolicyIterationAgent(), new AggressiveAgent(), 50);
		System.out.println("Wins: " + results[0] + " Losses: " + results[1] + " Draws: " + results[2]);
		assertEquals(0, results[1]);
		

	}
	
	
		
		
		
		
		
	
	
	@Test
	public void testRandom() {
		System.out.println("Against Random Agent:");
		int[] results=playAgainstEachOther(new PolicyIterationAgent(), new RandomAgent(), 50);
		
		System.out.println("Wins: " + results[0] + " Losses: " + results[1] + " Draws: " + results[2]);
		assertEquals(0, results[1]);
		

	}

}
