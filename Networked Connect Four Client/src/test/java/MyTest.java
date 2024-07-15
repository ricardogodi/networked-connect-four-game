import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

class MyTest {

	
	/*
	int board[][] = {{-1,-1,-1,-1,-1,-1, -1},
			 {-1,-1,-1,-1,-1, -1,-1},
			 {-1,-1,-1,-1, -1,-1,-1},
			 {-1,-1,-1, -1,-1,-1,-1},
			 {-1,-1,-1,-1,-1,-1,-1},
			 {-1,-1,-1,-1,-1,-1,-1}};
			 */
	
	
	// Test Diagonally for winner
	@Test
	void testWinner1() {
		int board[][] = {{-1,-1,-1,-1,-1,-1, 0},
						 {-1,-1,-1,-1,-1, 0,-1},
						 {-1,-1,-1,-1, 0,-1,-1},
						 {-1,-1,-1, 0,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1}};

		int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

		assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
	}
	
	// Test Diagonally for no winner
	@Test
	void testNoWinner1() {
		int board[][] = {{-1,-1,-1,-1,-1,-1, 0},
						 {-1,-1,-1,-1,-1, 0,-1},
						 {-1,-1,-1,-1, 0,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1}};

		int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

		assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
	}

	
	// Test Diagonally for winner
	@Test
	void testWinner2() {
		int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1, 0,-1},
						 {-1,-1,-1,-1, 0,-1,-1},
						 {-1,-1,-1, 0,-1,-1,-1},
						 {-1,-1, 0,-1,-1,-1,-1}};

		int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

		assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
	}
	
	// Test Diagonally for no winner
	@Test
	void testNoWinner2() {
		int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1,-1},
						 {-1,-1,-1,-1,-1,-1, 0},
						 {-1,-1,-1,-1,-1, 0,-1},
						 {-1,-1,-1,-1, 0,-1,-1}};

		int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

		assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
	}
	
	// Test Diagonally for winner
		@Test
		void testWinner3() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
					 		 {-1, 0,-1,-1,-1,-1,-1},
					 		 {-1,-1, 0,-1,-1,-1,-1},
					 		 {-1,-1,-1, 0,-1,-1,-1},
					 		 {-1,-1,-1,-1, 0,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Diagonally for no winner
		@Test
		void testNoWinner3() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
							 {-1, 0,-1,-1,-1,-1,-1},
							 {-1,-1, 0,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1, 0,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Horizontally for winner
		@Test
		void testWinner4() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1, 0, 0, 0, 0},
					 		 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Horizontally for no winner
		@Test
		void testNoWinner4() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1, 0, 0, 0,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		
		// Test Horizontally for winner
		@Test
		void testWinner5() {
			int board[][] = {{-1,-1,-1, 0, 0, 0, 0},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Horizontally for no winner
		@Test
		void testNoWinner5() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1, 0, 0, 0,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Vertically for winner
		@Test
		void testWinner6() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1, 0,-1,-1,-1},
					 		 {-1,-1,-1, 0,-1,-1,-1},
					 		 {-1,-1,-1, 0,-1,-1,-1},
					 		 {-1,-1,-1, 0,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Vertically for no winner
		@Test
		void testNoWinner6() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1, 0,-1,-1,-1,-1,-1},
							 {-1, 0,-1,-1,-1,-1,-1},
							 {-1, 0,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Vertically for winner
		@Test
		void testWinner7() {
			int board[][] = {{-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1,-1},
					 		 {-1,-1,-1,-1,-1,-1, 0},
					 		 {-1,-1,-1,-1,-1,-1, 0},
					 		 {-1,-1,-1,-1,-1,-1, 0},
					 		 {-1,-1,-1,-1,-1,-1, 0}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertNotEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}
		
		// Test Vertically for no winner
		@Test
		void testNoWinner7() {
			int board[][] = {{-1,-1, 0,-1,-1,-1,-1},
							 {-1,-1, 0,-1,-1,-1,-1},
							 {-1,-1, 0,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1},
							 {-1,-1,-1,-1,-1,-1,-1}};

			int winnerButtons [][]= ConnectFourLogic.checkForWinner2(board);

			assertEquals(-1, winnerButtons[0][0]); // If the first element in the array is a -1, there is NO WINNER.
		}

	}

