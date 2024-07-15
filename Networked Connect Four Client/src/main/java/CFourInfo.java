import java.io.Serializable;


public class CFourInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int whoPlayed;
	private int move[] = new int[2];
 	private boolean haveWinner;
 	private boolean haveTie;
 	private int winner;
 	private int playerNumber;
 	private int [][] winnerButtons = new int[4][2];
 	private boolean connectedToServer;
 	private int numberOfPlayers;
 	private boolean player1Exited = false;
	private boolean player2Exited = false;
 	String receivedFromAddress = "";
	
 	public CFourInfo () {
 		whoPlayed = -1;
 		move = null;
 		haveWinner = false;
 		haveTie = false;
 		winner = -1;
 		playerNumber = -1;
 		connectedToServer = false;
 		numberOfPlayers = 0;
 	}
 	
 	public CFourInfo (int whoPlayed, int move[], boolean haveWinner, boolean haveTie, int winner, int playerNumber) {
 		this.whoPlayed = whoPlayed;
 		this.move = move;
 		this.haveWinner = haveWinner;
 		this.haveTie = haveTie;
 		this.winner = winner;
 		this.playerNumber = playerNumber;
 	}
 	
 	public void playAgainConfig() {
 		//whoPlayed = -1;
 	//	move = null;
 		haveWinner = false;
 		haveTie = false;
 		winner = -1;
 		//playerNumber = -1;  // Don't reset this
 		//connectedToServer = false;
 		//numberOfPlayers = 0;
 	}
 	
 	public int getWhoPlayed() {
 		return whoPlayed;
 	}
 	
 	public void setWhoPlayed(int whoPlayed) {
 		this.whoPlayed = whoPlayed;
 	}
 	
	public int[] getMove() {
		//System.out.println("Printing MOVE:" + move[0] + "," +   move[1]);
 		return move;
 	}
 	
 	public void setMove(int move[]) {
 		this.move = move;
 	}
 	
 	public boolean getHaveWinner() {
 		return haveWinner;
 	}
 	
 	public void setHaveWinner(boolean haveWinner) {
 		this.haveWinner = haveWinner;
 	}
 	
 	public boolean getHaveTie() {
 		return haveTie;
 	}
 	
 	public void setHaveTie(boolean haveTie) {
 		this.haveTie = haveTie;
 	}
 	
 	public int getWinner() {
 		return winner;
 	}
 	
 	public void setWinner(int winner) {
 		this.winner = winner;
 	}
 	
 	public int getPlayerNumber() {
 		return playerNumber;
 	}
 	
	public void setPlayerNumber(int playerNumber) {
 		this.playerNumber = playerNumber;
 	}
	
 	public int[][] getWinnerButtons() {
 		return winnerButtons;
 	}
 	
	public void setWinnerButtons(int winnerButtons[][]) {
 		this.winnerButtons = winnerButtons;
 	}
	
	public boolean conectionEstablished() {
		return connectedToServer;
	}
	
	public void setConnectedToServer(boolean bool) {
		connectedToServer = bool;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public void setNumberOfPlayers(int nPlayers) {
		numberOfPlayers = nPlayers;
	}
	
	public boolean getPlayer1Exited() {
		return player1Exited;	
	}
	
	
	public boolean getPlayer2Exited() {
		return player2Exited;	
	}
	
	public void setPlayer1Exited() {
		player1Exited = !player1Exited;
	}
	
	public void setPlayer2Exited() {
		player2Exited = !player2Exited;
	}
	
	
	public String toString() {
		//System.out.println(this);
		
		String address = Integer.toHexString(System.identityHashCode(this));
		String allInfo = "Object information: \nAddress: " + address + "\n";
		
		allInfo = allInfo + "whoPlayed: " + whoPlayed + "\n";
		
		if (move == null) {
			allInfo = allInfo + "move: null\n";
		} else {
			allInfo = allInfo + "move: (" + move[0] + "," +  move[1] + ")\n";
		}
		allInfo = allInfo + "haveWinner: " + haveWinner + "\n";
		allInfo = allInfo + "haveTie: " + haveTie + "\n";
		allInfo = allInfo + "winner: " + winner + "\n";
		allInfo = allInfo + "playerNumber: " + playerNumber + "\n";
		allInfo = allInfo + "connectedToServer: " + connectedToServer + "\n";
		allInfo = allInfo + "numberOfPlayers: " + numberOfPlayers + "\n";
		allInfo = allInfo + "player1Exited: " + player1Exited + "\n";
		allInfo = allInfo + "player2Exited: " + player2Exited + "\n";
		allInfo = allInfo + "received from address: " + receivedFromAddress + "\n\n ";

		return allInfo;
	}
}
