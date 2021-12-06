package ticTacToe;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is a Policy which picks an move randomly with equal probability from the available moves. 
 * @author ae187
 *
 */
public class RandomPolicy extends Policy{

	Random r = new Random();
	
	@Override
	public Move getMove(Game g) {
		
		
		List<IndexPair> pairs=new ArrayList<IndexPair>();
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				if (g.getBoard()[i][j]==' ')
					pairs.add(new IndexPair(i,j));
					
			}
		
		IndexPair random=pairs.get(r.nextInt(pairs.size()));
		
		return new Move(g.whoseTurn, random.x, random.y);
	}
	
	
	

}


class IndexPair{
	
	public int x;
	public int y;
	
	IndexPair(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
}