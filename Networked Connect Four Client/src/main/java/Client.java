import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Client extends Thread {

	private Socket socketClient;
	private int port;
	private String ip;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	CFourInfo info;
	private Consumer<Serializable> callback;

	Client(Consumer<Serializable> call, String ip, int port) {
		this.ip = ip;
		this.port = port;
		callback = call;
		info = new CFourInfo();
	}

	public void run() {
		try {
			
			socketClient= new Socket(ip, port);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
			
			System.out.println("Client is waiting for data. This will be initializing data.");
			System.out.println(info.toString());
			info = (CFourInfo) in.readObject();  // Get object 
			System.out.println("Client received data from server. playerNumber:" + info.getPlayerNumber());
			callback.accept(info);  
			System.out.println("Client sent data to client GUI. playerNumber:" + info.getPlayerNumber());
		}
		catch(Exception e) {			
			System.out.println("Connection with server failed.");
			callback.accept(info);
		}

		while(true) {
			
			try {	
				
				System.out.println("Client is waiting for data.");
				System.out.println(info.toString());
				CFourInfo info = (CFourInfo) in.readObject();
				
				if (info.getPlayer1Exited() || info.getPlayer2Exited() ) {
					System.out.println("Closing socket in client thread");
					socketClient.close();
					break;
				}
				
				if (info.getMove() != null ) {
					System.out.println("Client received data fromm server: (" + info.getMove()[0] + "," + info.getMove()[1] + ")");
				}
				
				System.out.println(info.toString());
			
				callback.accept(info); 
				if (info.getMove() != null) {
					System.out.println("Client sent data to client GUI: (" + info.getMove()[0] + "," + info.getMove()[1] + ")");
					System.out.println(info.toString());
				}
				
				System.out.println();
			} catch(Exception e) {
				System.out.println("There was an EXCEPTION");
			}	
		}	
	}

	public void send(CFourInfo data) {
	
		try {
			System.out.println("Sending data to server: ");
			System.out.println(data.toString());
			//data.receivedFromAddress = Integer.toHexString(System.identityHashCode(this));
			out.writeObject(data);
			System.out.println("After 'out'");
				
		} catch (IOException e) {
			
			System.out.println("EXCEPTION INSIDE SEND.");
			e.printStackTrace();
		}
	}
}