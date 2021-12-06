package ticTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This is a class that defines the environment of a Tic Tac Toe Game: its
 * underlying state representation, actions transitions etc. It also provides
 * functionality for playing out a game between two agents, various other useful
 * methods.
 * 
 * 
 * 
 * 
 * @author ae187
 *
 */

public class Game {

	/**
	 * Constants representing different game states
	 * 
	 */
	public static final int DRAW = 3;
	public static final int X_WON = 1;
	public static final int O_WON = 2;
	public static final int ONGOING = 0;

	/**
	 * this integer represents the state of the game, and is set by
	 * {@link #evaluateGameState()} after every move DO NOT CONFUSE THIS with an MDP
	 * state.
	 */
	int state = 0;

	char[][] board = new char[3][3];

	/**
	 * the X agent
	 */
	Agent x;

	/**
	 * the O agent
	 */
	Agent o;

	Agent whoseTurn;

	/**
	 * new game with new X and O agents with null policies
	 */
	public Game() {
		this(new Agent(), new Agent());

	}

	/**
	 * New game where x plays first
	 * 
	 * @param x
	 * @param o
	 */
	public Game(Agent x, Agent o) {
		this(x, o, x);
	}

	/**
	 * A new game where whoseTurn starts the game.
	 * 
	 * @param whoseTurn either 'X' or 'O'
	 */
	public Game(char whoseTurn) {
		this(new Agent(), new Agent());
		if (whoseTurn != 'X' && whoseTurn != 'O')
			throw new IllegalArgumentException("Argument should be either 'X' or 'O'");
		else if (whoseTurn == 'X')
			this.whoseTurn = x;
		else
			this.whoseTurn = o;

	}

	/**
	 * New game with X and O agents where it's whoseTurn's turn to play.
	 * 
	 * @param x
	 * @param o
	 * @param whoseTurn
	 */
	public Game(Agent x, Agent o, Agent whoseTurn) {
		this.x = x;
		x.setName('X');

		this.o = o;
		o.setName('O');
		if (whoseTurn != x && whoseTurn != o)
			throw new IllegalArgumentException("Agent with current turn is not one of the game agents");

		this.whoseTurn = whoseTurn;

		initBoard();

	}

	/**
	 * To deep copy
	 * 
	 */
	public Game(Game g) {
		this.x = g.x;
		this.o = g.o;
		this.whoseTurn = g.whoseTurn;
		// WARNING: Currently Agents are not deep copied

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.board[i][j] = g.board[i][j];

	}

	public void initBoard() {
		// fill the board with e's (empty cells)
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = ' ';

	}

	public boolean isLegal(Move m) {
		if (m.x < 0 || m.x > 2 || m.y < 0 || m.y > 2)
			return false;

		if (m.who.getName() != whoseTurn.getName()) {
			return false;
		}

		if (board[m.x][m.y] != ' ')
			return false;

		return true;
	}

	/**
	 * 
	 * @return All possible next game states from the current game state in one step
	 *         where the agent whose turn it is moves.
	 * 
	 */
	public List<Game> getAllSuccessorGames() {
		List<Game> result = new ArrayList<Game>();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ') {
					Game newGame = null;
					try {
						newGame = this.simulateMove(whoseTurn.getName(), i, j);
					} catch (IllegalMoveException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					// System.out.println("Adding:\n"+newGame);
					result.add(newGame);
				}
			}

