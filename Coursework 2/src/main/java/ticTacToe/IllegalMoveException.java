package ticTacToe;

/**
 * 
 * 
 * @author ae187
 *
 */
public class IllegalMoveException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5323071316835731348L;

	public IllegalMoveException(String message) {
		super(message);
	}

}
