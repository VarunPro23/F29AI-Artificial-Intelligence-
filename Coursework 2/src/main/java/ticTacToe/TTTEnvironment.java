package ticTacToe;


import java.util.ArrayList;
import java.util.List;

/**
 * This is a Reinforcement Learning environment for TicTacToe, to be used in conjunction with Reinforcement Learning (RL) agents. Like everywhere else, 
 * the opponent here is modeled as part of the environment, is by default a {@link RandomAgent} and is always assigned as the 'O' agent;
 * whereas the RL agent (e.g. {@link QLearningAgent}) interacting with this environment always plays as 'X'. The key method to be used from this class
 * is the {@link TTTEnvironment#executeMove} method which takes a move (from X) and returns an {@link Outcome} object containing reward received as well
 * as the target state.
 * @author ae187
 *
 */
public class TTTEnvironment {
	
	
	/**
	 * The game object representing the current game state.
	 */
	Game game;
	
	
	double winReward=10.0;
	double loseReward=-50.0;
	double livingReward=-1.00;
	double drawReward=0.0;
	
	
	/**
	 * By default, the environment contains an opponent that plays randomly, i.e. a {@link RandomAgent}; and uses all the
	 * reward parameters above.
	 */
	public TTTEnvironment()
	{
		game=new Game(new Agent(), new RandomAgent());
	}
	
	public TTTEnvironment(Agent opponent)
	{
		game=new Game(new Agent(), opponent);
	}
	
	public TTTEnvironment(Agent opponent, double winReward, double loseReward, double livingReward, double drawReward)
	{
		game=new Game(new Agent(), opponent);
		this.winReward=winReward;
		this.loseReward=loseReward;
		this.livingReward=livingReward;
		this.drawReward=drawReward;
		
	}
	
	public Game getCurrentGameState()
	{
		return game;
	}
	
	public List<Move> getPossibleMoves()
	{
		List<Move> moves=new ArrayList<Move>();
		if (game.whoseTurn.getName()!='X')
			return moves;
		
		return game.getPossibleMoves();
		
	}
	
	/**
	 * Performs action/move {@code m} and returns an environment outcome {@code o}. Note that the outcome returned includes
	 * the opponent's move, i.e. it is the game state AFTER the opponent has also played. The only exception to this is 
	 * when our agent's move leads to a terminal state (winning, losing or draw). 
	 * @param m
	 * @return the environment outcome after playing move {@code m}. Null if we the move is illegal or if the environment is in a terminal 
	 * state.
	 */
	public Outcome executeMove(Move m) throws IllegalMoveException
	{
	
		
		if (!game.isLegal(m))
			throw new IllegalMoveException("Illegal Move:"+m+" on:"+game);
		else if (game.isTerminal())
		{
			System.out.println("Executing move in terminal state. Returning null.");
			return null;
		}
		else if (m.who.getName()!='X')
		{
			System.out.println("Trying to executing O move - the RL agent must always play as X. Returning null Outcome object.");
			return null;
		}
		
		Game prev=this.game.clone();
		
		game.executeMove(m);
		
		if (game.getState()==Game.X_WON)
		{
			return new Outcome(prev, m, this.winReward, game);
		}
		else if (game.getState()==Game.DRAW)
		{
			//O couln't have won by X's move.
			return new Outcome(prev, m, this.drawReward, game);
		}
		
		//If we are here, the game is ongoing. So now it's the opponent's turn to play.
		Move oMove=game.o.getMove(game);
		if (!game.isLegal(oMove))
			throw new IllegalMoveException("Illegal Move:"+m+" on:"+game);
		
		
		game.executeMove(oMove);
		if (game.getState()==Game.O_WON)
		{
			return new Outcome(prev, m, this.loseReward, game);
		}
		else if (game.getState()==Game.DRAW)
		{
			//O couln't have won by X's move.
			return new Outcome(prev, m, this.drawReward, game);
		}
		
		
		
		
		return new Outcome(prev,m,this.livingReward,game);
		
	}
	
	public boolean isTerminal()
	{
		return game.isTerminal();
	}
	
	
	/**
	 * Resets current game state to a new game state (empty board)
	 */
	public void reset()
	{
		this.game=new Game(new Agent(), new RandomAgent());
	}
	
	

}