		return result;

	}

	/**
	 * A static method to generate all valid game states where it is either X's turn
	 * or O's turn as specified by {@code xo} argument. NOTE: this method will
	 * include ALL terminal states in the game.
	 * 
	 * @param xo specifies whose turn it should be in the resulting games.
	 * @return
	 */
	public static List<Game> generateAllValidGames(char xo) {
		List<Game> result = new ArrayList<Game>();
		// this is the number of possible game hashes (2222222222 in base 3): 59048 -
		// not all are valid. Here we only return the valid ones.
		for (int i = 0; i < 59048; i++) {
			Game g = inverseHash(i);
			if (g == null)
				continue;

			if (g.isValid()) {
				// System.out.println("for:"+i);
				// System.out.println("got:\n"+g);
				// System.out.println("turn:"+g.whoseTurn.getName());
				// System.out.println("--------");
				g.evaluateGameState();
				if (g.whoseTurn.getName() == xo || g.isTerminal())
					result.add(g);
			}

		}

		return result;
	}

	/**
	 * returns a list of all available moves by the agent whose turn it is.
	 * 
	 * @return
	 */
	public List<Move> getPossibleMoves() {
		List<Move> possibleMoves = new ArrayList<Move>();
		// if it's not our turn then no moves possible.
		if (getState() != ONGOING)
			return possibleMoves;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (getBoard()[i][j] == ' ')
					possibleMoves.add(new Move(whoseTurn, i, j));
			}

		return possibleMoves;

	}

	/**
	 * returns a list of all available moves by the agent whose turn it is NOT. Used
	 * by the defensive agent.
	 * 
	 * @return
	 */
	public List<Move> getPossibleMovesByOpponent() {
		List<Move> possibleMoves = new ArrayList<Move>();
		// if it's not our turn then no moves possible.
		if (getState() != ONGOING)
			return possibleMoves;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (getBoard()[i][j] == ' ')
					possibleMoves.add(new Move((whoseTurn.getName() == 'X') ? 'O' : 'X', i, j));
			}

		return possibleMoves;

	}

	public char[][] getBoard() {
		return board;
	}

	public String toString() {
		String result = "\n";
		for (int i = 0; i < 3; i++) {
			result += "|";
			for (int j = 0; j < 3; j++) {
				result += board[i][j] + "|";

			}
			result += "\n";

		}
		// result+=this.whoseTurn+"'s turn";

		return result;
	}

	/**
	 * Evaluates the game, and sets its state to one of DRAW, ONGOING, X_WIN or
	 * O_WIN
	 * 
	 * @return an integer representing the game state
	 */
	public int evaluateGameState() {

		// checking columns
		if (board[0][0] != ' ' && board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
			if (board[0][0] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}
		if (board[1][0] != ' ' && board[1][0] == board[1][1] && board[1][1] == board[1][2]) {
			if (board[1][0] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}
		if (board[2][0] != ' ' && board[2][0] == board[2][1] && board[2][1] == board[2][2]) {
			if (board[2][0] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}

		// checking rows

		if (board[0][0] != ' ' && board[0][0] == board[1][0] && board[1][0] == board[2][0]) {
			if (board[0][0] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}
		if (board[0][1] != ' ' && board[0][1] == board[1][1] && board[1][1] == board[2][1]) {
			if (board[0][1] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}
		if (board[0][2] != ' ' && board[0][2] == board[1][2] && board[1][2] == board[2][2]) {
			if (board[0][2] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}

		// checking diagnols

		if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[0][0] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}

		if (board[2][0] != ' ' && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
			if (board[2][0] == 'X')
				this.state = X_WON;
			else
				this.state = O_WON;

			return this.state;

		}

		boolean spacesLeft = false;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ') {
					spacesLeft = true;
					break;

				}
			}

		if (spacesLeft)
			this.state = ONGOING;
		else
			this.state = DRAW;

		return this.state;

	}

	private int count(char xo) {
		int count = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (board[i][j] == xo)
					count++;
		return count;
	}

	/**
	 * Tells us if this game is a valid game.
	 * 
	 * @return
	 */
	public boolean isValid() {
		int xs = count('X');
		int os = count('O');
		if (Math.abs(xs - os) > 1)
			return false;

		if (xs > os && this.whoseTurn.getName() == 'X')
			return false;
		else if (xs < os && this.whoseTurn.getName() == 'O')
			return false;

		return true;

	}

	/**
	 * Executes move on the current game. Returns the resulting game without
	 * changing the current one.
	 * 
	 * @param who the agent making move (X or O)
	 * @param x   the x coordinate of the move (between 0 and 2 inclusive)
	 * @param y   the y coordinate of the move
	 * @return
	 */
	public Game simulateMove(char who, int x, int y) throws IllegalMoveException {
		Move m = new Move(who, x, y);
		return simulateMove(m);

	}

	/**
	 * Executes the move m on the current game. Returns the resulting game without
	 * changing the current one.
	 * 
	 * @param m
	 * @return the Game after the move has been executed.
	 */
	public Game simulateMove(Move m) throws IllegalMoveException {
		if (whoseTurn == x && m.who.getName() != 'X')
			throw new IllegalMoveException("it is not x's turn");

		if (whoseTurn == o && m.who.getName() != 'O')
			throw new IllegalMoveException("it is not o's turn");

		if (board[m.x][m.y] != ' ')
			throw new IllegalMoveException("Invalid move. The square is " + m);

		Game copy = clone();
		copy.board[m.x][m.y] = m.who.getName();
		if (m.who.getName() == 'X')
			copy.whoseTurn = copy.o;
		else if (m.who.getName() == 'O')
			copy.whoseTurn = copy.x;
		else
			throw new IllegalArgumentException();

		copy.evaluateGameState();

		return copy;

	}

	/**
	 * Executes the move m. This will change the game.
	 * 
	 * @param m
	 */
	public void executeMove(Move m) throws IllegalMoveException {
		if (whoseTurn == x && m.who.getName() != 'X')
			throw new IllegalMoveException("it is not O's turn");

		if (whoseTurn == o && m.who.getName() != 'O')
			throw new IllegalMoveException("it is not X's turn");

		if (board[m.x][m.y] != ' ')
			throw new IllegalMoveException("Invalid move. The location (" + m.x + "," + m.y + ") is not empty");

		board[m.x][m.y] = m.who.getName();
		if (whoseTurn.getName() == 'X')
			whoseTurn = o;
		else if (m.who.getName() == 'O')
			whoseTurn = x;
		else
			throw new IllegalArgumentException();

		this.evaluateGameState();

	}

	/**
	 * Executes move on the current game. Returns the resulting game without
	 * changing the current one.
	 * 
	 * @param who the agent making move (X or O)
	 * @param x   the x coordinate of the move (between 0 and 2 inclusive)
	 * @param y   the y coordinate of the move (between 0 and 2 inclusive)
	 */
	public void executeMove(char who, int x, int y) throws IllegalMoveException {
		Move m = new Move(who, x, y);
		executeMove(m);

	}

	/**
	 * 
	 * /** This method will play out the game to the end using moves from the X and
	 * O agents. Use this method to test your agents
	 * 
	 * Each step is pretty printed.
	 */
	public void playOut() throws IllegalMoveException {

		while (this.state == ONGOING) {

			Move m = this.whoseTurn.getMove(this);
			// if (!(this.whoseTurn instanceof ValueIterationAgent))
			// {
			// System.out.println("----successors----");
			// for(Game g: this.getAllSuccessorGames())
			// {
			//
			// ValueIterationAgent a=(ValueIterationAgent)this.x;
			// System.out.println("Game:"+g);
			// System.out.println("Game Value="+a.valueFunction.get(g));
			// }
			// System.out.println("---------");
			// }

			System.out.println("Playing move: " + m);
			executeMove(m);
			System.out.println(this);

		}
		if (this.state == X_WON) {
			System.out.println("X won!");
		} else if (this.state == O_WON) {
			System.out.println("O won!");
		} else
			System.out.println("It's a draw.");

	}

	/**
	 * Deep copy
	 */
	public Game clone() {
		return new Game(this);
	}

	/**
	 * A hashCode function implemented assuming the game board is a 10 digit number
	 * in base three, where: ' ' maps to 0 'X' maps to 1 'O' maps to 2
	 * 
	 * 10 digits instead of 9 because we used the last, 10th one to encode whose
	 * turn it is.
	 * 
	 * The returned value is essentially that number converted to base 10.
	 * 
	 * You can use this method to store (e.g. write to file) your value functions or
	 * policies.
	 * 
	 * @return an integer representation of the game
	 */
	public int hashCode() {
		// ' ' ->0
		// 'X' ->1
		// 'O' ->2
		String s = "";
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				switch (board[i][j]) {
				case 'X':
					s += "1";
					break;
				case 'O':
					s += "2";
					break;
				case ' ':
					s += "0";
					break;
				}

			}
		// also encode whose turn it is. Either 1 or 2 (x or o)

		s += (whoseTurn == x) ? "1" : "2";

		return Integer.parseInt(s, 3);

	}

	/**
	 * Given a hash value for a game according to {@link Game#hashCode} this method
	 * turns it back into a Game object, i.e. it is the inverse of the hashCode
	 * method.
	 * 
	 * You can use this method to read your policy or value functions from file.
	 * 
	 * @param hash
	 * @return the Game corresponding to the hash
	 */
	public static Game inverseHash(int hash) {

		Game g = new Game();

		String s1 = Integer.toString(hash, 3);

		if (s1.charAt(s1.length() - 1) == '0')
			return null;

		// add leading 0's
		String s = new String(s1);

		for (int i = 0; i < 10 - s1.length(); i++) {
			s = "0" + s;
		}
		// System.out.println("Intermediate string:"+s);
		// set whose turn
		if (s.charAt(s.length() - 1) == '1')
			g.whoseTurn = g.x;
		else if (s.charAt(s.length() - 1) == '2')
			g.whoseTurn = g.o;

		for (int l = s.length() - 2; l >= 0; l--) {
			int i = (int) l / 3;
			int j = (int) l % 3;
			switch (s.charAt(l)) {
			case '0':
				g.board[i][j] = ' ';
				break;
			case '1':
				g.board[i][j] = 'X';
				break;
			case '2':
				g.board[i][j] = 'O';
				break;
			default:
				throw new IllegalStateException("this shouldn't happen!");

			}
		}

		return g;
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		else if (!(other instanceof Game))
			return false;

		return this.hashCode() == other.hashCode();
	}

	public int getState() {
		return this.state;
	}

	public boolean isTerminal() {

		return this.state != ONGOING;
	}

	public static final String help = " -h this menu" + "\n -x the X agent: -x <pi, vi, random, agg, def, human>"
			+ "\n -o the O agent: -o <random, agg, def, human>"
			+ "\n -s the agent that starts the game (x or o): -s <x or o>";

	// public static void main(String args[]) throws IllegalMoveException
	// {

	// Game g=new Game();
	// TTTMDP mdp=new TTTMDP();
	//
	// g.executeMove('X', 0, 0);
	// g.executeMove('O', 1, 0);
	// g.executeMove('X', 0, 2);
	// g.executeMove('O', 1, 1);
	//
	// List<TransitionProb> transitions=mdp.generateTransitions(g, new Move('X', 0,
	// 1));
	// for(TransitionProb t:transitions)
	// {
	// System.out.println(t);
	// }

	// DEBUG valid games----
	// List<Game> games=generateAllValidGames('X');
	// for(Game g: games)
	// {
	// System.out.println(g);
	// System.out.println(g.whoseTurn.getName());
	// System.out.println("=============");
	// }
	// System.out.println("there were:"+games.size());
	// }

	/**
	 * 
	 * @param a
	 * @throws IllegalMoveException of the there is an illegal move by one of the
	 *                              agents. This would just exit the programme.
	 */
	public static void main(String a[]) throws IllegalMoveException {
		List<String> args = Arrays.asList(a);
		if (args.contains("-h")) {
			System.out.println(help);
			return;
		}

		Agent x = null;
		Agent o = null;
		String whoseTurn = null;
		Iterator<String> iter = args.iterator();

		while (iter.hasNext()) {
			String cur = iter.next();
			String next = null;
			switch (cur) {
			case "-x":
				next = iter.next();
				if (next == null || next.startsWith("-")) {
					System.out.println("-x should be followed by the agent name, vi, pi, random, agg, def, or human");
					return;
				}
				if (next.equals("vi")) {
					System.out.println("x is vi agent.");
					x = new ValueIterationAgent();
				} else if (next.equals("pi")) {
					System.out.println("X is pi agent.");
					x = new PolicyIterationAgent();
				} else if (next.equals("random")) {
					System.out.println("X is random agent.");
					x = new RandomAgent();
				} else if (next.equals("human")) {
					System.out.println("X is human agent.");
					x = new HumanAgent();
				} else if (next.equals("agg")) {

					System.out.println("X is aggressive agent.");
					x = new AggressiveAgent();
				} else if (next.equals("def")) {
					System.out.println("X is defensive agent.");
					x = new DefensiveAgent();
				} else if (next.equals("ql")) {
					System.out.println("X is q learning agent.");
					x = new QLearningAgent();
				} else {
					System.out.println("-x should be followed by the agent type: vi, pi, ql, random or human");
					return;

				}
				break;
			case "-o":
				next = iter.next();

				if (next == null || next.startsWith("-")) {
					System.out.println("-o should be followed by the agent name e.g. random or human");
					return;
				}
				if (next.equals("vi")) {
					System.out.println("Error: the value iteration agent should be the X agent");
					return;
				}

				else if (next.equals("pi")) {
					System.out.println("Error: the policy iteration agent should be the X agent");
					return;
				} else if (next.equals("random"))
					o = new RandomAgent();
				else if (next.equals("human")) {
					System.out.println("O is human agent");
					o = new HumanAgent();
				} else if (next.equals("agg")) {

					System.out.println("O is aggressive agent.");
					o = new AggressiveAgent();
				} else if (next.equals("def")) {
					System.out.println("O is defensive agent.");
					o = new DefensiveAgent();
				} else if (next.equals("ql")) {
					System.out.println("O is q learning agent.");
					o = new QLearningAgent();
				} else {
					System.out.println("Error: -x should be followed by the agent type: vi, pi, random or human");
					return;

				}
				break;
			case "-s":
				next = iter.next();
				if (next == null) {
					System.out.println("Error: -s should be followed by the agent that starts the game: X or O");
					return;
				} else if (next.equalsIgnoreCase("x"))
					whoseTurn = "x";
				else if (next.equalsIgnoreCase("o"))
					whoseTurn = "o";
				else {
					System.out.println("Error: -s should be followed by the agent that starts first: x or o");
					return;
				}
				break;

			}

		}

		if (x == null && o == null) {
			x = new HumanAgent();
			o = new RandomAgent();
			System.out.println("X is human agent.");
			System.out.println("O is random agent.");
		} else if (x == null) {
			System.out.println("X is random agent");
			x = new RandomAgent();
		} else if (o == null) {
			System.out.println("O is random agent.");
			o = new RandomAgent();
		}

		Game g;
		if (whoseTurn == null || whoseTurn.equals("x"))
			g = new Game(x, o);
		else
			g = new Game(x, o, o);

		g.playOut();

	}

}
