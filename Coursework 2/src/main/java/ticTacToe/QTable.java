package ticTacToe;

import java.util.HashMap;
/**
 * This class a simple implementation of a Q-Table. It's a subclass of {@link java.util.HashMap}, so all the methods from 
 * that class are available. Two methods for adding and retrieving q-values to/from the table are provided.
 * 
 * @author ae187
 *
 */
public class QTable extends HashMap<Game, HashMap<Move,Double>> {

	
	public QTable()
	{
		super();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param g
	 * @param m
	 * @return the q value associated with the q-state {@code (g,m)}, where game is a {@link Game} object 
	 * and m is a {@link Move} object}
	 */
	public Double getQValue(Game g, Move m)
	{
		if (containsKey(g))
		{
			HashMap<Move,Double> moves=get(g);
			if (moves.containsKey(m))
				return moves.get(m);
		}
		
		return null;
	}
	
	/**
	 * Adds the q-value mapping (g,m)->v to the q-table represented by this map.
	 * @param g
	 * @param m
	 * @param v
	 */
	public void addQValue(Game g, Move m, Double v)
	{
		if (!containsKey(g))
			this.put(g, new HashMap<Move,Double>());
		
		this.get(g).put(m, v);
	}
	
	

}
