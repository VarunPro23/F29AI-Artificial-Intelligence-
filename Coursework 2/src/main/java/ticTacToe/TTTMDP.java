package ticTacToe;


import java.util.ArrayList;
import java.util.List;

/**
 * This class specifies a full MDP for the Tic Tac Toe game, i.e. the Transition Function, T, and the Reward Function R as per your lectures.
 * 
 * It does this by generating a set of {@link TransitionProb} objects for any given game/state and move, thus giving you 
 * a probability distribution over next states and their associated rewards. The {@link #generateTransitions} method does 
 * this. You will use an object of this class to implement your Value Iteration and Policy Iteration agents.
 * 
 * Note: this is only used for offline, planning methods such as Value Iteration and Policy Iteration, and, crucially, NOT in Reinforcement Learning 
 * (e.g. Q-learning) where you should't assume access to such a model.
 * 
 * Note: This class assumes that the Value/Policy Iteration agents always play as 'X' (never 'O') - as does the {@link ValueIterationAgent} class.
 * 
 * @author ae187
 */

public class TTTMDP {

	double winReward=10.0;
	double loseReward=-50.0;
	double livingReward=-1.00;
	double drawReward=0.0;
	
	public TTTMDP() {
	
		
	}

	public TTTMDP(double win, double lose, double live, double draw) {
		this.winReward=win;
		this.loseReward=lose;
		this.livingReward=live;
		this.drawReward=draw;
	}
	
	
	/**
	 * Generates a list of TransitionProb objects containing game outcomes (source game, move, reward, target game) tuples paired with their
	 * probabilities. 
	 * This essentially gives you a uniform probability distribution over all possible resulting game states and rewards when making move {@code m} 
	 * in game {@code g}. This is implemented for you to use in your Value Iteration and Policy Iteration implementations. 
	 * @param g
	 * @param m
	 * @return
	 */
	public List<TransitionProb> generateTransitions(Game g, Move m)
	{
		if (g.whoseTurn.getName()!=m.who.getName())
			throw new IllegalArgumentException("It's not "+m.who.getName()+"'s turn in game. This shouldn't happen!");
		
		if (m.who.getName()=='O')
		{
			throw new IllegalStateException("Value Iteration Agent is playing O. This will lead to unexpected results. It shouldn't happen.");
		}
		
		List<TransitionProb> result=new ArrayList<TransitionProb>();
		//first simulate move m
		
		Game intermediate=null;
		try {
			intermediate=g.simulateMove(m);
		}
		catch(IllegalMoveException e)
		{
			System.out.println("WARNING: illegal move "+m+" tried when generating transitions. Returning empty list.");
			System.out.println(e.getMessage());
			
			return result;
		}
		
		//first check if X has won.
		if (intermediate.getState()==Game.X_WON)
		{
			//if we are here, X won the game. 
			double reward=this.winReward;
			Outcome o=new Outcome(g, m, reward, intermediate);
			TransitionProb transProb=new TransitionProb(o, 1.0);
			result.add(transProb);
			return result;
			
		}//is it a draw?
		else if (intermediate.getState()==Game.DRAW)
		{
			double reward=this.drawReward;
			Outcome o=new Outcome(g, m, reward, intermediate);
			TransitionProb transProb=new TransitionProb(o, 1.0);
			result.add(transProb);
			return result;
			
			
		}
		
		
		//If we are here equal chance that the opponent will move into the available places.
		//we generate simulate all these, and associate them with equal probability
		
		List<Game> nextPossibleStates=intermediate.getAllSuccessorGames();
		for(Game game: nextPossibleStates)
		{
			double reward;	
			if(game.getState()==Game.O_WON)
			{
				reward=this.loseReward;
				
			}
			else if (game.getState()==Game.DRAW)
			{
				reward=this.drawReward;
				
			}
			else
			{
				//game is ongoing. 
				reward=this.livingReward;
			}
			
			Outcome o=new Outcome(g, m, reward, game);
			double prob=(double)1/nextPossibleStates.size();
			
			TransitionProb transProb=new TransitionProb(o, prob);
			result.add(transProb);
			
		}
		
		return result;
		
	}
	
	
	
	
	
	
	
	public boolean isTerminal(Game g)
	{
		return g.isTerminal();
	}
	
	public static void main(String args[]) throws IllegalMoveException
	{
		Game g=new Game();
		g.executeMove('X', 0, 0);
		g.executeMove('O',1,0);
		g.executeMove('X',0,1);
		g.executeMove('O', 2,2);
		
		Move m=new Move('X', 0,2);
		
		TTTMDP gm=new TTTMDP();
		List<TransitionProb> tps=gm.generateTransitions(g, m);
		
		for(TransitionProb tp:tps)
		{
			System.out.println(tp);
			System.out.println("---------------");
		}
		
		
	}
	

}
