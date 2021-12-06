package ticTacToe;

/**
 * A move in the Tic-Tac-Toe game. If the game is modelled as an MDP, a Move is essentially an MDP Action.
 * @author ae187
 *
 */
public class Move {
	
	/**
	 * can be either 'X', or 'O' 
	 */
	public Agent who;
	public int x;
	public int y;
	
	public Move(char who, int x, int y)
	{
		if (who!='X' && who!='O')
			throw new IllegalArgumentException("Can only be a move by X or by O");
		
		this.who=new Agent();
		this.who.setName(who);
		
		if (x<0 || x>2 || y<0 || y>2)
			throw new IllegalArgumentException("Invalid x or y coordinates");
		this.x=x;
		this.y=y;
		
	}
	
	public Move(Agent who, int x, int y)
	{
		if (who.getName()!='X' && who.getName()!='O')
			throw new IllegalArgumentException("Can only be a move by X or by O");
		
		this.who=who;
		
		if (x<0 || x>2 || y<0 || y>2)
			throw new IllegalArgumentException("Invalid x or y coordinates");
		this.x=x;
		this.y=y;
		
	}
	
	public Move(int x, int y)
	{
		this('X', x, y);
	
	}
	
	public String toString()
	{
		return who+"("+x+","+y+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((who == null) ? 0 : who.getName());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (this.who.getName()!=other.who.getName())
			return false;
		
		if (x != other.x)
			return false;
		
		if (y != other.y)
			return false;
		
		return true;
	}
	
	
	
	

}
