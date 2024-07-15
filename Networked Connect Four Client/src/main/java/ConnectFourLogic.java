import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class ConnectFourLogic {

	public static int[][] checkForWinner(GameButton gbArr[][], int playerNumber) {

		//GameButton array[] = new GameButton[4]; 
		int winnerButtons[][] = new int[4][2]; 

		/*
		 * 	Winner buttons 2D array will follow the format:
		 * 
		 * 	-1 -1	(row, col) for button 1
		 * 	-1 -1   (row, col) for button 2
		 *  -1 -1	(row, col) for button 3
		 *  -1 -1	(row, col) for button 4
		 *  
		 */

		for (int row = 0; row < 4; row++) {		// Populate with -1
			for (int col = 0; col < 2; col++) {  
				winnerButtons[row][col] = -1;
			}
		}

		// Iterate Horizontally
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 4; col++) {  

				int whoPressed1 = gbArr[row][col].getWhoPressed();
				int whoPressed2 = gbArr[row][col + 1].getWhoPressed();
				int whoPressed3 = gbArr[row][col + 2].getWhoPressed();
				int whoPressed4 = gbArr[row][col + 3].getWhoPressed();

				if (whoPressed1 == playerNumber && whoPressed2 == playerNumber &&
						whoPressed3 == playerNumber && whoPressed4 == playerNumber) {

					return rowAndColArr(row, col, row, col + 1, row, col + 2, row, col + 3);	
				}
			}
		}

		// Iterate Vertically
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 7; col++) {  

				int whoPressed1 = gbArr[row][col].getWhoPressed();
				int whoPressed2 = gbArr[row + 1][col].getWhoPressed();
				int whoPressed3 = gbArr[row + 2][col].getWhoPressed();
				int whoPressed4 = gbArr[row + 3][col].getWhoPressed();

				if (whoPressed1 == playerNumber && whoPressed2 == playerNumber &&
						whoPressed3 == playerNumber && whoPressed4 == playerNumber) {

					return rowAndColArr(row, col, row + 1, col, row + 2, col, row + 3, col);	
				}
			}
		}

		// Weird Bug: If we get an Exception here, player X will not 
		// reach the code that sends the pressed info to player Y

		// Iterate Diagonally-Down
		for (int row = 0; row < 3; row++) {  
			for (int col = 0; col < 4; col++) {  

				int whoPressed1 = gbArr[row][col].getWhoPressed();
				int whoPressed2 = gbArr[row + 1][col + 1].getWhoPressed();
				int whoPressed3 = gbArr[row + 2][col + 2].getWhoPressed();
				int whoPressed4 = gbArr[row + 3][col + 3].getWhoPressed();

				if (whoPressed1 == playerNumber && whoPressed2 == playerNumber &&
						whoPressed3 == playerNumber && whoPressed4 == playerNumber) {

					return rowAndColArr(row, col, row + 1, col + 1, row + 2, col + 2, row + 3, col + 3);	
				}
			}
		}

		// Iterate Diagonally-Up
		for (int row = 3; row < 6; row++) {  
			for (int col = 0; col < 4; col++) {  

				int whoPressed1 = gbArr[row][col].getWhoPressed();
				int whoPressed2 = gbArr[row - 1][col + 1].getWhoPressed();
				int whoPressed3 = gbArr[row - 2][col + 2].getWhoPressed();
				int whoPressed4 = gbArr[row - 3][col + 3].getWhoPressed();

				if (whoPressed1 == playerNumber && whoPressed2 == playerNumber &&
						whoPressed3 == playerNumber && whoPressed4 == playerNumber) {
					return rowAndColArr(row, col, row - 1, col + 1, row - 2, col + 2, row - 3, col + 3);	
				}
			}
		}
		return winnerButtons;  // Will return 2D array filled up with -1's
	}

	public static int[][] rowAndColArr(int btn1Row, int btn1Col,
			int btn2Row, int btn2Col,
			int btn3Row, int btn3Col,
			int btn4Row, int btn5Col) {

		int[][] winnerButtons = new int[4][2];

		winnerButtons[0][0] = btn1Row;
		winnerButtons[0][1] = btn1Col;
		winnerButtons[1][0] = btn2Row;
		winnerButtons[1][1] = btn2Col;
		winnerButtons[2][0] = btn3Row;
		winnerButtons[2][1] = btn3Col;
		winnerButtons[3][0] = btn4Row;
		winnerButtons[3][1] = btn5Col;

		return winnerButtons;	
	}


	public static int[][] checkForWinner2(int board [][]) {

		//GameButton array[] = new GameButton[4]; 
		int winnerButtons[][] = new int[4][2]; 

		/*
		 * 	Winner buttons 2D array will follow the format:
		 * 
		 * 	-1 -1	(row, col) for button 1
		 * 	-1 -1   (row, col) for button 2
		 *  -1 -1	(row, col) for button 3
		 *  -1 -1	(row, col) for button 4
		 *  
		 */

		for (int row = 0; row < 4; row++) {		// Populate with -1
			for (int col = 0; col < 2; col++) {  
				winnerButtons[row][col] = -1;
			}
		}

		// Iterate Horizontally
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 4; col++) {  

				if (board[row][col] == 0 &&
						board[row][col + 1] == 0 &&
						board[row][col + 2] == 0 &&
						board[row][col + 3] == 0) {
					return rowAndColArr(row, col, row, col + 1, row, col + 2, row, col + 3);	
				}
			}
		}

		// Iterate Vertically
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 7; col++) {  

				if (board[row][col] == 0 &&
						board[row + 1][col] == 0 &&
						board[row + 2][col] == 0 &&
						board[row + 3][col] == 0) {
					return rowAndColArr(row, col, row + 1, col, row + 2, col, row + 3, col);	
				}
			}
		}

		// Weird Bug: If we get an Exception here, player X will not 
		// reach the code that sends the pressed info to player Y

		// Iterate Diagonally-Down
		for (int row = 0; row < 3; row++) {  
			for (int col = 0; col < 4; col++) {  

				if (board[row][col] == 0 &&
						board[row + 1][col + 1] == 0 &&
						board[row + 2][col + 2] == 0 &&
						board[row + 3][col + 3] == 0) {
					return rowAndColArr(row, col, row + 1, col + 1, row + 2, col + 2, row + 3, col + 3);
				}
			
			}
		}

		// Iterate Diagonally-Up
		for (int row = 3; row < 6; row++) {  
			for (int col = 0; col < 4; col++) {  

				if (board[row][col] == 0 &&
						board[row - 1][col + 1] == 0 &&
						board[row - 2][col + 2] == 0 &&
						board[row - 3][col + 3] == 0) {
					return rowAndColArr(row, col, row - 1, col + 1, row - 2, col + 2, row - 3, col + 3);	
				}
			}
		}
		return winnerButtons;  // Will return 2D array filled up with -1's
	}

	public static GameButton[][] createBoard(int array[][]) {

		GameButton gbArr[][] = new GameButton[7][6];

		for(int col = 0; col < 7; col++) {
			for(int row = 0; row < 6; row++) {
				GameButton b = new GameButton(Integer.toString(row) + "," + Integer.toString(col) , "gray");
				gbArr[row][col] = b;

				if (array[row][col] == 0) {
					gbArr[row][col].setPressed();
					gbArr[row][col].setWhoPressed(1);
				}
			}
		}
		return gbArr;
	}


}
