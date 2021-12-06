package ticTacToe;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * A human agent that doesn't have a policy, instead asks the user to enter a move using a command line.
 * @author ae187
 *
 */
public class HumanAgent extends Agent {

	public HumanAgent() {
		super(null);
		
	}

	public Move getMove(Game g)
	{
		// show user possible moves.
		System.out.println("Choose location to put your "+name+" based on the following scheme.");
		System.out.println("0|1|2\n" +
				           "3|4|5\n" +
				           "6|7|8");
		System.out.print("Your move: ");
		
		// read in user input. If bad input is specified, allow user to try again. 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int move=1;
		try{
			move = Integer.parseInt(br.readLine());
			
		} catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		} catch(NumberFormatException e){
			System.out.println("Invalid move selection! You must enter an integer!");
			return getMove(g);
		}
		
		int x=move/3;
		int y=move%3;
		
		if (x<0 || x>2 || y<0 || y>2)
		{
			System.out.println("Invalid number chosen. Choose again.");
			return getMove(g);
		}
		
		Move m=new Move(name, x, y);
		if (!g.isLegal(m))
		{
			System.out.println("Illegal move. Choose again.");
			return getMove(g);
		}
		return m;	
		
		
	}

}
