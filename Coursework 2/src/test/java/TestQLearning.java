import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ticTacToe.AggressiveAgent;
import ticTacToe.DefensiveAgent;
import ticTacToe.QLearningAgent;
import ticTacToe.RandomAgent;
import ticTacToe.ValueIterationAgent;

public class TestQLearning {
	@Test
	public void testDefensive() {
		System.out.println("Against Defensive Agent:");
		int[] results=TestPolicyIterationAgent.playAgainstEachOther(new QLearningAgent(), new DefensiveAgent(), 50);
		System.out.println("Wins: " + results[0] + " Losses: " + results[1] + " Draws: " + results[2]);
		assertEquals(0, results[1]);
		

	}
	
	@Test
	public void testAggressive() {
		System.out.println("Against Aggressive Agent:");
		
		int[] results=TestPolicyIterationAgent.playAgainstEachOther(new QLearningAgent(), new AggressiveAgent(), 50);
		System.out.println("Wins: " + results[0] + " Losses: " + results[1] + " Draws: " + results[2]);
		assertEquals(0, results[1]);
		

	}

	
	@Test
	public void testRandom() {
		System.out.println("Against Random Agent:");
		int[] results=TestPolicyIterationAgent.playAgainstEachOther(new QLearningAgent(), new RandomAgent(), 50);
		
		System.out.println("Wins: " + results[0] + " Losses: " + results[1] + " Draws: " + results[2]);
		assertEquals(0, results[1]);
		

	}

}
