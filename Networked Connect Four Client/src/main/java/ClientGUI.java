
import javafx.application.Application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;

import java.util.Random;

import java.util.HashMap;
import java.util.ArrayList;







import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

public class ClientGUI extends Application {

	private HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
	private Client clientConnection;
	private ListView<String> movesList = new ListView<String>();
	private TextField ipTxtField, portTxtField;
	private Label turn;
	private Button connectBtn, playAgainBtn, exitBtn, ipShortCut, portShortCut;
	private GridPane gameBoard;
	private GameButton gbArr[][] = new GameButton[6][7];
	private EventHandler<ActionEvent> gameButtonHandler;
	private VBox gameSceneRoot;
	private HBox boardAndInfo;
	private Label title;
	private int playerNumber = -1;
	private CFourInfo info;
	private int port = -1;
	private String ip = "";
	private String winner;
	private Image player1Color = new Image("red.png");
	private Image defaultColor = new Image("grey.png");
	private Image player2Color = new Image("yellow.png");
	private ImageView playerColorImageView;
	private boolean started = false;
	private PauseTransition finalScenePause = new PauseTransition(Duration.seconds(4));
	private Label winnerLabel, winOrLoseLbl;
	static boolean playerOne = false; // This is another way 

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		gameButtonHandler = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				GameButton b = (GameButton)e.getSource();
				
