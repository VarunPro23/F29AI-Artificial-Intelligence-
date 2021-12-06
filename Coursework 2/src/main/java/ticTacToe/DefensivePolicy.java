package ticTacToe;


import java.util.List;
import java.util.Random;
/**
 * A defensive policy: if there is a move to block the opponent's X's or O's in a row, this policy finds it and returns it. Otherwise
 * returns random move.
 * @author ae187
 *
 */
public class DefensivePolicy extends Policy {
	
public Move getMove(Game g) {
		
		
		List<Move> moves=g.getPossibleMovesByOpponent();
		
		for(Move m: moves)
		{
			Game resulting=g.clone();
			resulting.getBoard()[m.x][m.y]=m.who.getName();
			resulting.evaluateGameState();
			if (resulting.getState()==Game.X_WON||resulting.getState()==Game.O_WON)
			{
				Move newMove=new Move(m.who.getName()=='X'?'O':'X', m.x,m.y);
				System.out.println("Playing defensive move");
				return newMove;
			}
		}
		
		Random r=new Random();
		System.out.println("Playing random move");
		Move randomMove=moves.get(r.nextInt(moves.size()));
		Move myMove=new Move(randomMove.who.getName()=='X'?'O':'X', randomMove.x,randomMove.y);
		
		return myMove;
		
	}

}
