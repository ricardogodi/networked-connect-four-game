import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerGUI extends Application{

	TextField portTxtField;
	Label numClientsTxtField, gamesPlayedTxtField, ply1ScoreTxtField,  ply2ScoreTxtField;
	Button startServerBtn, portShortCut;
	HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
	GridPane grid;
	VBox plyr1VBox;
	VBox plyr2VBox;
	
	int port = -1;
	int player1Score = 0;
	int player2Score = 0;
	int gamesPlayed = 0;
	Label listenPort;
	Server serverConnection;
	ListView<String> movesList;
	int numberOfPlayers = 0;
	boolean started = false;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		sceneMap.put("startScene", createStartScene());		
		sceneMap.put("serverScene", createServerScene());		

		portShortCut.setOnAction(e-> {
			port = 5555;
			portTxtField.setDisable(true);
			portShortCut.setDisable(true);
		});

		portTxtField.setOnKeyPressed(e -> {
			if(e.getCode().equals(KeyCode.ENTER)){

				port = Integer.parseInt(portTxtField.getText());
				portTxtField.setDisable(true);
				portShortCut.setDisable(true);
			}	
		});

		startServerBtn.setOnAction(e-> {

			primaryStage.setScene(sceneMap.get("serverScene"));

			if (port == -1) {
				port = Integer.parseInt(portTxtField.getText());
			}

			listenPort.setText("" + port);
		
			serverConnection = new Server( object-> {

				Platform.runLater(() ->  {

					CFourInfo info = (CFourInfo) object; 

					if (info.getPlayer1Exited()) {
						movesList.getItems().add("Player 1 disconnected.");
						plyr1VBox.setDisable(true);
					} else if (info.getPlayer2Exited()) {
						movesList.getItems().add("Player 2 disconnected.");
						plyr2VBox.setDisable(true);
					} else {
						
						numClientsTxtField.setText("" + info.getNumberOfPlayers());

						if (!started) {

							if (info.getNumberOfPlayers() == 2) { // we have two players
								started = true;
							} 

						} else {		
							System.out.println("CHECKING BRANCH");
							int whoMoved = info.getWhoPlayed();
							int row = info.getMove()[0];
							int col = info.getMove()[1];
							movesList.getItems().add("Player " + whoMoved + " moved: (" + row + "," + col + ")");
						}

						if (info.getHaveWinner()) {
							
							gamesPlayed++;
							gamesPlayedTxtField.setText("" + gamesPlayed);
							movesList.getItems().add("PLAYER " + info.getWinner() + " WON!");
							
							
							if (info.getWinner() == 1) {
								player1Score++;
								ply1ScoreTxtField.setText("" + player1Score);
							} else if (info.getWinner() == 2){
								player2Score++;
								ply2ScoreTxtField.setText("" + player2Score);
							}
						}
					}
				});

			}, port);
		});

		primaryStage.setScene(sceneMap.get("startScene"));
		primaryStage.setTitle("Connect Four Game - Server");
		primaryStage.show();
	}

	public Scene createStartScene () {

		Label serverLbl = new Label("Connect Four Server");
		serverLbl.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		Label prompt = new Label("Enter port to listen to: ");
		portTxtField = new TextField("");

		portShortCut = new Button("Use port 5555");

		HBox hbox = new HBox(prompt, portTxtField, portShortCut);
		HBox.setMargin(portShortCut, new Insets(0,0,0,40));
		hbox.setSpacing(30);
		hbox.setAlignment(Pos.CENTER);

		startServerBtn = new Button("Start Server");
		VBox vbox = new VBox(serverLbl, hbox, startServerBtn);

		VBox.setMargin(serverLbl, new Insets(30));

		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(30);
		vbox.setStyle("-fx-background-color: #FFC7C7");
		return new Scene(vbox,1000,500);
	}

	public Scene createServerScene () {

		Label title = new Label("Connect Four Server");
		title.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");

		
		Label listeningToPort = new Label("Listening to port:");
		listenPort = new Label("");
		
		
		Label numClientsLbl = new Label("Number of Clients:");
		numClientsTxtField = new Label("" + 0);

		Label gamesPlayedLbl = new Label("Games played:");
		gamesPlayedTxtField = new Label("" + gamesPlayed);

		GridPane grid = new GridPane();

		VBox info = new VBox(title, grid); 
		info.setAlignment(Pos.CENTER);
		VBox.setMargin(title, new Insets(30,0,30,0));

		
		grid.add(listeningToPort,0,0);
		grid.add(listenPort,1,0);
		grid.add(numClientsLbl,0,1);
		grid.add(numClientsTxtField,1,1);
		grid.add(gamesPlayedLbl,0,2);
		grid.add(gamesPlayedTxtField,1,2);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		Label player1Lbl = new Label("Player 1");
		player1Lbl.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		Label score1Lbl = new Label("Score:");

		ply1ScoreTxtField = new Label("" + 0);
		HBox plyr1HBox = new HBox(score1Lbl, ply1ScoreTxtField);
		plyr1HBox.setSpacing(10);
		plyr1HBox.setAlignment(Pos.CENTER);

		plyr1VBox = new VBox(player1Lbl, plyr1HBox);
		plyr1VBox.setAlignment(Pos.TOP_CENTER);
		//VBox.setMargin(player1Lbl, new Insets(30,0,30,0));

		Label player2Lbl = new Label("Player 2");
		player2Lbl.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 17;");
		Label score2Lbl = new Label("Score:");

		ply2ScoreTxtField = new Label("" + 0);
		HBox plyr2HBox = new HBox(score2Lbl, ply2ScoreTxtField);
		plyr2HBox.setSpacing(10);
		plyr2HBox.setAlignment(Pos.CENTER);

		plyr2VBox = new VBox(player2Lbl, plyr2HBox);
		
		
		plyr2VBox.setAlignment(Pos.TOP_CENTER);
		//VBox.setMargin(player2Lbl, new Insets(30,0,30,0));

		movesList = new ListView<String>();
		movesList.setMaxWidth(400);
		movesList.setMaxHeight(500);
		BorderPane pane = new BorderPane();
		pane.setTop(info);
		pane.setCenter(movesList);
		pane.setLeft(plyr1VBox);
		pane.setRight(plyr2VBox);
		
		BorderPane.setAlignment(plyr1VBox, Pos.CENTER);
		BorderPane.setAlignment(plyr2VBox, Pos.CENTER);
		
		BorderPane.setMargin(movesList, new Insets(40,0,40,0));
		
		BorderPane.setMargin(plyr1VBox, new Insets(0,0,0,100));
		BorderPane.setMargin(plyr2VBox, new Insets(0,100,0,0));
		
		pane.setStyle("-fx-background-color: #FFC7C7");

		return new Scene(pane, 1000, 500);
	}
}