				if (info.getHaveWinner()) {
					// Don't do anything

				} else if (!validateMove(b)) { 

					movesList.getItems().add("Invalid move");

					int row = GridPane.getRowIndex(b);
					int col = GridPane.getColumnIndex(b);

					if (row != 5 && !gbArr[row][col].wasPressed()) {
						movesList.getItems().add("Button is not at the bottom");
					} else if (b.wasPressed()) {
						movesList.getItems().add("Button is already pressed");
					}

				} else {	// Button b has not been pressed

					if (playerNumber == 1) { 
						b.setColor("red");
						turn.setText("Player 2");
					} else if (playerNumber == 2){
						b.setColor("yellow");
						turn.setText("Player 1");
					}

					b.setWhoPressed(playerNumber);
					b.setPressed();	
					int row = GridPane.getRowIndex(b);
					int col = GridPane.getColumnIndex(b);

					movesList.getItems().add("You moved: (" + row + "," + col + ")");

					int move[] = {row,col};

					// Note: buttons will populate if there is a winner
					int buttons[][] = ConnectFourLogic.checkForWinner(gbArr, playerNumber);	// check if we have a winner

					if (buttons[0][0] != -1) { // we have a winner
						
						GameButton[] winnerButtons = getWinnerButtons(buttons);
						highlightWinner(winnerButtons);
						movesList.getItems().add("Congratulations... YOU WON!");

						info.setWinner(playerNumber);
						winner = "Player " + playerNumber;
						winOrLoseLbl.setText("YOU WON!");
						info.setHaveWinner(true);
						info.setWinnerButtons(buttons);
						finalScenePause.play();		

					} else {
						gameBoard.setDisable(true);
					}

					info.setWhoPlayed(playerNumber);
					System.out.println("Setting move: " + "(" + move[0] + "," + move[1] + ")");
					info.setMove(move); 
					System.out.println("Player " + playerNumber + " is about to call send with: (" + info.getMove()[0] + "," + info.getMove()[1] +")");
					System.out.println(info.toString());
					System.out.println("clientConnection Address: " +  Integer.toHexString(System.identityHashCode(clientConnection)));
					info.receivedFromAddress = Integer.toHexString(System.identityHashCode(clientConnection));
					clientConnection.send(info);
				}
			}
		};

		sceneMap.put("welcomeScene", createWelcomeScene());
		sceneMap.put("gameScene", createGameScene());
		sceneMap.put("finalScene", createWinnerScene());
		sceneMap.put("exitScene", createExitScene());

		connectBtn.setOnAction(e-> {
			
			if (ip == "") {
				ip = ipTxtField.getText();
			}
			
			if (port == -1) {
				port = Integer.parseInt(portTxtField.getText());
			}
			
			clientConnection = new Client(object-> {	
				Platform.runLater(()-> {

					info = (CFourInfo) object;

					if (!started) { // Only gets executed once

						if (info.getNumberOfPlayers() == 1) {

							movesList.getItems().add("Waiting for player 2 to join...");
							initializePlayerInfo(primaryStage);

							gameBoard.setDisable(true);

						}  else {  // info.getNumberOfPlayers() == 2

							if (playerNumber != 1) {
								initializePlayerInfo(primaryStage);
							}

							if (playerNumber == 1) {
								movesList.getItems().clear();
								gameBoard.setDisable(false);
							}
							turn.setText("Player 1");
							started = true;
						}

					} else {  // We have started

						int row = info.getMove()[0];
						int col = info.getMove()[1];
						GameButton b = gbArr[row][col];
						b.setPressed();

						int whoMoved = info.getWhoPlayed();

						if (playerNumber == 1) { 
							b.setColor("yellow");
							turn.setText("Player 1");
						} else if (playerNumber == 2) {
							b.setColor("red");
							turn.setText("Player 2");
						}
						movesList.getItems().add("Player " + whoMoved + " moved: (" + row + "," + col + ")");

						if (info.getHaveWinner()) {

							if (playerNumber == 1) {
								winner = "Player " + 2;
							} else if (playerNumber == 2) {
								winner = "Player " + 1;
							}
							
							movesList.getItems().add("YOU LOST! :(");
							winOrLoseLbl.setText("YOU LOST!");
							GameButton[] winnerButtons = getWinnerButtons(info.getWinnerButtons());
							highlightWinner(winnerButtons);
							finalScenePause.play();	
						} 
						
						gameBoard.setDisable(false);
					}
				});
			}, ip, port);

			primaryStage.setScene(sceneMap.get("gameScene"));
			clientConnection.start();		
		});
		
		exitBtn.setOnAction(e -> {
			System.out.println("ATTEMPTING TO EXIT, PLAYER " + playerNumber );
			primaryStage.setScene(sceneMap.get("exitScene"));
			
			if (playerNumber == 1) {
				CFourInfo info = new CFourInfo();
				
				System.out.println("setting player 1 exiited");
				info.setPlayer1Exited();
				info.setWinner(999999999);
				clientConnection.send(info);
			} else if (playerNumber == 2) {
				CFourInfo info = new CFourInfo();
				System.out.println("setting player 2 exiited");
				info.setPlayer2Exited();
				clientConnection.send(info);
			}
		});

		playAgainBtn.setOnAction(e-> {
			
			reset();
			movesList.getItems().clear();
			
			primaryStage.setScene(sceneMap.get("gameScene"));

			if (playerNumber == 2) {
				gameBoard.setDisable(true);
			}
			
			turn.setText("Player 1");
			
			//THIS WORKS:
			/*
			info = new CFourInfo();
			info.setConnectedToServer(true);
			info.setNumberOfPlayers(2);
			
			*/

			
			
			// DOES NOT WORK.
			/*
			info.setWhoPlayed(-1);
			info.setMove(null);
			info.setHaveWinner(false);
			info.setHaveTie(false);
			info.setWinner(-1);
			info.setPlayerNumber(-1);
			info.setConnectedToServer(false);
			info.setNumberOfPlayers(0);

			info.setConnectedToServer(true);
			info.setNumberOfPlayers(2);
			 
*/
			
			
			

			// DOES NOT WORK
			
			
			info.setWinner(-1);
			info.setHaveWinner(false);
			//info.setMove(null);
			//info.playAgainConfig();	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			System.out.println("Play again button pressed, and the object is: ");
			System.out.println(info.toString());		
		});
		
		ipShortCut.setOnAction(e-> {
			ip = "127.0.0.1";
			ipShortCut.setDisable(true);
			ipTxtField.setDisable(true);
		});
		
		portShortCut.setOnAction(e-> {
			port = 5555;		
			portShortCut.setDisable(true);
			portTxtField.setDisable(true);
		});
		
		ipTxtField.setOnKeyPressed(e-> {
			
			if (e.getCode().equals(KeyCode.ENTER)){
				ip = ipTxtField.getText();
				ipTxtField.setDisable(true);
				ipShortCut.setDisable(true);
			}	
		});
		
		portTxtField.setOnKeyPressed(e-> {
			
			if (e.getCode().equals(KeyCode.ENTER)){
				
				port = Integer.parseInt(portTxtField.getText());
				portTxtField.setDisable(true);
				portShortCut.setDisable(true);	
			}	
		});
		
		finalScenePause.setOnFinished(e-> {
			primaryStage.setScene(sceneMap.get("finalScene"));
			winnerLabel.setText("Winner: " + winner);
		});

		primaryStage.setScene(sceneMap.get("welcomeScene"));
		primaryStage.setTitle("Connect Four Game - Welcome");
		primaryStage.show();	
	}

	public boolean validateMove(GameButton b) {
		System.out.println("Validating button:" + "(" + b.getRowCol() + ")");

		int row = GridPane.getRowIndex(b);
		int col = GridPane.getColumnIndex(b);

		if (row == 5 && !b.wasPressed()) {  // if button is in the last row and it was not pressed
			return true;

		} else if (row < 5 && !b.wasPressed() && gbArr[row + 1][col].wasPressed()) {
			// if button is NOT in the last row AND button is NOT pressed AND button below was pressed
			return true;
		} else {
			return false;
		}
	}

	public GameButton[] getWinnerButtons(int btns[][]) {
		GameButton[] winnerButtons = new GameButton[4];
		winnerButtons[0] = gbArr[ btns[0][0] ][ btns[0][1] ];
		winnerButtons[1] = gbArr[ btns[1][0] ][ btns[1][1] ];
		winnerButtons[2] = gbArr[ btns[2][0] ][ btns[2][1] ];
		winnerButtons[3] = gbArr[ btns[3][0] ][ btns[3][1] ];
		return winnerButtons;
	}

	public void highlightWinner(GameButton array[]) {
		array[0].setColor("blue");
		array[1].setColor("blue");
		array[2].setColor("blue");
		array[3].setColor("blue");
	}

	public void disableAllButtons() {
		for(int col = 0; col < 7; col++) {
			for(int row = 0; row < 6; row++) {
				gbArr[row][col].setPressed();
			}
		}
	}
	
	/*
	 * Create 6x7 grid
	 */

	public GridPane createGrid() {

		gameBoard = new GridPane();

		for(int col = 0; col < 7; col++) {
			for(int row = 0; row < 6; row++) {
				GameButton b = new GameButton(Integer.toString(row) + "," + Integer.toString(col) , "gray");

				//b.setMaxSize(60, 60);
				b.setPrefSize(60, 60);
				gbArr[row][col] = b;
				b.setOnAction(gameButtonHandler);
				gameBoard.add(b, col, row);    // Adds column, row
			}
		}
		gameBoard.setHgap(5);
		gameBoard.setVgap(5);
		gameBoard.setAlignment(Pos.CENTER);
		return gameBoard;
	}

	public void initializePlayerInfo(Stage primaryStage) {

		if (info.conectionEstablished()) { // Set board

			playerNumber = info.getPlayerNumber();
			System.out.println("Connection established for player " + playerNumber);
			title.setText("Player " + playerNumber);

			if (playerNumber == 1) { 
				playerColorImageView.setImage(player1Color);
			} else if (playerNumber == 2){
				playerColorImageView.setImage(player2Color);
				gameBoard.setDisable(true);
			}

			primaryStage.setTitle("Connect Four Game - Player " + playerNumber);

		} else {
			title.setText("Connection with server failed");
			boardAndInfo.setDisable(true);
		}
	}

	public Scene createWelcomeScene() {

		Label welcome = new Label("    Welcome to the \n Connect Four Game!");
		welcome.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");


		Label ipLbl = new Label("Enter the server's ip address: ");
		ipTxtField = new TextField("");
		
		ipShortCut = new Button("Use ip 127.0.0.1");

		Label portLbl = new Label("Enter port number: ");
		portTxtField = new TextField("");
		
		portShortCut = new Button("Use port 5555   ");

		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);

		grid.add(ipLbl,0,0);
		grid.add(ipTxtField,1,0);
		
		grid.add(ipShortCut,3,0);
		
		grid.add(portLbl,0,1);
		grid.add(portTxtField,1,1);
		
		grid.add(portShortCut,3,1);
		
		

		grid.setAlignment(Pos.CENTER);

		connectBtn = new Button("Connect to Server");

		VBox vbox = new VBox(welcome, grid, connectBtn);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setStyle("-fx-background-color: #ABECF8");;

		VBox.setMargin(welcome, new Insets(30));
		VBox.setMargin(connectBtn, new Insets(30));
		return new Scene(vbox, 800, 500);
	}

	public Scene createGameScene() {

		title = new Label("TBD");
		title.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		Label turnLbl = new Label("Turn:");
		turnLbl.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		turn = new Label("");
		turn.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		HBox turnHBox = new HBox(turnLbl, turn);
		turnHBox.setSpacing(15);
		turnHBox.setAlignment(Pos.CENTER);

		Label movesLbl = new Label("Moves:");
		movesLbl.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		createGrid();


		Label color = new Label("Your color: ");
		color.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		playerColorImageView = new ImageView(defaultColor);
		playerColorImageView.setFitHeight(30);
		playerColorImageView.setFitWidth(30);
		HBox colorHbox = new HBox(color, playerColorImageView);
		colorHbox.setAlignment(Pos.CENTER);
		colorHbox.setSpacing(20);


		VBox info = new VBox(turnHBox, colorHbox, movesLbl, movesList);

		VBox.setMargin(movesLbl, new Insets(20,0,0,0));

		info.setAlignment(Pos.CENTER);
		VBox.setMargin(movesList, new Insets(20));

		movesList.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 12;");

		boardAndInfo = new HBox(info, gameBoard);
		HBox.setMargin(gameBoard, new Insets(20));


		gameSceneRoot = new VBox(title, boardAndInfo);

		VBox.setMargin(title, new Insets(30));
		gameSceneRoot.setAlignment(Pos.CENTER);
		gameSceneRoot.setStyle("-fx-background-color: #ABECF8");
		return new Scene(gameSceneRoot, 800, 500);
	}



	public Scene createWinnerScene() {

		winnerLabel = new Label("TBD");
		winnerLabel.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 20;");

		winOrLoseLbl = new Label("TBD");
		winOrLoseLbl.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 30;");
		
		playAgainBtn = new Button("Play Again");
		playAgainBtn.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");

		exitBtn = new Button("Exit");
		exitBtn.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");

		GridPane grid = new GridPane();
		grid.add(playAgainBtn, 0, 0);
		grid.add(exitBtn, 1, 0);
		grid.setHgap(30);
		grid.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(winnerLabel, winOrLoseLbl, grid);
		VBox.setMargin(winnerLabel, new Insets(30));
		VBox.setMargin(winOrLoseLbl, new Insets(30));
		vbox.setAlignment(Pos.CENTER);

		vbox.setStyle("-fx-background-color: #ABECF8");
		return new Scene(vbox, 800, 500);
	}
	
	public Scene createExitScene() {

		Label thankYou = new Label("Thanks for playing!");
		thankYou.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 20;");

		VBox vbox = new VBox(thankYou);

		vbox.setAlignment(Pos.CENTER);

		vbox.setStyle("-fx-background-color: #ABECF8");
		return new Scene(vbox, 800, 500);
	}
	
	public void reset() {

		for(int row = 0; row < 6; row++) {
			for(int col = 0; col < 7; col++) {
				gbArr[row][col].reset();
			}
		}
	}
}