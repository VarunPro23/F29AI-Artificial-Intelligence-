package ticTacToe;


import java.util.List;
import java.util.Random;
/**
 * An agrgessive policy: if there is a winning move given a game state (Game object) then this policy finds it. Otherwise returns a
 * random available move.
 *  
 * @author ae187
 *
 */
public class AggressivePolicy extends Policy {
	
	
	public Move getMove(Game g) {
		
		
		List<Move> moves=g.getPossibleMoves();
		
		for(Move m:moves)
		{
			
			Game resulting=null;
			try{
				resulting=g.simulateMove(m);
			}
			catch(IllegalMoveException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			if (resulting.getState()==Game.X_WON||resulting.getState()==Game.O_WON)
				return m;
		}
		
		Random r=new Random();
		
		return moves.get(r.nextInt(moves.size()));
		
	}

}
