import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server {

	private int port;
	private ClientThread playerOne;
	private ClientThread playerTwo;
	private TheServer server;
	private Consumer<Serializable> callback;
	private int count = 0;
	private CFourInfo info;

	Server(Consumer<Serializable> call, int port){
		this.port = port;
		callback = call;
		server = new TheServer();
		server.start();
		//info = new CFourInfo();
	}

	public class TheServer extends Thread{

		public void run() {
			/*
			while(true) {
				
				try(ServerSocket mysocket = new ServerSocket(port)) {
					
					
					
					
					
				}
				catch(Exception e) {
					System.out.println("Server socket did not launch");
					//callback.accept("Server socket did not launch");
				}
				
			}
				*/
				
			
			try(ServerSocket mysocket = new ServerSocket(port);) {

				System.out.println("Server is waiting for client one!");
				playerOne = new ClientThread(mysocket.accept(), 1);
				playerOne.start();
				
				System.out.println("Player One was initialized");
 
				System.out.println("Server is waiting for client two!");
				playerTwo = new ClientThread(mysocket.accept(), 2);
				playerTwo.start();
				
				System.out.println("Player Two was initialized");
			} //end of try
			catch(Exception e) {
				System.out.println("Server socket did not launch");
				//callback.accept("Server socket did not launch");
			}
			
			
		}
	}

	class ClientThread extends Thread{

		Socket connection;
		int playerNumber;
		ObjectInputStream in;
		ObjectOutputStream out;

		ClientThread(Socket s, int playerNumber){
			this.connection = s;
			this.playerNumber = playerNumber;	
		}

		public void run() {

			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);	
				
				info = new CFourInfo(); 
				info.setPlayerNumber(playerNumber);
				info.setConnectedToServer(true);
				count++;
				info.setNumberOfPlayers(count);
				
				if (count == 1) {
					out.writeObject(info);	
				} else if (count == 2) { 
					playerOne.out.writeObject(info);
					playerTwo.out.writeObject(info);	
				}	
				callback.accept(info);
			}
			catch(Exception e) {
				System.out.println("Streams not open");
			}

			while(true) {
				try {
					
					System.out.println("Server is waiting for data.");
					System.out.println(info.toString());

					info = (CFourInfo) in.readObject(); // Get object from Client

					System.out.println("Server received data from client ");
					System.out.println(info.toString());
					
					callback.accept(info);  // Send object to Server GUI
					System.out.println("Server is sending data to server GUI: ");
					
					if (info.getWhoPlayed() == 1) {
						System.out.println("Server is sending data to Player 2: (");
						playerTwo.out.writeObject(info); // we have access to the OTHER player. Send object.
					} else if (info.getWhoPlayed() == 2){
						System.out.println("Server is sending data to Player 1: (");
						playerOne.out.writeObject(info); // we have access to the OTHER player. Send object.
					}
					
					if (info.getPlayer1Exited() ) {
						System.out.println("Closing player 1 socket");
						
						playerOne.out.writeObject(info);
						connection.close();
						break;
					} else if (info.getPlayer2Exited() ) {
						System.out.println("Closing player 2 socket");
						playerTwo.out.writeObject(info);
						connection.close();
						break;
					}
									
					System.out.println();
				}
				catch (Exception e) {
					
					System.out.println("THERE WAS AN EXCEPTION INSIDE THE SERVER");					
				}
			}
		}//end of run
	}//end of client thread
}